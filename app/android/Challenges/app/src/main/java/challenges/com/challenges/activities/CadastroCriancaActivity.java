package challenges.com.challenges.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Usuario;
import challenges.com.challenges.util.Validator;
import de.hdodenhof.circleimageview.CircleImageView;

public class CadastroCriancaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText email;
    private EditText nome;
    private EditText senha;
    private EditText confirmarSenha;
    private Button concluir;
    private FirebaseAuth autenticacao;
    private CircleImageView imagem;
    private static final int GALLERY_INTENT = 2;
    private Uri imagemCarregada = null;
    private Usuario usuario;
    private String responsavel;
    String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_crianca);

        responsavel = getIntent().getStringExtra("responsavel");


        imagem = findViewById(R.id.imagemPerfil);
        email = findViewById(R.id.editTextEmail);
        nome = findViewById(R.id.editTextNome);
        senha = findViewById(R.id.editTextSenha);
        confirmarSenha = findViewById(R.id.editTextConfirmarSenha);
        concluir = findViewById(R.id.buttonConcluir);
        toolbar = findViewById(R.id.toolbarCadastroCrianca);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cadastre uma criança");

        imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir o selecionador de imagem
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    usuario = new Usuario();
                    usuario.setNome(nome.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setPontos(0);
                    usuario.setTipo(1); //tipo crianca
                    //pega a referencia do responsavel
                    DocumentReference responsavelReferencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(responsavel);
                    usuario.setResponsavel(responsavelReferencia);
                    cadastrarCrianca();
                }
            }
        });
    }


    private void cadastrarCrianca() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString()).addOnCompleteListener(CadastroCriancaActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //deu certo
                if (task.isSuccessful()){
                    Toast.makeText(CadastroCriancaActivity.this, "Criança inserida com sucesso", Toast.LENGTH_LONG).show();
                    //salvar no banco de dados
                    if (imagemCarregada != null){
                        //inserir no Storage e salva no banco
                        idUsuario = autenticacao.getCurrentUser().getUid();
                        StorageReference storageRef = ConfiguracaoFirebase.getStorage();
                        //Cada usuario tem uma pasta com seu UID e dentro tem duas pastas com sua foto de perfil e a foto de seus anuncios
                        StorageReference imagemPerfil = storageRef.child(idUsuario + "/" + "perfil/" + imagemCarregada.getLastPathSegment());
                        //upa a imagem ao storage
                        UploadTask uploadTask = imagemPerfil.putFile(imagemCarregada);
                        //Listener para verificar o estado da task de upload
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //pega o link da foto
                                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                //seta a foto no usuario
                                usuario.setFoto(downloadUrl.toString());
                                //insere no banco a crianca
                                CollectionReference referencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios");
                                Map<String, Object> salvar = new HashMap<String, Object>();
                                salvar.put("nome", usuario.getNome());
                                salvar.put("email", usuario.getEmail());
                                salvar.put("tipo", usuario.getTipo());
                                salvar.put("foto", usuario.getFoto());
                                salvar.put("pontos", usuario.getPontos());
                                salvar.put("responsavel", usuario.getResponsavel());
                                referencia.document(idUsuario).set(salvar).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    //Insere no responsavel a referencia da crianca
                                    public void onComplete(@NonNull Task<Void> task) {
                                        autenticacao.signOut();
                                        //recupera a referencia da crianca inserida
                                        DocumentReference referencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuario);
                                        //cria um vetor de criancas
                                        ArrayList<DocumentReference> criancas = new ArrayList<>();
                                        criancas.add(referencia);
                                        //insere no responsavel as criancas
                                        DocumentReference responsavelRef = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(responsavel);
                                        Map<String, Object> update = new HashMap<String, Object>();
                                        update.put("criancas", criancas);
                                        responsavelRef.update(update);
                                        abrirTelaPrincipal();
                                    }
                                });
                            }
                        });

                    }else{
                        Log.i("DEBUG", "Entrou no ELSE");
                        //apenas salva no banco
                        idUsuario = autenticacao.getCurrentUser().getUid();
                        Log.i("DEBUG", "Usuario: " + idUsuario);
                        CollectionReference referencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios");
                        Map<String, Object> salvar = new HashMap<String, Object>();
                        salvar.put("nome", usuario.getNome());
                        salvar.put("email", usuario.getEmail());
                        salvar.put("tipo", usuario.getTipo());
                        salvar.put("pontos", usuario.getPontos());
                        salvar.put("responsavel", usuario.getResponsavel());
                        referencia.document(idUsuario).set(salvar).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                autenticacao.signOut();
                                //recupera a referencia da crianca inserida
                                DocumentReference referencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuario);
                                //cria um vetor de criancas
                                ArrayList<DocumentReference> criancas = new ArrayList<>();
                                criancas.add(referencia);
                                //insere no responsavel as criancas
                                DocumentReference responsavelRef = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(responsavel);
                                Map<String, Object> update = new HashMap<String, Object>();
                                update.put("criancas", criancas);
                                responsavelRef.update(update);
                                abrirTelaPrincipal();
                            }
                        });
                    }



                }else{//nao cadastrou
                    String erroExcecao = "";
                    try {
                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail";
                        email.setFocusable(true);
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Esse e-mail já esá em uso no APP!";
                        email.setFocusable(true);
                    }catch (Exception e){
                        erroExcecao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroCriancaActivity.this, "" + erroExcecao, Toast.LENGTH_LONG ).show();
                }
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(CadastroCriancaActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validarCampos() {
        Validator validator = new Validator();
        boolean retorno = true;
        if (!validator.validateNotNull(email)){
            email.setError("Email vazio");
            email.setFocusable(true);
            email.requestFocus();
            retorno = false;
        }
        if (!validator.validateNotNull(nome)){
            nome.setError("Nome vazio");
            nome.setFocusable(true);
            nome.requestFocus();
            retorno = false;
        }
        if (!validator.validateNotNull(senha)){
            senha.setError("Senha vazia");
            senha.setFocusable(true);
            senha.requestFocus();
            retorno = false;
        }
        if (!senha.getText().toString().equals(confirmarSenha.getText().toString())){
            confirmarSenha.setError("Senhas diferentes");
            confirmarSenha.setFocusable(true);
            confirmarSenha.requestFocus();
            retorno = false;
        }
        return retorno;
    }


    public void cadastrarDepois(View view) {
        Intent intent = new Intent(CadastroCriancaActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            imagemCarregada = data.getData();
            imagem.setImageURI(imagemCarregada);
        }

    }


}


