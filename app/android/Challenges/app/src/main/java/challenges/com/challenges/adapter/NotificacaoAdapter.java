package challenges.com.challenges.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.activities.NotificacoesActivity;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.Desafio;
import challenges.com.challenges.model.Notificacao;

public class NotificacaoAdapter extends RecyclerView.Adapter<ViewHolderNotificacao> {

    private ArrayList<Notificacao> notificacaoDesafio;

    public NotificacaoAdapter(ArrayList<Notificacao> notificacao) {
        this.notificacaoDesafio = notificacao;
    }

    @Override
    public ViewHolderNotificacao onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.row_notificacao, parent, false);

        ViewHolderNotificacao holderNotificacao = new ViewHolderNotificacao(view);

        return holderNotificacao;
    }

    @Override
    public void onBindViewHolder(final ViewHolderNotificacao holder, final int position) {

        if (notificacaoDesafio != null && notificacaoDesafio.size() > 0) {
            final Notificacao notificacao = notificacaoDesafio.get(position);

            holder.titulo.setText(notificacao.desafioObject.getTitulo());

            if (notificacao.desafioObject.getHabilidade() != null) {
                switch (notificacao.desafioObject.getHabilidade()){
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

                holder.aceitar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Desafio desafio = notificacao.desafioObject;
                        desafio.setChecado(true);
                        if (desafio.getFrequencia().equals("unica vez")){
                            ConfiguracaoFirebase.getFirestore().collection("Desafios").document(desafio.getId()).delete();
                        }
                        ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(desafio.getCrianca()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Crianca crianca = documentSnapshot.toObject(Crianca.class);
                                int pontos = crianca.getPontos();
                                crianca.setPontos(pontos + desafio.getPontos());
                                int recompensa = crianca.getRecompensa();
                                crianca.setRecompensa(recompensa + 1);
                                ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(desafio.getCrianca()).update(crianca.construirHash()).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Toast.makeText(holder.imagem.getContext(), "Desafio aceito com sucesso!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(holder.imagem.getContext(), NotificacoesActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        holder.imagem.getContext().startActivity(intent);
                                    }
                                });
                            }
                        });
                    }
                });

                holder.recusar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        }
    }

    @Override
    public int getItemCount() {
        return notificacaoDesafio.size();
    }
}
