package com.softwares.jamelli.meuslivros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.softwares.jamelli.meuslivros.database.BancoHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BancoHelper db = new BancoHelper(this);

        final Button btn_cad = findViewById(R.id.btn_cad);
        final Button btn_listar = findViewById(R.id.btn_listar);
        btn_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CadastroLivroActivity.class);
                startActivity(i);
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
}
