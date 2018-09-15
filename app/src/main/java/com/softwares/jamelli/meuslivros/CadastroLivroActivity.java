package com.softwares.jamelli.meuslivros;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.softwares.jamelli.meuslivros.database.BancoHelper;

public class CadastroLivroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);
        final Button btn_salvar = findViewById(R.id.btn_salvar);
        final Button btn_canc = findViewById(R.id.btn_cancelar);
        final EditText et_titulo = findViewById(R.id.et_titulo);
        final EditText et_autor = findViewById(R.id.et_autor);
        final EditText et_ano = findViewById(R.id.et_ano);
        final RatingBar rb = findViewById(R.id.rb_nota);
        final BancoHelper db = new BancoHelper(this);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Livro l = new Livro(
                        et_titulo.getText().toString(),
                        et_autor.getText().toString(),
                        Integer.valueOf(et_ano.getText().toString()),
                        rb.getRating()
                );
                db.save(l);
                Log.i("teste",l.toString());
                Snackbar sb = Snackbar.make( (View) view.getParent(),R.string.txt_snack,Snackbar.LENGTH_LONG)
                        .setAction(R.string.canc_snack, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(CadastroLivroActivity.this,"cancelou",Toast.LENGTH_SHORT).show();
                            }
                        });
                sb.show();
                finish();
            }
        });


        btn_canc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });
    }
}
