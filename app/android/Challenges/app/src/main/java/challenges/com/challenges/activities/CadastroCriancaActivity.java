package challenges.com.challenges.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.Responsavel;
import challenges.com.challenges.util.Validator;
import de.hdodenhof.circleimageview.CircleImageView;

public class CadastroCriancaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText email;
    private EditText nome;
    private EditText senha;
    private EditText confirmarSenha;
    private TextView cadastrarDepois;
    private Button concluir;
    private FirebaseAuth autenticacao;
    private CircleImageView imagem;
    private static final int GALLERY_INTENT = 2;
    private Uri imagemCarregada = null;
    private Crianca crianca;
    private Responsavel responsavelRecuperado;
    private String responsavel;
    private String segundoCadastro;
    private String idUsuario;
    private ProgressDialog progressDialog;
    private boolean tipoDeConclusao = true; //true significa o fluxo normal, false é o fluxo de dentro do app
    private SharedPreferences sharedPreferences;
    private String tipo;
    private String id;
    private Crianca criancaEditavel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_crianca);

        responsavel = getIntent().getStringExtra("responsavel");
        segundoCadastro = getIntent().getStringExtra("segundoCadastro");
        try {
            tipo = getIntent().getStringExtra("tipo");
        } catch (Exception e) {
            e.printStackTrace();
        }


        cadastrarDepois = findViewById(R.id.textViewCadastrarDepois);
        imagem = findViewById(R.id.imagemPerfil);
        email = findViewById(R.id.editTextEmail);
        nome = findViewById(R.id.editTextNome);
        senha = findViewById(R.id.editTextSenha);
        confirmarSenha = findViewById(R.id.editTextConfirmarSenha);
        concluir = findViewById(R.id.buttonConcluir);
        toolbar = findViewById(R.id.toolbarCadastroCrianca);
        setSupportActionBar(toolbar);


        if (tipo.equals("editar")) {
            getSupportActionBar().setTitle("Alterar a criança");
            cadastrarDepois.setVisibility(View.INVISIBLE);
            id = getIntent().getStringExtra("id");

            ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(id).get().addOnSuccessListener(CadastroCriancaActivity.this, new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    criancaEditavel = documentSnapshot.toObject(Crianca.class);

                    if (criancaEditavel.getFoto() != null){
                        Picasso.get().load(criancaEditavel.getFoto()).into(imagem);
                    }else{
                        Picasso.get().load(R.drawable.user).into(imagem);
                    }

                    nome.setText(criancaEditavel.getNome());

                    email.setText(criancaEditavel.getEmail());
                    email.setClickable(false);
                    email.setFocusable(false);
                    senha.setText("0000000000");
                    senha.setClickable(false);
                    senha.setFocusable(false);
                    confirmarSenha.setText("0000000000");
                    confirmarSenha.setClickable(false);
                    confirmarSenha.setFocusable(false);

                    final int sdk = android.os.Build.VERSION.SDK_INT;

                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        email.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                        senha.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                        confirmarSenha.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                    } else {
                        email.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                        senha.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                        confirmarSenha.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                    }


                }
            });


        }else{
            getSupportActionBar().setTitle("Cadastre uma criança");
        }



        if (tipo.equals(null)){
            if (segundoCadastro.equals("verdade") || tipo.equals("editar")) {
                cadastrarDepois.setVisibility(View.INVISIBLE);
                tipoDeConclusao = false;
            }
        }

        cadastrarDepois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroCriancaActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
                if (tipo.equals("editar")) {
                    //salva no banco a alteraçao
                    progressDialog = new ProgressDialog(CadastroCriancaActivity.this);
                    progressDialog.setMessage("Atualizando a criança...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    if (imagemCarregada != null) {
                        //inserir no Storage e salva no banco
                        StorageReference storageRef = ConfiguracaoFirebase.getStorage();
                        //Cada usuario tem uma pasta com seu UID e dentro tem duas pastas com sua foto de perfil e a foto de seus anuncios
                        StorageReference imagemPerfil = storageRef.child(id + "/" + "perfil/" + imagemCarregada.getLastPathSegment());
                        //upa a imagem ao storage
                        UploadTask uploadTask = imagemPerfil.putFile(imagemCarregada);
                        //Listener para verificar o estado da task de upload
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //pega o link da foto
                                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                //seta a foto no usuario
                                criancaEditavel.setFoto(downloadUrl.toString());

                                Map<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("foto", criancaEditavel.getFoto());
                                hashMap.put("nome", nome.getText().toString());
                                ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(id).update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Criança atualizada com sucesso!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(CadastroCriancaActivity.this, HomeResponsavelActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                    }



                    progressDialog.dismiss();

                } else {
                    if (validarCampos()) {
                        crianca = new Crianca();
                        crianca.setNome(nome.getText().toString());
                        crianca.setSenha(senha.getText().toString());
                        crianca.setEmail(email.getText().toString());
                        crianca.setPontos(0);
                        //pega a referencia do pai
                        DocumentReference responsavelReferencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(responsavel);
                        crianca.setResponsavel(responsavelReferencia);
                        cadastrarCrianca();
                    }
                }
            }
        });
    }

    private void cadastrarCrianca() {
        progressDialog = new ProgressDialog(CadastroCriancaActivity.this);
        progressDialog.setMessage("Inserindo a criança...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString()).addOnCompleteListener(CadastroCriancaActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //deu certo
                if (task.isSuccessful()) {
                    //salvar no banco de dados
                    idUsuario = autenticacao.getCurrentUser().getUid();
                    crianca.setId(idUsuario);
                    if (imagemCarregada != null) {
                        //inserir no Storage e salva no banco
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
                                crianca.setFoto(downloadUrl.toString());
                                //insere no banco a crianca
                                CollectionReference referencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios");
                                referencia.document(idUsuario).set(crianca.construirHash()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        autenticacao.signOut();

                                        CollectionReference reference = ConfiguracaoFirebase.getFirestore().collection("Usuarios");
                                        reference.document(responsavel).get().addOnSuccessListener(CadastroCriancaActivity.this, new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                responsavelRecuperado = documentSnapshot.toObject(Responsavel.class);
                                                //recupera a referencia da crianca inserida
                                                DocumentReference referencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuario);
                                                //cria um vetor de criancas
                                                ArrayList<DocumentReference> criancas = responsavelRecuperado.getCriancas();
                                                criancas.add(referencia);
                                                //insere no responsavel as criancas
                                                DocumentReference responsavelRef = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(responsavel);
                                                Map<String, Object> update = new HashMap<String, Object>();
                                                update.put("criancas", criancas);
                                                responsavelRef.update(update);
                                                Toast.makeText(CadastroCriancaActivity.this, "Criança inserida com sucesso", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }
                                });
                            }
                        });

                        autenticacao.signOut();

                    } else {
                        //insere no banco a crianca
                        CollectionReference referencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios");
                        referencia.document(idUsuario).set(crianca.construirHash()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                autenticacao.signOut();
                                CollectionReference reference = ConfiguracaoFirebase.getFirestore().collection("Usuarios");
                                reference.document(responsavel).get().addOnSuccessListener(CadastroCriancaActivity.this, new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        responsavelRecuperado = documentSnapshot.toObject(Responsavel.class);
                                        //recupera a referencia da crianca inserida
                                        DocumentReference referencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuario);
                                        //cria um vetor de criancas
                                        ArrayList<DocumentReference> criancas = responsavelRecuperado.getCriancas();
                                        criancas.add(referencia);
                                        //insere no responsavel as criancas
                                        DocumentReference responsavelRef = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(responsavel);
                                        Map<String, Object> update = new HashMap<String, Object>();
                                        update.put("criancas", criancas);
                                        responsavelRef.update(update);
                                        Toast.makeText(CadastroCriancaActivity.this, "Criança inserida com sucesso", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });

                    }

                    progressDialog.dismiss();
                    if (tipoDeConclusao == true) {
                        abrirTelaPrincipal();
                    } else {
                        finish();
                        autenticarNovamente();
                    }


                } else {//nao cadastrou
                    progressDialog.dismiss();
                    String erroExcecao = "";
                    try {
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail";
                        email.setFocusable(true);
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse e-mail já esá em uso no APP!";
                        email.setFocusable(true);
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroCriancaActivity.this, "" + erroExcecao, Toast.LENGTH_LONG).show();
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
        if (!validator.validateNotNull(email)) {
            email.setError("Email vazio");
            email.setFocusable(true);
            email.requestFocus();
            retorno = false;
        }
        if (!validator.validateNotNull(nome)) {
            nome.setError("Nome vazio");
            nome.setFocusable(true);
            nome.requestFocus();
            retorno = false;
        }
        if (!validator.validateNotNull(senha)) {
            senha.setError("Senha vazia");
            senha.setFocusable(true);
            senha.requestFocus();
            retorno = false;
        }
        if (!senha.getText().toString().equals(confirmarSenha.getText().toString())) {
            confirmarSenha.setError("Senhas diferentes");
            confirmarSenha.setFocusable(true);
            confirmarSenha.requestFocus();
            retorno = false;
        }
        return retorno;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            imagemCarregada = data.getData();
            imagem.setImageURI(imagemCarregada);
        }

    }

    private void autenticarNovamente() {

        sharedPreferences = getSharedPreferences("CHALLENGES", Context.MODE_PRIVATE);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String email = sharedPreferences.getString("email", "email");
        String senha = sharedPreferences.getString("senha", "senha");

        autenticacao.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("DEBUG", "Autenticou de novo o usuário");
            }
        });

    }

}


