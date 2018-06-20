package challenges.com.challenges.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.activities.HomeResponsavelActivity;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Notificacao;

public class NotificacaoAdapter extends RecyclerView.Adapter<ViewHolderNotificacao> {

    private ArrayList<Notificacao> notificacaoDesafio;
    Context context;

    public NotificacaoAdapter(ArrayList<Notificacao> notificacao, Context context) {
        this.notificacaoDesafio = notificacao;
        this.context = context;
    }

    @Override
    public ViewHolderNotificacao onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_notificacao, parent, false);
        ViewHolderNotificacao holder = new ViewHolderNotificacao(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderNotificacao holder, int position) {

        if (notificacaoDesafio != null && notificacaoDesafio.size() > 0) {
            final Notificacao notificacao = notificacaoDesafio.get(position);

            holder.titulo.setText(notificacao.getDesafioObject().getTitulo());

            if (notificacao.getDesafioObject().getHabilidade() != null) {
                switch (notificacao.getDesafioObject().getHabilidade()) {
                    case "intelectual":
                        holder.imagem.setImageResource(R.drawable.habilidade_inteligencia);
                        break;
                    case "fisica":
                        holder.imagem.setImageResource(R.drawable.habilidade_fisica);
                        break;
                    case "criatividade":
                        holder.imagem.setImageResource(R.drawable.habilidade_criatividade);
                        break;
                    case "social":
                        holder.imagem.setImageResource(R.drawable.habilidade_social);
                        break;
                }
            }

            holder.aceitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO aceitar o desafio, excluir a notificação e adicionar os pontos e a recompensa para a crianca
                    Toast.makeText(holder.imagem.getContext(), "Clicou confirmar", Toast.LENGTH_LONG).show();

                    if (notificacao.getDesafioObject().getFrequencia().equals("unica vez")) {

                    }
                }
            });

            holder.recusar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO recusar o desafio, excluir a notificacao
                    Toast.makeText(holder.imagem.getContext(), "Clicou recusar", Toast.LENGTH_LONG).show();

                    recusarDesafio(notificacao, holder.imagem.getContext());
                }
            });

        }

    }

    private void recusarDesafio(final Notificacao notificacao, final Context context) {
        DocumentReference desafioNegado = ConfiguracaoFirebase.getFirestore().collection("Desafios").document(notificacao.getDesafio().getId());
        desafioNegado.update("completado", false).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess (Void aVoid){
                ConfiguracaoFirebase.getFirestore().collection("NotificacoesDesafioCompleto")
                        .document(notificacao.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(context, "Desafio Recusado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, HomeResponsavelActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificacaoDesafio.size();
    }
}
