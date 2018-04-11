package challenges.com.challenges.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.model.Crianca;
import de.hdodenhof.circleimageview.CircleImageView;

public class CriancaAdapter extends ArrayAdapter<Crianca> {

    private LayoutInflater inflater;
    private Context c;
    private ArrayList<Crianca> criancas;


    public CriancaAdapter(Context context, int resource, ArrayList<Crianca> objects) {
        super(context, resource, objects);
        this.c = context;
        this.criancas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (criancas != null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_crianca, parent, false);

            CircleImageView imagem = view.findViewById(R.id.circleImageViewCrianca);
            TextView nome = view.findViewById(R.id.textViewNomeCrianca);

            Crianca crianca = criancas.get(position);
            if (crianca != null){
                if (crianca.getFoto() != null){
                    Picasso.with(getContext()).load(crianca.getFoto()).into(imagem);
                }
                nome.setText(crianca.getNome());
            }
        }
        return view;
    }
}
