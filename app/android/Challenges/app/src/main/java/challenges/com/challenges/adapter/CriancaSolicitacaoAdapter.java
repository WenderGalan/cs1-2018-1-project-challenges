package challenges.com.challenges.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.model.Crianca;

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
    public void onBindViewHolder(ViewHolderSolicitacao holder, int position) {
        if (criancas != null && criancas.size() > 0){
            final Crianca crianca = criancas.get(position);
            if (crianca != null){
                holder.titulo.setText(crianca.getNome());
                if (crianca.getFoto() != null){
                    Picasso.get().load(crianca.getFoto()).into(holder.imagem);
                }
            }

            holder.aceitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Solicitação enviada com sucesso", Toast.LENGTH_LONG).show();
                    ((Activity)context).finish();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return criancas.size();
    }
}
