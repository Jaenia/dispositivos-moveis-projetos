package br.edu.ifpb.pdm.liguei.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.edu.ifpb.pdm.liguei.R;
import br.edu.ifpb.pdm.liguei.model.Contato;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroActivity extends AppCompatActivity {
    private Contato contato;
    private int posicao;

    @BindView(R.id.etCadastroNome) EditText etNome;
    @BindView(R.id.etCadastroNumero) EditText etNumero;
    @BindView(R.id.etCadastroDescricao) EditText etDesc;
    @BindView(R.id.fabCadastro) FloatingActionButton fabCadastro;
    @BindView(R.id.btCadastroSalvar) Button btSalvar;
    @BindView(R.id.btCadastroExcluir) Button btExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCadastro);
        fab.setOnClickListener(new FabButtonClick());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        fabCadastro.setVisibility(View.VISIBLE);
        btSalvar.setVisibility(View.GONE);
        btExcluir.setVisibility(View.GONE);

        try{
            Intent it = getIntent();
            this.contato = (Contato) it.getSerializableExtra("CONTATO");
            this.posicao = (int) it.getSerializableExtra("POSICAO");

            if(this.contato != null) {
                this.etNome.setText(this.contato.getNome());
                this.etNumero.setText(this.contato.getNumero());
                this.etDesc.setText(this.contato.getDescricao());
                this.fabCadastro.setVisibility(View.GONE);
                btExcluir.setVisibility(View.VISIBLE);
                btSalvar.setVisibility(View.VISIBLE);

                btSalvar.setOnClickListener(new FabButtonClick());
                btExcluir.setOnClickListener(new FabButtonClick());
            }

        } catch (Exception e){

        }

    }

    private class FabButtonClick implements View.OnClickListener {
        Intent it = new Intent(CadastroActivity.this, MainActivity.class);

        @Override
        public void onClick(View v) {
            if(v != btExcluir) {
                if (CadastroActivity.this.contato != null) {
                    CadastroActivity.this.contato = this.getContato();
                    it.putExtra("CONTATO", CadastroActivity.this.contato);
                } else {
                    it.putExtra("CONTATO", this.getContato());
                }
                it.putExtra("POSICAO", CadastroActivity.this.posicao);
                setResult(RESULT_OK, it);
                finish();
            } else {
                it.putExtra("POSICAO", CadastroActivity.this.posicao);
                setResult(RESULT_OK, it);
                finish();
            }
        }

        private Contato getContato() {
            Contato ct = new Contato();
            CadastroActivity ac = CadastroActivity.this;
            ct.setNome(!ac.etNome.getText().toString().isEmpty() ? ac.etNome.getText().toString() : "Polícia");
            ct.setNumero(!ac.etNumero.getText().toString().isEmpty() ? ac.etNumero.getText().toString() : "190");
            ct.setDescricao(!ac.etDesc.getText().toString().isEmpty() ? ac.etDesc.getText().toString() : "");

            return ct;
        }
    }

}
