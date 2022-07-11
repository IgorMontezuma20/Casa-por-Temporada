package com.example.casaportemporada.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casaportemporada.R;
import com.example.casaportemporada.activity.MainActivity;
import com.example.casaportemporada.databinding.ActivityCriarContaBinding;
import com.example.casaportemporada.databinding.ActivityLoginBinding;
import com.example.casaportemporada.helper.FirebaseHelper;
import com.example.casaportemporada.model.Usuario;

public class CriarContaActivity extends AppCompatActivity {

    private ActivityCriarContaBinding binding;
    private TextView tvTitulo;

    private String nome;
    private String email;
    private String telefone;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarDados(view);
            }
        });

        findViewById(R.id.btnVoltar).setOnClickListener(view -> finish());

        //Nome da Toolbar
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        tvTitulo.setText("Criar Conta");

    }

    private void cadastroUsuario(Usuario usuario){
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                String idUser = task.getResult().getUser().getUid();
                usuario.setId(idUser);

                usuario.salvarDados();
                finish();

                startActivity(new Intent(CriarContaActivity.this, MainActivity.class));

            }else{
                String error = task.getException().getMessage();
                Toast.makeText(this,error ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Recuperando os Dados da Activity
    public void salvarDados(View view){

        nome = binding.edtNome.getText().toString();
        email = binding.edtEmail.getText().toString();
        telefone = binding.edtTelefone.getText().toString();
        senha = binding.edtSenha.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!telefone.isEmpty()) {
                    if (!senha.isEmpty()) {

                        binding.progressBar.setVisibility(View.VISIBLE);

                        Usuario usuario = new Usuario();
                        usuario.setNome(nome);
                        usuario.setEmail(email);
                        usuario.setTelefone(telefone);
                        usuario.setSenha(senha);

                        cadastroUsuario(usuario);
                        //usuario.salvarDados();


                    } else {
                        binding.edtSenha.requestFocus();
                        binding.edtSenha.setError("Defina sua senha");
                    }

                } else {
                    binding.edtTelefone.requestFocus();
                    binding.edtTelefone.setError("Informe seu telefone");
                }
            } else {
                binding.edtEmail.requestFocus();
                binding.edtEmail.setError("informe seu email");
            }
        } else {
            binding.edtNome.requestFocus();
            binding.edtNome.setError("Informe seu nome");
        }
    }
}