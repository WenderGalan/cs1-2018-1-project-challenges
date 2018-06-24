package challenges.com.challenges.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.adapter.CriancaSolicitacaoAdapter;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;

public class SearchAmigos extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView lista;
    private CriancaSolicitacaoAdapter criancaSolicitacaoAdapter;

    private ArrayList<Crianca> criancasResult = new ArrayList<Crianca>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_amigos);

        searchView = findViewById(R.id.search_amigos);
        lista = findViewById(R.id.recyclerViewResultadoAmigos);

        LinearLayoutManager linearLayout = new LinearLayoutManager(SearchAmigos.this, LinearLayoutManager.VERTICAL, false);
        lista.setLayoutManager(linearLayout);

        final CollectionReference referenceUsuarios = ConfiguracaoFirebase.getFirestore().collection("Usuarios");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                criancasResult.clear();
                Query query = referenceUsuarios.whereEqualTo("nome", s).limit(1);
                query.get().addOnSuccessListener(SearchAmigos.this, new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots != null){
                            for (DocumentSnapshot document : documentSnapshots){
                               if (document.exists() && document != null){
                                   try {
                                           Crianca crianca = document.toObject(Crianca.class);
                                           crianca.setId(document.getId());
                                           criancasResult.add(crianca);


                                   }catch (Exception e){
                                       e.printStackTrace();
                                   }
                               }

                            }
                            criancaSolicitacaoAdapter = new CriancaSolicitacaoAdapter(criancasResult, SearchAmigos.this);
                            lista.setAdapter(criancaSolicitacaoAdapter);
                            //criancaSolicitacaoAdapter.notificar();
                        }
                    }
                });

                lista.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("DEBUG", "CLICOU");
                    }
                });

                query.get().addOnFailureListener(SearchAmigos.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals("")){

                }
                return false;
            }
        });

    }
}
