package challenges.com.challenges.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;

public class HomeResponsavelActivity extends AppCompatActivity {

    private Button sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_responsavel);

        sair = findViewById(R.id.botao_sair);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //desloga o usuario e volta para a tela incial de Login
                FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                Intent intent = new Intent(HomeResponsavelActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
