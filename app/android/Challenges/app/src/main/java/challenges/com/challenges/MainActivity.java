package challenges.com.challenges;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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