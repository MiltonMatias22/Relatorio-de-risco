package hu.pe.nodout.relatorio_de_risco_11.Control;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Local;

/**
 * Created by Milton Matias on 03/02/2017.
 */
public class LocalControl {
    private Local local;
    public LocalControl(){
        local = new Local();
    }

    public int insert(Local local)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        ContentValues values = new ContentValues();
        values.put(Local.COLUNA_nome_Local,local.getNome_Local());
        values.put(Local.COLUNA_data_Local,local.getData_Local());
        int resultado = (int)db.insert(Local.NOME_TABELA_LOCAL,null,values);
        DataBaseManager.getInstance().closeDatabase();
        return resultado;
    }//fim insert()

    public static int update(Local local)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        ContentValues values = new ContentValues();
        values.put(Local.COLUNA_nome_Local, local.getNome_Local());
        values.put(Local.COLUNA_data_Local, local.getData_Local());
        int resultado = db.update(Local.NOME_TABELA_LOCAL, values, " _id = " + local.getId_Local(), null);
        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }//fim update()

    public static Cursor pesquisar(String nomeLocal){
        String sql = "SELECT * FROM "+Local.NOME_TABELA_LOCAL +
                " WHERE "+Local.COLUNA_nome_Local+" LIKE '" + nomeLocal +"%'";

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }//fim pesquisar()

    public static Cursor cursorListarTodos()throws Exception{
        String sql = "SELECT * FROM "+Local.NOME_TABELA_LOCAL;
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql,null);

        return cursor;
    }

    public static int delete(int _id)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        int resultado = db.delete(Local.NOME_TABELA_LOCAL, " _id = " + _id,null);
        DataBaseManager.getInstance().closeDatabase();
        return resultado;
    }
}
