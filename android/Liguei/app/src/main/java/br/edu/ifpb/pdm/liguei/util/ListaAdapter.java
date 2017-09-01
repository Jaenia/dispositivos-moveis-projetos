package br.edu.ifpb.pdm.liguei.util;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.edu.ifpb.pdm.liguei.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaAdapter extends BaseAdapter {
    private ContatoDAO dao;
    private Context context;

    public ListaAdapter(Context ctx, ContatoDAO dao){
        this.dao = dao;
        this.context = ctx;
    }

    @Override
    public int getCount() {
        return this.dao.size();
    }

    @Override
    public Object getItem(int position) {
        return this.dao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListaContatoViewHolder holder; // classe modelo pra só povoar os TextViews de dentro

        if (convertView != null) { // se já existisr uma view
            holder = (ListaContatoViewHolder) convertView.getTag();
        } else {
            LayoutInflater li = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.custom_list_item, null);
            holder = new ListaContatoViewHolder(convertView);
            convertView.setTag(holder);
        }

        if(position % 2 == 0){
            convertView.setBackgroundResource(R.color.colorListPar);
        } else
            convertView.setBackgroundResource(R.color.colorListImpar);

        holder.tvNome.setText(this.dao.get(position).getNome());
        holder.tvNumero.setText(this.dao.get(position).getNumero());

        return convertView;
    }


    static class ListaContatoViewHolder {
        @BindView(R.id.tvListaNome) TextView tvNome;
        @BindView(R.id.tvListaNumero) TextView tvNumero;

        public ListaContatoViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
