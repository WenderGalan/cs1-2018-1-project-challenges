package challenges.com.challenges.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import challenges.com.challenges.R;
import challenges.com.challenges.fragments.HomeFragment;
import challenges.com.challenges.fragments.PerfilCriancaFragment;

public class HomeCriancaActivity extends AppCompatActivity {

    private boolean fragmentHome = true;
    private boolean fragmentPerfil = false;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (!fragmentHome) {
                        HomeFragment homeFragment = new HomeFragment();
                        fragmentTransaction.replace(R.id.rl_id_container_fragment, homeFragment);
                        fragmentTransaction.commit();
                        fragmentHome = true;
                        fragmentPerfil = false;
                    }
                    return true;

                case R.id.navigation_notifications:
                    if (!fragmentPerfil) {
                        PerfilCriancaFragment perfilCriancaFragment = new PerfilCriancaFragment();
                        fragmentTransaction.replace(R.id.rl_id_container_fragment, perfilCriancaFragment);
                        fragmentTransaction.commit();
                        fragmentHome = false;
                        fragmentPerfil = true;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crianca);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.rl_id_container_fragment, homeFragment);
        fragmentTransaction.commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
