package challenges.com.challenges.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.activities.CriancaDetalhesActivity;
import challenges.com.challenges.model.Crianca;

public class CriancaAdapter extends RecyclerView.Adapter<ViewHolderCrianca> {

    private ArrayList<Crianca> criancas;
    private String tipo;

    public CriancaAdapter(ArrayList<Crianca> criancas, String tipo){
        this.criancas = criancas;
        this.tipo = tipo;
    }


    @Override
    public ViewHolderCrianca onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.row_crianca, parent, false);

        ViewHolderCrianca holder = new ViewHolderCrianca(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderCrianca holder, final int position) {

        if (criancas != null && criancas.size() > 0) {
            final Crianca crianca = criancas.get(position);

            holder.nome.setText(crianca.getNome());
            if (crianca.getFoto() != null) {
                Picasso.get().load(crianca.getFoto()).into(holder.imagem);
            }
            //listeners dos botoes
            holder.nome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //chamar o metodo
                    abrirDetalhesCriança(criancas.get(position), holder.imagem.getContext());
                }
            });
            holder.imagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //chamar o metodo
                    abrirDetalhesCriança(criancas.get(position), holder.imagem.getContext());
                }
            });
        }
    }

        private void abrirDetalhesCriança (Crianca crianca, Context context){
            Intent intent = new Intent(context, CriancaDetalhesActivity.class);
            intent.putExtra("id", crianca.getId());
            context.startActivity(intent);
        }

        @Override
        public int getItemCount () {
            return criancas.size();
        }


}
