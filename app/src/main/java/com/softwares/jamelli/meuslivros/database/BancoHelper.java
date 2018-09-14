package com.softwares.jamelli.meuslivros.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.softwares.jamelli.meuslivros.Livro;

import java.util.ArrayList;
import java.util.List;

public class BancoHelper extends SQLiteOpenHelper {
    //String auxiliares
    private static final String TAG = "sql";
    private static final String TEXT_TYPE = " TEXT";
    private static final String NUMBER_TYPE = " INTEGER";
    private static final String VIRGULA = ",";

    // Nome do banco
    private static final String DATABASE_NAME = "banco_livros.sqlite";

    //versão do banco
    private static final int DATABASE_VERSION = 1;
    //SQLs
    private static final String SQL_CREATE_TABLE =
            ("CREATE TABLE " + LivroContrato.LivroEntry.TABLE_NAME +
                    "("+
                    LivroContrato.LivroEntry._ID + NUMBER_TYPE+  " PRIMARY KEY"+ VIRGULA+
                    LivroContrato.LivroEntry.TITULO + TEXT_TYPE + VIRGULA+
                    LivroContrato.LivroEntry.AUTOR + TEXT_TYPE + VIRGULA+
                    LivroContrato.LivroEntry.ANO + NUMBER_TYPE + VIRGULA+
                    LivroContrato.LivroEntry.NOTA + NUMBER_TYPE+
                    ");"
            );
    private static final String SQL_DROP_TABLE =
            ("DROP TABLE "+ LivroContrato.LivroEntry.TABLE_NAME+";"
            );

    public  BancoHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Não foi possível acessar o banco, criando um novo...");
        Log.d(TAG, SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
        Log.d(TAG, "Banco criado com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Caso mude a versãoo do banco de dados, podemos executar um SQL aqui
        if (oldVersion != newVersion) {
            // Execute o script para atualizar a versão...
            Log.d(TAG, "Foi detectada uma nova versão do banco, aqui deverão ser executados os scripts de update.");
            sqLiteDatabase.execSQL(SQL_DROP_TABLE);
            this.onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void  onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion != newVersion) {
            // Execute o script para atualizar a versão...
            Log.d(TAG, "Foi detectada uma nova versão do banco, aqui deverão ser executados os scripts de downgrade.");
            db.execSQL(SQL_DROP_TABLE);
            this.onCreate(db);
        }
    }

    //cadastrando novos livros
    /*public long save(Livro l){
        long id = l.getId();
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(LivroContrato.LivroEntry.TITULO, l.getTitulo());
            cv.put(LivroContrato.LivroEntry.AUTOR, l.getAutor());
            cv.put(LivroContrato.LivroEntry.ANO, l.getAno());
            cv.put(LivroContrato.LivroEntry.NOTA, l.getNota());



            id = db.insert(LivroContrato.LivroEntry.TABLE_NAME, null, cv);
            Log.i(TAG, "Inseriu id [" + id + "] no banco.");
            return id;
        }finally {
            db.close();
        }
    }*/

    public long save(Livro livro){
        long id = livro.getId();
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(LivroContrato.LivroEntry.TITULO,livro.getTitulo());
            values.put(LivroContrato.LivroEntry.AUTOR,livro.getAutor());
            values.put(LivroContrato.LivroEntry.ANO,livro.getAno());
            values.put(LivroContrato.LivroEntry.NOTA,livro.getNota());

            if (id != 0) {
                String selection = LivroContrato.LivroEntry._ID + "= ?";
                String[] whereArgs = new String[]{String.valueOf(id)};

                int count = db.update(LivroContrato.LivroEntry.TABLE_NAME, values, selection, whereArgs);
                Log.i(TAG, "Atualizou id [" + id + "] no banco.");
                return count;

            } else {
                id = db.insert(LivroContrato.LivroEntry.TABLE_NAME, null, values);
                Log.i(TAG, values.toString());
                Log.i(TAG, "Inseriu id [" + id + "] no banco.");
                return id;
            }

        }finally {
            db.close();
        }
    }

    //Converte cada cursor retornado pelo getReadableDatabase() em objetos de livros para a lista de livros
    private List<Livro> convList(Cursor c){
        List<Livro> livros = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                Livro l = new Livro();
                l.setId(c.getInt(c.getColumnIndex(LivroContrato.LivroEntry._ID)));
                l.setTitulo(c.getString(c.getColumnIndex(LivroContrato.LivroEntry.TITULO)));
                l.setAutor(c.getString(c.getColumnIndex(LivroContrato.LivroEntry.AUTOR)));
                l.setAno(c.getInt(c.getColumnIndex(LivroContrato.LivroEntry.ANO)));
                l.setNota(c.getFloat(c.getColumnIndex(LivroContrato.LivroEntry.NOTA)));
                livros.add(l);
            }while (c.moveToNext());
        }
        return livros;
    }

    //listando todos os livros
    public List<Livro> findAll(){
        SQLiteDatabase db = getReadableDatabase();
        try{
            Cursor c = db.query(LivroContrato.LivroEntry.TABLE_NAME, null, null, null, null, null, null, null);
            return convList(c);
        }finally {
            db.close();
        }
    }

    // Executa um SQL
    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } finally {
            db.close();
        }
    }

}
