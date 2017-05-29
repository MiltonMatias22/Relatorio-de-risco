package hu.pe.nodout.relatorio_de_risco_11.Control;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Tecnico;

/**
 * Created by Milton Matias on 03/02/2017.
 */
public class TecnicoControl {
    private Tecnico tecnico;

    public TecnicoControl(){
        tecnico = new Tecnico();
    }

    public static int insert(Tecnico tecnico)throws Exception{

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();

        ContentValues values = new ContentValues();
        values.put(Tecnico.COLUNA_nome_Tecnico, tecnico.getNome_Tecnico());
        values.put(Tecnico.COLUNA_data_Tecnico,tecnico.getData_Tecnico());
        int resultado = (int)db.insert(Tecnico.NOME_TABELA_TECNICO,null,values);

        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }//fim insert()

    public static int update(Tecnico tecnico)throws Exception{

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();

        ContentValues values = new ContentValues();
        values.put(Tecnico.COLUNA_nome_Tecnico, tecnico.getNome_Tecnico());
        values.put(Tecnico.COLUNA_data_Tecnico,tecnico.getData_Tecnico());
        int resultado = db.update(Tecnico.NOME_TABELA_TECNICO,values," _id = " +
                tecnico.getId_Tecnico(),null);

        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }//fim update()

    public Tecnico pesquisar(int _id)throws Exception{
        Tecnico tecnico = new Tecnico();
        String sql = "SELECT * FROM "+Tecnico.NOME_TABELA_TECNICO +
                " WHERE _id = " + _id;

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql, null);

        tecnico.setId_Tecnico(cursor.getInt(0));
        tecnico.setNome_Tecnico(cursor.getString(1));

        DataBaseManager.getInstance().closeDatabase();
        return tecnico;
    }//fim pesquisar()

    public static Cursor cursorListarTodos()throws Exception{
        String sql = "SELECT * FROM "+Tecnico.NOME_TABELA_TECNICO;
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }//fim listarTodos()

    public static Cursor pesquisar(String nomeTecnico)throws Exception{
        String sql = "SELECT * FROM "+Tecnico.NOME_TABELA_TECNICO+" WHERE" +
                " nome_Tecnico LIKE '"+nomeTecnico+"%'";
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }//fim listarTodos()



    public static int delete(int id_tecnico)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        int resultado = db.delete(Tecnico.NOME_TABELA_TECNICO, " _id = " + id_tecnico,null);
        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }
}
