package com.hacktiv8.aplikasikpu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    // Metode untuk membuka MainActivity
    public void goToInformasiActivity(View view) {
        Intent intent = new Intent(this, InformasiActivity.class);
        startActivity(intent);
    }

    // Metode untuk membuka MainActivity
    public void goToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Metode untuk membuka AddActivity
    public void goToAddActivity(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    // Metode untuk keluar dari aplikasi
    public void exitApp(View view) {
        finish(); // Close the current activity
        System.exit(0); // Terminate the app
    }
}