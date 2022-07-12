package com.example.casaportemporada.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casaportemporada.R;
import com.example.casaportemporada.databinding.ActivityEsqueciSenhaBinding;
import com.example.casaportemporada.databinding.ActivityLoginBinding;
import com.example.casaportemporada.helper.FirebaseHelper;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private ActivityEsqueciSenhaBinding binding;
    private TextView tvTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEsqueciSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        findViewById(R.id.btnVoltar).setOnClickListener(view -> finish());

        //Nome da Toolbar
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        tvTitulo.setText("Trocar Senha");

        binding.btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaDados(view);
            }
        });

    }

    public void validaDados(View view){

        String email = binding.edtEmailEsqueciSenha.getText().toString();

        if(!email.isEmpty()){

            binding.progressBar.setVisibility(View.VISIBLE);

            recuperaSenha(email);

        }else{
            binding.edtEmailEsqueciSenha.requestFocus();
            binding.edtEmailEsqueciSenha.setError("Informe o email.");
        }

    }

    private void recuperaSenha(String email){

        FirebaseHelper.getAuth().sendPasswordResetEmail(email).addOnCompleteListener(
                task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(this, "Email enviado com sucesso",
                                Toast.LENGTH_SHORT).show();
                    }else{

                        String erro = task.getException().getMessage();
                        Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();

                    }
                }
        );

    }
}