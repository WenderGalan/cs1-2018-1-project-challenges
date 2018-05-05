package challenges.com.challenges.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Responsavel;
import challenges.com.challenges.util.Validator;

public class CadastroActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText email;
    private EditText nome;
    private EditText senha;
    private EditText confirmarSenha;
    private Button continuar;
    private Responsavel responsavel;
    private FirebaseAuth autenticacao;
    private ProgressDialog progressDialog;
    private String tipo;
    private String idUsuario;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email = findViewById(R.id.editTextEmail);
        nome = findViewById(R.id.editTextNome);
        senha = findViewById(R.id.editTextSenha);
        confirmarSenha = findViewById(R.id.editTextConfirmarSenha);
        continuar = findViewById(R.id.buttonContinuar);
        responsavel = new Responsavel();

        toolbar = findViewById(R.id.toolbarCadastro);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

        tipo = getIntent().getStringExtra("tipo");

        if (tipo.equals("editar")) {
            getSupportActionBar().setTitle("Editar Usuário");
            idUsuario = ConfiguracaoFirebase.getFirebaseAutenticacao().getCurrentUser().getUid();


            ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuario).get().addOnSuccessListener(CadastroActivity.this, new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Responsavel responsavel = documentSnapshot.toObject(Responsavel.class);

                    email.setText(responsavel.getEmail());
                    email.setClickable(false);
                    email.setFocusable(false);
                    email.setBackgroundColor(R.color.cinza);

                    nome.setText(responsavel.getNome());

                    senha.setText("nao pode alterar");
                    senha.setClickable(false);
                    senha.setFocusable(false);
                    senha.setBackgroundColor(R.color.cinza);

                    final int sdk = android.os.Build.VERSION.SDK_INT;

                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        confirmarSenha.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                        senha.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                        email.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                    } else {
                        confirmarSenha.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                        senha.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                        email.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                    }

                    confirmarSenha.setText("nao pode alterar");
                    confirmarSenha.setClickable(false);
                    confirmarSenha.setFocusable(false);
                }
            });

            continuar.setText("Salvar");

        } else if (tipo.equals("novo")) {
            getSupportActionBar().setTitle("Crie sua conta");
        }


        //botao cadastrar
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipo.equals("novo")) {
                    if (validarCampos()) {
                        responsavel.setNome(nome.getText().toString());
                        responsavel.setSenha(senha.getText().toString());
                        responsavel.setEmail(email.getText().toString());
                        cadastrarUsuario();
                    }
                } else if (tipo.equals("editar")) {
                    Map<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("nome", nome.getText().toString());
                    DocumentReference reference = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuario);
                    reference.update(hashMap).addOnSuccessListener(CadastroActivity.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Usuário atualizado com sucesso!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CadastroActivity.this, HomeResponsavelActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                }
            }
        });
    }

    private void cadastrarUsuario() {
        progressDialog = new ProgressDialog(CadastroActivity.this);
        progressDialog.setMessage("Inserindo o usuário...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString()).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //insere no banco o usuario agora
                    Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                    String idUsuario = autenticacao.getCurrentUser().getUid();
                    //seta o ID do usuario
                    responsavel.setId(idUsuario);

                    CollectionReference referencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios");
                    referencia.document(idUsuario).set(responsavel.construirHash()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.i("DEBUG", "SALVO COM SUCESSO");
                            autenticacao.signOut();
                        }
                    });

                    progressDialog.dismiss();

                    //cadastra a crianca agora - passa o id do usuario para pegar a referencia dele depois
                    abrirCadastroCrianca(idUsuario);

                } else {
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

                    Toast.makeText(CadastroActivity.this, "" + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }


        });

        autenticacao.signOut();
    }

    private void abrirCadastroCrianca(String responsavel) {
        Intent intent = new Intent(CadastroActivity.this, CadastroCriancaActivity.class);
        intent.putExtra("responsavel", responsavel);
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
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //Botão BACK padrão do android
        if (tipo.equals("novo")) {
            startActivity(new Intent(this, LoginActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
            return;
        } else if (tipo.equals("editar")) {
            startActivity(new Intent(this, HomeResponsavelActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
            return;
        }
    }

}
