package com.softwares.jamelli.meuslivros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softwares.jamelli.meuslivros.database.BancoHelper;

import java.util.List;

public class ListarLivrosActivity extends AppCompatActivity {
    private int livroAtual;
    private List<Livro> livros;
    private TextView tv_tit;
    private TextView tv_aut;
    private TextView tv_ano;
    private TextView tv_nota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_livros);
        BancoHelper db = new BancoHelper(this);
        final Button btn_prox = findViewById(R.id.btn_prox);
        final Button btn_ant = findViewById(R.id.btn_ant);
        tv_tit = findViewById(R.id.tv1);
        tv_aut = findViewById(R.id.tv2);
        tv_ano = findViewById(R.id.tv3);
        tv_nota = findViewById(R.id.tv4);

        livros = db.findAll();
        livroAtual = 0;
        for (Livro l: livros) {
            Log.i("teste", l.toString());
        }
        alterarTextos();
        btn_ant.setEnabled(false);
        btn_ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(livroAtual < 1){
                    btn_ant.setEnabled(false);
                }else{
                    btn_prox.setEnabled(true);
                    btn_ant.setEnabled(true);
                    livroAtual--;
                    alterarTextos();
                }
            }
        });

        btn_prox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(livroAtual >= livros.size()-1){
                    btn_prox.setEnabled(false);
                }else{
                    btn_prox.setEnabled(true);
                    btn_ant.setEnabled(true);
                    livroAtual++;
                    alterarTextos();
                }

            }
        });

    }
    public void alterarTextos(){
        tv_tit.setText(livros.get(livroAtual).getTitulo());
        tv_aut.setText(livros.get(livroAtual).getAutor());
        tv_ano.setText(String.valueOf(livros.get(livroAtual).getAno()));
        tv_nota.setText(String.valueOf(livros.get(livroAtual).getNota()));
    }
}
