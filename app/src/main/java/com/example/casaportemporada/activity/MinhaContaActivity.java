package com.example.casaportemporada.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.casaportemporada.databinding.ActivityMainBinding;
import com.example.casaportemporada.databinding.ActivityMinhaContaBinding;

public class MinhaContaActivity extends AppCompatActivity {

    private ActivityMinhaContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMinhaContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}