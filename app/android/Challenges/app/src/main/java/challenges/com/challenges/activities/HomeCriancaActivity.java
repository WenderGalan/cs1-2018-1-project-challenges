package challenges.com.challenges.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import challenges.com.challenges.R;
import challenges.com.challenges.adapter.TabsAdapter;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.helper.SlidingTabLayout;

public class HomeCriancaActivity extends AppCompatActivity {

    private Button sair;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_crianca);

        slidingTabLayout = findViewById(R.id.sliding_tab_crianca);
        viewPager = findViewById(R.id.view_pager_crianca);


        sair = findViewById(R.id.button2);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //desloga o usuario e volta para a tela incial de Login
                FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                Intent intent = new Intent(HomeCriancaActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //configurar o adapter da navegacao
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);




    }
}
