package com.example.gastospersonales.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gastospersonales.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.gastospersonales.R;
import com.example.gastospersonales.auth.PinActivity;
import com.example.gastospersonales.auth.SetPinActivity;

public class ProfileActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}
