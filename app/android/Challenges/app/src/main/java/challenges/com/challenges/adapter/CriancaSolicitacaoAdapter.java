package challenges.com.challenges.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.NotificacaoAmizade;

public class CriancaSolicitacaoAdapter extends RecyclerView.Adapter<ViewHolderSolicitacao> {

    private ArrayList<Crianca> criancas;
    Context context;

    public CriancaSolicitacaoAdapter(ArrayList<Crianca> criancas, Context context) {
        this.criancas = criancas;
        this.context = context;
    }

    @Override
    public ViewHolderSolicitacao onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_adicionar_amigo, parent, false);
        ViewHolderSolicitacao holder = new ViewHolderSolicitacao(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderSolicitacao holder, int position) {
        if (criancas != null && criancas.size() > 0){
            final Crianca crianca = criancas.get(position);
            if (crianca != null){
                holder.titulo.setText(crianca.getNome());
                if (crianca.getFoto() != null){
                    Picasso.get().load(crianca.getFoto()).into(holder.imagem);
                }
            }

            final NotificacaoAmizade notificacaoAmizade = new NotificacaoAmizade();
            notificacaoAmizade.setCrianca(ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(ConfiguracaoFirebase.getFirebaseAutenticacao().getCurrentUser().getUid()));
            notificacaoAmizade.setAmigo(ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(crianca.getId()));

            holder.aceitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CollectionReference notificacao = ConfiguracaoFirebase.getFirestore().collection("NotificacaoAmizade");
                    notificacao.add(notificacaoAmizade.construirHash()).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(context, "Solicitação enviada com sucesso", Toast.LENGTH_LONG).show();
                            holder.aceitar.setText("Solicitação enviada");
                            holder.aceitar.setEnabled(false);
                        }
                    });
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return criancas.size();
    }
}
