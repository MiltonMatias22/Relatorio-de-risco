package hu.pe.nodout.relatorio_de_risco_11.Control;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Local;
import hu.pe.nodout.relatorio_de_risco_11.Model.Setor;

/**
 * Created by Milton Matias on 03/02/2017.
 */
public class SetorControl {
    private Setor setor;
    public SetorControl(){
        setor = new Setor();
    }

    public static int insert(Setor setor)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        ContentValues values = new ContentValues();
        values.put(Setor.COLUNA_nome_Setor, setor.getNome_Setor());
        values.put(Setor.COLUNA_quantidade_de_pessoas_setor,setor.getQuantidade_de_pessoas_setor());
        values.put(Setor.COLUNA_tipo_de_pessoas_setor,setor.getTipo_de_pessoas_setor());
        values.put(Setor.COLUNA_id_Local, setor.getId_Local());
        int resultado = (int)db.insert(Setor.NOME_TABELA_SETOR,null,values);
        DataBaseManager.getInstance().closeDatabase();
        return resultado;
    }//fim insert()

    public static int update(Setor setor)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        ContentValues values = new ContentValues();
        values.put(Setor.COLUNA_nome_Setor, setor.getNome_Setor());
        values.put(Setor.COLUNA_quantidade_de_pessoas_setor,setor.getQuantidade_de_pessoas_setor());
        values.put(Setor.COLUNA_tipo_de_pessoas_setor,setor.getTipo_de_pessoas_setor());
        values.put(Setor.COLUNA_id_Local, setor.getId_Local());
        int resultado = db.update(Setor.NOME_TABELA_SETOR, values, " _id = " + setor.getId_Setor(), null);
        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }//fim update()

    public static Setor pesquisar(int id_setor)throws Exception{
        Setor setor = new Setor();
        String sql = "SELECT * FROM "+Setor.NOME_TABELA_SETOR +
                " WHERE _id = " + id_setor;

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {
                setor.setId_Setor(cursor.getInt(0));
                setor.setNome_Setor(cursor.getString(1));
                setor.setQuantidade_de_pessoas_setor(cursor.getInt(2));
                setor.setTipo_de_pessoas_setor(cursor.getString(3));

            }while (cursor.moveToNext());
        }
        DataBaseManager.getInstance().closeDatabase();
        return setor;
    }//fim pesquisar()

    public static Cursor pesquisar(String nomeSetor,int id_local){
        String sql = "SELECT S._id, S.nome_Setor, L.nome_Local " +
                "FROM SETOR S JOIN LOCAL L ON L._id = S.id_Local " +
                "WHERE S.nome_Setor LIKE '"+nomeSetor+"%' AND S.id_Local = "+ id_local;

    SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
    Cursor cursor = db.rawQuery(sql, null);

    return cursor;
}//fim pesquisar()

    public static Cursor cursorListarTodos2(int id_Local)throws Exception{
        String sql = "SELECT S._id, S.nome_Setor, L.nome_Local " +
                "FROM SETOR S JOIN LOCAL L ON L._id = S.id_Local " +
                "WHERE S.id_Local = "+id_Local;
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }//fim cursorListarTodos()

    public static int delete(int _id)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        int resultado = db.delete(Setor.NOME_TABELA_SETOR," _id = " + _id,null);
        DataBaseManager.getInstance().closeDatabase();
        return resultado;
    }//fim delete()

}
