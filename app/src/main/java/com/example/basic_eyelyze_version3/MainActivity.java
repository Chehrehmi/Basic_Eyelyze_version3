package com.example.basic_eyelyze_version3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
//    EditText username;
//    EditText password;
    Button loginbutton;
    ImageView imageview1;
    FloatingActionButton camera, gallery, add;
    private Animation fab_open, fab_close, fab_rotate_clock, fab_rotate_anticlock;
    boolean isOpen = false;
    String uriString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Login

//        username = findViewById(R.id.username);
//        password=findViewById(R.id.password);
//        loginbutton=findViewById(R.id.button);
//
//        loginbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (username.getText().toString().equals("user1") && password.getText().toString().equals("1234")) {
//                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        })

        //MLKIT PART (adding two buttons where clicking each button reloads the intent to a new activity)
        Button button2 = findViewById(R.id.button2);
        imageview1 = findViewById(R.id.imageView);
        gallery = findViewById(R.id.gallery);
        camera = findViewById(R.id.camera);
        add = findViewById(R.id.add);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_rotate_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_rotate_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("uri", uriString);
                startActivity(intent);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();

            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOpen){
                    camera.setVisibility(View.GONE);
                    gallery.setVisibility(View.GONE);
                    camera.startAnimation(fab_close);
                    gallery.startAnimation(fab_close);
                    add.startAnimation(fab_rotate_anticlock);
                    camera.setClickable(false);
                    gallery.setClickable(false);
                    isOpen = true;
                } else {
                    camera.setVisibility(View.VISIBLE);
                    gallery.setVisibility(View.VISIBLE);
                    camera.startAnimation(fab_open);
                    gallery.startAnimation(fab_open);
                    add.startAnimation(fab_rotate_clock);
                    camera.setClickable(true);
                    gallery.setClickable(true);
                    isOpen = false;
                }
            }
        });


    }
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try{
//            if(requestCode == 101){
//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                imageview1.setImageBitmap(bitmap);
//                uriString = bitmap.toString();
//
//                Uri imageUri = getImageUri(this, bitmap);
//                uriString = imageUri.toString();
//            }
//        } catch (Exception e){
//            Toast.makeText(MainActivity.this, "No Photo Taken", Toast.LENGTH_SHORT).show();
//        }
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 101) {
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getExtras() != null && data.getExtras().containsKey("data")) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        imageview1.setImageBitmap(bitmap);
                        Uri imageUri = getImageUri(this, bitmap);
                        uriString = imageUri.toString();
                    } else {
                        throw new Exception("No image data returned from camera. Please take a photo.");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Camera operation canceled", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {

                @Override
                public void onActivityResult(Uri uri) {
                    imageview1.setImageURI(uri);
                    uriString = uri.toString();
                }
            });

}

