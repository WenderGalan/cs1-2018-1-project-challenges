package challenges.com.challenges.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.DesafioApp;

/**
 * Created by matheus on 03/06/18.
 */

public class DesafioAppAdapter extends RecyclerView.Adapter<ViewHolderDesafio> {

    private ArrayList<DesafioApp> desafioApps;
    private FirebaseAuth autenticacao;

    public DesafioAppAdapter(ArrayList<DesafioApp> desafiosApp) {
        this.desafioApps = desafiosApp;
    }

    @Override
    public ViewHolderDesafio onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.row_desafio, parent, false);

        ViewHolderDesafio holder = new ViewHolderDesafio(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderDesafio holder, final int position) {

        if (desafioApps != null && desafioApps.size() > 0) {
            final DesafioApp desafioApp = desafioApps.get(position);

            holder.titulo.setText(desafioApp.getTitulo());

            if (desafioApp.getHabilidade() != null) {
                switch (desafioApp.getHabilidade()){
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

            holder.titulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //cria uma dialog para criar notificação de desafio
                    Toast.makeText(holder.imagem.getContext(), "Clicou no titulo", Toast.LENGTH_SHORT).show();
                    abrirDialog(holder.imagem.getContext(), holder.titulo.getText().toString(), desafioApp);
                }
            });
            holder.imagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //cria uma dialog para criar notificação de desafio
                    Toast.makeText(holder.imagem.getContext(), "Clicou na imagem", Toast.LENGTH_SHORT).show();
                    abrirDialog(holder.imagem.getContext(), holder.titulo.getText().toString(), desafioApp);

                }
            });
        }
    }

    private void abrirDialog(final Context context, String titulo, final DesafioApp desafioApp) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(titulo);
        alertDialog.setMessage("Você tem interesse neste desafio?");

        Button button = new Button(context);
        button.setBackgroundResource(R.drawable.fundo_botao_verde);
        button.setText("Enviar Solicitação!");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        button.setGravity(1);
        button.setLayoutParams(lp);
        //passando ao Alert Dialog o Button
        alertDialog.setView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                String idUsuarioAtual = autenticacao.getCurrentUser().getUid();
                DocumentReference crianca = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuarioAtual);
                desafioApp.setCrianca(crianca);

                //coloquei um listener para ficar escutando as alteraçoes do banco porque é usuário...
                ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuarioAtual)
                        .addSnapshotListener((Activity) context, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                                if (documentSnapshot.exists()) {
                                    Crianca resultCrianca;
                                    resultCrianca = documentSnapshot.toObject(Crianca.class);
                                    desafioApp.setResponsavel(resultCrianca.getResponsavel().getId());
                                }
                            }
                        });

                DocumentReference desafioAppDocument = ConfiguracaoFirebase.getFirestore().collection("DesafioApp").document(desafioApp.getId());
                desafioApp.setDesafio(desafioAppDocument);

                //enviar a notificação!
                ConfiguracaoFirebase.getFirestore().collection("NotificacaoDesafioApp").document()
                        .set(desafioApp.construirHash()).addOnSuccessListener((Activity) context, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(context, "Enviado com sucesso", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return desafioApps.size();
    }
}
