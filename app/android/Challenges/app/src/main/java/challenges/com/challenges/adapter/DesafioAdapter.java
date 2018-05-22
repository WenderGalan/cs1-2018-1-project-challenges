package challenges.com.challenges.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.activities.DesafioDetalhesActivity;
import challenges.com.challenges.model.Desafio;

public class DesafioAdapter extends RecyclerView.Adapter<ViewHolderDesafio> {

    private ArrayList<Desafio> desafios;
    private String tipo;

    public DesafioAdapter(ArrayList<Desafio> desafios, String tipo){
        this.desafios = desafios;
        this.tipo = tipo;
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

        if (desafios != null && desafios.size() > 0) {
            final Desafio desafio = desafios.get(position);

            holder.titulo.setText(desafio.getTitulo());

            if (desafio.getHabilidade() != null) {
                switch (desafio.getHabilidade()){
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
                holder.titulo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //chamar o metodo
                        abrirDetalhesDesafio(desafios.get(position), holder.imagem.getContext());
                    }
                });
                holder.imagem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //chamar o metodo
                        abrirDetalhesDesafio(desafios.get(position), holder.imagem.getContext());

                    }
                });

            }
        }
    }


    private void abrirDetalhesDesafio(Desafio desafio, Context context) {
        Intent intent = new Intent(context, DesafioDetalhesActivity.class);
        intent.putExtra("desafio", desafio);
        intent.putExtra("tipo", tipo);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount () {
        return desafios.size();
    }


}
