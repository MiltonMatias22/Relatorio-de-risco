package hu.pe.nodout.relatorio_de_risco_11.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hu.pe.nodout.relatorio_de_risco_11.Control.InspecaoControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.LocalControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.RiscoControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.SetorControl;
import hu.pe.nodout.relatorio_de_risco_11.Control.TecnicoControl;
import hu.pe.nodout.relatorio_de_risco_11.Model.Inspecao;
import hu.pe.nodout.relatorio_de_risco_11.Model.Local;
import hu.pe.nodout.relatorio_de_risco_11.Model.Risco;
import hu.pe.nodout.relatorio_de_risco_11.Model.Setor;
import hu.pe.nodout.relatorio_de_risco_11.Model.Tecnico;

/**
 * Created by Milton Matias on 03/02/2017.
 */
public class DBHelper extends SQLiteOpenHelper{
    //Versão do banco de dados
    private static final int DATABASE_VERSION = 5;
    // nome do banco de dados
    private static final String DATABASE_NAME = "DB_RISCO";
    private static final String TAG = DBHelper.class.getSimpleName().toString();

    public DBHelper() {//OBS: passar o this como contexto!
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //criando todas as tabelas
        //db.execSQL("PRAGMA foreign_keys = ON;");
        db.execSQL(Inspecao.criarTabela());
        db.execSQL(Local.criarTabela());
        db.execSQL(Risco.criarTabela());
        db.execSQL(Setor.criarTabela());
        db.execSQL(Tecnico.criarTabela());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Deletar o conteúdo de todas as tabelas
        db.execSQL("DROP TABLE IF EXISTS " + Inspecao.NOME_TABELA_INSPECAO);
        db.execSQL("DROP TABLE IF EXISTS " + Local.NOME_TABELA_LOCAL);
        db.execSQL("DROP TABLE IF EXISTS " + Risco.NOME_TABELA_RISCO);
        db.execSQL("DROP TABLE IF EXISTS " + Setor.NOME_TABELA_SETOR);
        db.execSQL("DROP TABLE IF EXISTS " + Tecnico.NOME_TABELA_TECNICO);
        onCreate(db);
    }
}
