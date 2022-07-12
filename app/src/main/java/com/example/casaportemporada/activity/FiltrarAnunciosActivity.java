package com.example.casaportemporada.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.casaportemporada.R;
import com.example.casaportemporada.databinding.ActivityFiltrarAnunciosBinding;
import com.example.casaportemporada.databinding.ActivityMainBinding;

public class FiltrarAnunciosActivity extends AppCompatActivity {

    private ActivityFiltrarAnunciosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFiltrarAnunciosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}