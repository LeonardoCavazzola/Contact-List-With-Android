package com.example.androidt2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.floatingActionButton)
                .setOnClickListener(v -> startActivity(new Intent(this, AddActivity.class)));
    }
}
