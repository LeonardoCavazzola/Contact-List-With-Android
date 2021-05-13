package com.example.androidt2;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.floatingActionButton)
                .setOnClickListener(v -> startActivity(new Intent(this, AddActivity.class)));
    }

    public void takePhotoClick(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 1);
    }
}
