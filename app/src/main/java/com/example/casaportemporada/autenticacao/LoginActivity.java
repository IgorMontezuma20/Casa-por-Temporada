package com.example.casaportemporada.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.casaportemporada.activity.MainActivity;
import com.example.casaportemporada.databinding.ActivityLoginBinding;
import com.example.casaportemporada.helper.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, CriarContaActivity.class));
            }
        });

        binding.tvEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, EsqueciSenhaActivity.class));
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaDados(view);
            }
        });
    }

    public void validaDados( View view){

        String email = binding.edtEmail.getText().toString();
        String senha = binding.edtSenha.getText().toString();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){

                binding.progressBar.setVisibility(View.VISIBLE);

                login(email, senha);

            }else{
                binding.edtSenha.requestFocus();
                binding.edtSenha.setError("Informe sua senha");
            }

        }else{
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Preencha o campo de email");
        }

    }

    private void login(String email, String senha){

        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }else{
                String erro = task.getException().getMessage();
                Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
