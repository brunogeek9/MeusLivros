package com.softwares.jamelli.meuslivros;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.softwares.jamelli.meuslivros.database.BancoHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    final int CODE_SAVE = 22;
    private ConstraintLayout tela;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BancoHelper db = new BancoHelper(this);
        tela = findViewById(R.id.tela);
        final Button btn_cad = findViewById(R.id.btn_cad);
        final Button btn_listar = findViewById(R.id.btn_listar);
        btn_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CadastroLivroActivity.class);
                startActivityForResult(i,CODE_SAVE);

            }
        });
        btn_listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ListarLivrosActivity.class);
                startActivity(i);
            }
        });
        Livro l1 = new Livro("Teste","seila",2018,4f);
        db.save(l1);

        List<Livro> lista = db.findAll();
        for (Livro l: lista) {
            Log.i("teste", l.toString());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_SAVE && resultCode == RESULT_OK){
            Snackbar snack = Snackbar.make(tela, R.string.txt_snack, Snackbar.LENGTH_LONG);
            snack.show();
        }
    }
}
