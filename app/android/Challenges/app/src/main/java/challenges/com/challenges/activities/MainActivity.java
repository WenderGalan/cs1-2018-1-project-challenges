package challenges.com.challenges.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import challenges.com.challenges.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //regras alteradas para gravar
        DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
        referencia.child("pontos").setValue("100");

    }
}
