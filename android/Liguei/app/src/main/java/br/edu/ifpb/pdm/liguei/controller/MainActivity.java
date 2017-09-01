package br.edu.ifpb.pdm.liguei.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifpb.pdm.liguei.R;
import br.edu.ifpb.pdm.liguei.model.Contato;
import br.edu.ifpb.pdm.liguei.util.ContatoDAO;
import br.edu.ifpb.pdm.liguei.util.ListaAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final int EDITAR_CONTATO = 1001;
    public static final int NOVO_CONTATO = 1010;
    private ContatoDAO dao;
    @BindView(R.id.lvMainNumeros) ListView listView;
    @BindView(R.id.tvMainSemNumeros)
    TextView tvVazio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new FabButtonListener());

        ButterKnife.bind(this);

        this.dao = new ContatoDAO(this);
        ListaAdapter adapter = new ListaAdapter(this, this.dao);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ClickItem());
        listView.setOnItemLongClickListener(new LongClickItem());
        listView.setEmptyView(tvVazio);

    }

    private void updateListView() {
        ((BaseAdapter) this.listView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_zerar) {
            for(Contato c : this.dao.get()){
                this.dao.delete(c);
            }
            this.updateListView();
            Toast.makeText(this, "Todos os contatos foram apagados!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == EDITAR_CONTATO) {
                int ctPosicao = data.getIntExtra("POSICAO", -1);
                try {
                    Contato ctOriginal = this.dao.get(ctPosicao);
                    Contato ctAlterado = (Contato) data.getSerializableExtra("CONTATO");
                    this.dao.update(ctOriginal, ctAlterado);
                    this.updateListView();
                } catch (Exception e) {
                    this.dao.delete(this.dao.get(ctPosicao));
                    this.updateListView();
                }
            } else
                if(requestCode == NOVO_CONTATO) {
                    Contato ct = (Contato) data.getSerializableExtra("CONTATO");
                    this.dao.insert(ct);
                    this.updateListView();
                }
        }
    }

    private class LongClickItem implements android.widget.AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Intent it = new Intent(MainActivity.this, CadastroActivity.class);
            it.putExtra("CONTATO", MainActivity.this.dao.get(position));
            it.putExtra("POSICAO", position);
            startActivityForResult(it, EDITAR_CONTATO);
            return true;
        }
    }

    private class ClickItem implements android.widget.AdapterView.OnItemClickListener {
        private AlertDialog ad;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder ab;
            Contato ct = MainActivity.this.dao.get(position);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ab = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Light_Dialog_NoActionBar);
            } else {
                ab = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
            }

            ab.setTitle(String.format("Ligar para %s?",ct.getNumero()))
                    .setMessage(String.format("%s: %s\n\nVocê será levado ao discador após confirmar. Deseja ligar para %s?",ct.getNome(), ct.getDescricao(), ct.getNome()))
                    .setPositiveButton("LIGAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent it = new Intent(Intent.ACTION_DIAL);
                            it.setData(Uri.parse("tel:"+MainActivity.this.dao.get(position).getNumero()));
                            startActivity(it);
                        }
                    })
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ad.dismiss();
                        }
                    });
            ad = ab.create();
            ad.show();
        }
    }

    private class FabButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(MainActivity.this, CadastroActivity.class);
            startActivityForResult(it, NOVO_CONTATO);
        }
    }
}

