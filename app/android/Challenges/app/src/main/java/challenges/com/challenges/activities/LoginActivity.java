package challenges.com.challenges.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.util.Validator;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button entrar;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = findViewById(R.id.editTextEmailLogin);
        senha = findViewById(R.id.editTextSenhaLogin);
        entrar = findViewById(R.id.buttonEntrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    validarLogin(email.getText().toString(), senha.getText().toString());
                }
            }
        });
    }

    private void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    private void validarLogin(String email, String senha) {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    abrirTelaPrincipal();
                }else{
                    String erroExcecao = "";
                    try {
                        throw task.getException();

                    } catch (FirebaseAuthInvalidUserException e){
                        erroExcecao = "Email inexistente ou desativado";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "Usuário ou senha inválidos";
                    } catch (FirebaseAuthEmailException e){
                        erroExcecao = "Email inválido";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this, "" + erroExcecao, Toast.LENGTH_LONG ).show();
                }
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
        if (!validator.validateNotNull(senha)){
            senha.setError("Senha vazia");
            senha.setFocusable(true);
            senha.requestFocus();
            retorno = false;
        }
        return retorno;
    }

    public void criarNovaConta(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
        finish();
    }

    public void esqueceuSenha(View view) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        //definindo um titulo
        alertDialog.setTitle("Redefinir senha");
        //definindo uma mensagem
        alertDialog.setMessage("Digite seu email");

        //Edit text dentro do Alert Dialog
        final EditText input = new EditText(LoginActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setGravity(1);
        input.setLayoutParams(lp);
        //passando ao Alert Dialog o edit text
        alertDialog.setView(input);

        //botao positivo - enviar
        alertDialog.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //logica positiva - enviar
                FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                if (input.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Email não enviado", Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "Campo Vazio");
                }else{
                    autenticacao.sendPasswordResetEmail(input.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Email enviado com sucesso", Toast.LENGTH_LONG).show();
                                Log.i("DEBUG", "Erro: "+task.getException());
                            }else{
                                Toast.makeText(LoginActivity.this, "Email inválido", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        //botao negativo - cancelar
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //logica negativa - cancelar
                dialogInterface.cancel();
            }
        });
        //mostrar o alert dialog
        alertDialog.show();

    }
}
