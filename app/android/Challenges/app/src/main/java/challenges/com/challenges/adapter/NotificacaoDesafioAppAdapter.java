package challenges.com.challenges.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import challenges.com.challenges.R;
import challenges.com.challenges.activities.DesafioDetalhesActivity;
import challenges.com.challenges.activities.HomeCriancaActivity;
import challenges.com.challenges.activities.HomeResponsavelActivity;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Desafio;
import challenges.com.challenges.model.Notificacao;
import challenges.com.challenges.model.NotificacaoDesafioApps;

/**
 * Created by matheus on 20/06/18.
 */

public class NotificacaoDesafioAppAdapter extends RecyclerView.Adapter<ViewHolderNotificacao> {

    private ArrayList<NotificacaoDesafioApps> notificacaoDesafioApp;
    private Context context;

    public NotificacaoDesafioAppAdapter(ArrayList<NotificacaoDesafioApps> notificacaoDesafioApp, Context context) {
        this.notificacaoDesafioApp = notificacaoDesafioApp;
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

        if (notificacaoDesafioApp != null && notificacaoDesafioApp.size() > 0) {
            final NotificacaoDesafioApps notificacao = notificacaoDesafioApp.get(position);

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
                    aceitarDesafio(notificacao, holder.imagem.getContext());
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

    private void recusarDesafio(NotificacaoDesafioApps notificacao, final Context context) {
        ConfiguracaoFirebase.getFirestore().collection("NotificacaoDesafioApp")
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

    private void aceitarDesafio(final NotificacaoDesafioApps notificacao, final Context context) {
        Calendar calendar= Calendar.getInstance();
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        Desafio desafio = new Desafio();
        desafio.setChecado(false);
        desafio.setCompletado(false);
        desafio.setCrianca(notificacao.getCrianca().getId());
        desafio.setResponsavel(notificacao.getResponsavel());
        desafio.setObservacoes(null);
        desafio.setHabilidade(notificacao.getDesafioObject().getHabilidade());
        desafio.setRepeticoes(notificacao.getDesafioObject().getRepeticoes());
        desafio.setFrequencia(notificacao.getDesafioObject().getFrequencia());
        desafio.setTitulo(notificacao.getDesafioObject().getTitulo());
        desafio.setPontos(notificacao.getDesafioObject().getPontos());
        desafio.setDataCriacaoTimestamp(timestamp);
        desafio.setDataUpdateTimestamp(timestamp);
        desafio.setResponsavel(null);

        ConfiguracaoFirebase.getFirestore().collection("Desafios").document()
                .set(desafio.construirHash()).addOnSuccessListener((Activity) context, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(context, "Desafio aceito", Toast.LENGTH_LONG).show();
                ConfiguracaoFirebase.getFirestore().collection("NotificacaoDesafioApps")
                        .document(notificacao.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
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
        return notificacaoDesafioApp.size();
    }
}
