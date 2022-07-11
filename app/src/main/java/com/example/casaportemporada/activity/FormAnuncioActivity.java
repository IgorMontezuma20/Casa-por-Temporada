package com.example.casaportemporada.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casaportemporada.R;
import com.example.casaportemporada.databinding.ActivityFormAnuncioBinding;
import com.example.casaportemporada.helper.FirebaseHelper;
import com.example.casaportemporada.model.Anuncio;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.List;

public class FormAnuncioActivity extends AppCompatActivity {

    private ActivityFormAnuncioBinding binding;

    private static final int REQUEST_GALERIA = 100;

    private ImageButton btnSalvar;

    //Componentes de imagem
    private ImageView imgAnuncio;
    private String caminhoImagem;
    private Bitmap bitmap;
    private Anuncio anuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormAnuncioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        confCliques();
        componentes();

        binding.cvImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissaoGaleria(view);
            }
        });

    }

    //Permissões de galeria
    public void permissaoGaleria(View view){

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                abrirGaleria();

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(FormAnuncioActivity.this,"Permissão negada.",Toast.LENGTH_SHORT).show();
            }
        };

        showDialogPermissaoGaleria(permissionListener, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});

    }

    //Erro de Permissão
    private void showDialogPermissaoGaleria(PermissionListener listener, String[] permissoes){

       TedPermission.create()
               .setPermissionListener(listener)
               .setDeniedTitle("Permissões negadas.")
               .setDeniedMessage("As permissões são necessárias para o aplicativo, deseja pearmitir?")
               .setDeniedCloseButtonText("Não")
               .setGotoSettingButtonText("Sim")
               .setPermissions(permissoes).check();;

    }

    //Acesso à galeria
    private void abrirGaleria(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALERIA);
    }

    //Salvar Imagem do Anúncio
    private void salvarImagem(){

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("imagens")
                .child("anuncios")
                .child(anuncio.getId() +".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(caminhoImagem));
        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl()
                .addOnCompleteListener(task -> {

                    String urlImagem = task.getResult().toString();
                    anuncio.setUrlImagem(urlImagem);

                    anuncio.salvar();


                    //finish();

                })).addOnFailureListener(e ->
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    //Configuração do smétodos de Clique
    private void confCliques(){
        findViewById(R.id.btnSalvar).setOnClickListener(view -> validacao());

    }

    //Inicialização dos Componetes
    private void componentes(){

        btnSalvar = findViewById(R.id.btnSalvar);
        imgAnuncio = findViewById(R.id.imgAnuncio);

        TextView tvTitulo = findViewById(R.id.tvTitulo);
        tvTitulo.setText("Informações do Anúncio");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_GALERIA){

                Uri localImagemSelecionada = data.getData();
                caminhoImagem = data.getData().toString();

                if(Build.VERSION.SDK_INT < 28){
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                                localImagemSelecionada);
                        binding.imgAnuncio.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(),
                            localImagemSelecionada);
                    try {
                        bitmap = ImageDecoder.decodeBitmap(source);
                        binding.imgAnuncio.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //imgAnuncio.setImageBitmap(imagem);

            }
        }

    }

    //Validação do Dados
    private void validacao(){

        String titulo = binding.edtTituloItem.getText().toString();
        String descricao = binding.edtDescricao.getText().toString();
        String quartos = binding.edtQuarto.getText().toString();
        String banheiros = binding.edtBanheiro.getText().toString();
        String garagem = binding.edtGaragem.getText().toString();

        if(!titulo.isEmpty()){
            if(!descricao.isEmpty()){
                if(!quartos.isEmpty()){
                    if(!banheiros.isEmpty()){
                        if(!garagem.isEmpty()){

                            if(anuncio == null) anuncio = new Anuncio();
                            anuncio.setTitulo(titulo);
                            anuncio.setDescricao(descricao);
                            anuncio.setQuarto(quartos);
                            anuncio.setBanheiro(banheiros);
                            anuncio.setGaragem(garagem);
                            anuncio.setStatus(binding.cbStatus.isChecked());

                            if(caminhoImagem != null){
                                salvarImagem();
                            }else{
                                Toast.makeText(this, "Selecione uma imagem para o anúncio",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            binding.edtGaragem.requestFocus();
                            binding.edtGaragem.setError("Campo obrigatório.");
                        }
                    }else{
                        binding.edtBanheiro.requestFocus();
                        binding.edtBanheiro.setError("Campo obrigatório.");
                    }

                }else{
                    binding.edtQuarto.requestFocus();
                    binding.edtQuarto.setError("Campo obrigatório.");
                }

            }else{
                binding.edtDescricao.requestFocus();
                binding.edtDescricao.setError("Descreva o anúncio.");
            }
        }else{
            binding.edtTituloItem.requestFocus();
            binding.edtTituloItem.setError("Informe o título do anúncio.");
        }

    }
}