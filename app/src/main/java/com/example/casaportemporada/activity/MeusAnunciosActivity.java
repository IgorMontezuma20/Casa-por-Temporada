package com.example.casaportemporada.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.casaportemporada.databinding.ActivityMainBinding;
import com.example.casaportemporada.databinding.ActivityMeusAnunciosBinding;

public class MeusAnunciosActivity extends AppCompatActivity {

    private ActivityMeusAnunciosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeusAnunciosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}