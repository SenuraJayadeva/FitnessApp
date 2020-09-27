package com.mad.fitapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UserRegistation extends AppCompatActivity {

    private ImageView profileImage;
    private EditText userName;
    private EditText userEmail;
    private EditText userPassword;
    private Button registerButton;
    private ProgressDialog dialog;
    private static final int GALARY_INTENT = 2;
    private Uri imageUri;
    private String userId;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Users");
    private StorageReference userImageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registation);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        userImageReference = FirebaseStorage.getInstance().getReference("User_Profile_images");

        uploadImage();

    }

    private void uploadImage() {
        profileImage = findViewById(R.id.goal_user_image);
        userName = findViewById(R.id.registration_user_name);
        userEmail = findViewById(R.id.registration_user_email);
        userPassword = findViewById(R.id.registration_user_password);
        registerButton = findViewById(R.id.registratoin_button);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "ProductImage"), GALARY_INTENT);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();
            }
        });
    }

    private void registration() {
        if (imageUri != null) {
            final String name = userName.getText().toString().trim();
            final String email = userEmail.getText().toString().trim();
            final String password = userPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                userName.setError("Name is required");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                userEmail.setError("Email is required");
                return;
            }

            dialog.setMessage("Creating Account...");
            dialog.show();

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        userId = auth.getCurrentUser().getUid();
                        final StorageReference uploadData = userImageReference.child(System.currentTimeMillis() + "." + GetFileExtension(imageUri));

                        uploadData.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                uploadData.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url =  uri.toString();
                                        User newUser = new User(name, email, password, url);
                                        reference.child(userId).setValue(newUser);
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Home.class));
                                    }
                                });
                            }
                        });
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Account Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALARY_INTENT && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}