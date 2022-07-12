package com.example.casaportemporada.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.casaportemporada.R;
import com.example.casaportemporada.autenticacao.LoginActivity;
import com.example.casaportemporada.databinding.ActivityLoginBinding;
import com.example.casaportemporada.databinding.ActivityMainBinding;
import com.example.casaportemporada.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ImageButton ic_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        componentes();
        cliques();


    }

    private void cliques(){
        ic_menu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, ic_menu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_home, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if(menuItem.getItemId() == R.id.menu_filtrar){
                    startActivity(new Intent(this, FiltrarAnunciosActivity.class));
                }else if(menuItem.getItemId() == R.id.menu_meus_anuncios){
                    if(FirebaseHelper.getAutenticado()){
                        startActivity(new Intent(this, MeusAnunciosActivity.class));
                    }else{
                        showDialogLogin();
                    }
                }else{
                    if(FirebaseHelper.getAutenticado()){
                        startActivity(new Intent(this, MinhaContaActivity.class));
                    }else{
                        showDialogLogin();
                    }
                }
                return true;
            });
            popupMenu.show();
        });
    }

    private void showDialogLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autenticação: ");
        builder.setMessage("Você não está logado no app, deseja fazer isso agora ?");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", (dialog, wich) -> dialog.dismiss());
        builder.setPositiveButton("Sim", (dialog, wich) -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void componentes(){
        ic_menu = findViewById(R.id.ic_menu);
    }
}