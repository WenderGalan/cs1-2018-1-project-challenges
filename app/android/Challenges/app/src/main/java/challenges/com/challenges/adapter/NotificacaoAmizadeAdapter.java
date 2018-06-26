package challenges.com.challenges.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.NotificacaoAmizade;

public class NotificacaoAmizadeAdapter extends RecyclerView.Adapter<ViewHolderNotificacaoAmizade> {

    private ArrayList<NotificacaoAmizade> objects;
    private Context context;

    public NotificacaoAmizadeAdapter(ArrayList<NotificacaoAmizade> objects, Context context) {
        this.objects = objects;
        this.context = context;
    }

    @Override
    public ViewHolderNotificacaoAmizade onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view  = layoutInflater.inflate(R.layout.row_notificacao, parent, false);
        ViewHolderNotificacaoAmizade holder = new ViewHolderNotificacaoAmizade(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderNotificacaoAmizade holder, final int position) {
        final Crianca amigo = new Crianca();
        final Crianca crianca = new Crianca();

        if (objects != null && objects.size() > 0 ){
            final NotificacaoAmizade amizade = objects.get(position);
            final DocumentReference amigoReference = amizade.getAmigo();
            amigoReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot != null){
                        Crianca amigo = documentSnapshot.toObject(Crianca.class);
                        amigo = amigo;
                    }
                }
            });

            final DocumentReference criancaReference = amizade.getCrianca();
            criancaReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot != null){
                        Crianca crianca = documentSnapshot.toObject(Crianca.class);
                        crianca = crianca;
                        holder.nome.setText(crianca.getNome());
                        if (crianca.getFoto() != null){
                            Picasso.get().load(crianca.getFoto()).into(holder.imagem);
                        }
                    }
                }
            });


            holder.confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //tem que add nos dois a amizade
                    ArrayList<DocumentReference> reference = new ArrayList<>();
                    reference.add(amigoReference);
                    amigo.setAmigos(reference);
                    amigoReference.update(amigo.construirHash()).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            ArrayList<DocumentReference> references = new ArrayList<>();
                            references.add(criancaReference);
                            crianca.setAmigos(references);
                            criancaReference.update(crianca.construirHash()).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {

                                }
                            });
                        }
                    });
                    ConfiguracaoFirebase.getFirestore().collection("NotificacaoAmizade").document(amizade.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("DEBUG", "EXCLUIU A NOTIFICAÇÃO");
                        }
                    });
                }
            });

            holder.recusar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConfiguracaoFirebase.getFirestore().collection("NotificacaoAmizade").document(amizade.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("DEBUG", "EXCLUIU A NOTIFICAÇÃO");
                        }
                    });
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
