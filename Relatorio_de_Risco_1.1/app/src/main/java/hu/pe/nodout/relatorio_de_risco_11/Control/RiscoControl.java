package hu.pe.nodout.relatorio_de_risco_11.Control;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Risco;
import hu.pe.nodout.relatorio_de_risco_11.Model.Risco_setor;
import hu.pe.nodout.relatorio_de_risco_11.Model.Setor;

/**
 * Created by Milton Matias on 03/02/2017.
 */
public class RiscoControl {
    private Risco risco;

    public RiscoControl(){
        risco = new Risco();
    }

    public static int insert(Risco risco)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        ContentValues values = new ContentValues();
        values.put(Risco.COLUNA_tipo_Risco, risco.getTipo_Risco());
        values.put(Risco.COLUNA_nome_Agentes_Causadores, risco.getNome_Agentes_Causadores());
        values.put(Risco.COLUNA_grau_risco, risco.getGrau_risco());
        values.put(Risco.COLUNA_recomendacoes_risco, risco.getRecomendacoes_risco());
        values.put(Risco.COLUNA_data_risco, risco.getData_risco());
        values.put(Risco.COLUNA_id_Inspecao, risco.getId_inspecao());
        int resultado = (int)db.insert(Risco.NOME_TABELA_RISCO,null,values);
        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }//fim insert()

    public static int update(Risco risco)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        ContentValues values = new ContentValues();
        values.put(Risco.COLUNA_tipo_Risco, risco.getTipo_Risco());
        values.put(Risco.COLUNA_nome_Agentes_Causadores, risco.getNome_Agentes_Causadores());
        values.put(Risco.COLUNA_grau_risco, risco.getGrau_risco());
        values.put(Risco.COLUNA_recomendacoes_risco, risco.getRecomendacoes_risco());
        values.put(Risco.COLUNA_data_risco, risco.getData_risco());
        values.put(Risco.COLUNA_id_Inspecao, risco.getId_inspecao());
        int resultado = db.update(Risco.NOME_TABELA_RISCO, values, " _id = " + risco.getId_Risco(), null);
        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }//fim update()

    public static Risco pesquisar(int id_risco){
        Risco risco = new Risco();
        String sql = "SELECT _id,tipo_Risco,nome_Agentes_Causadores,grau_risco," +
                "recomendacoes_risco,data_risco FROM "+Risco.NOME_TABELA_RISCO+
                " WHERE _id = " + id_risco;

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                risco.setId_Risco(cursor.getInt(0));
                risco.setTipo_Risco(cursor.getString(1));
                risco.setNome_Agentes_Causadores(cursor.getString(2));
                risco.setGrau_risco(cursor.getString(3));
                risco.setRecomendacoes_risco(cursor.getString(4));
                risco.setData_risco(cursor.getString(5));
            }while (cursor.moveToNext());
        }

        DataBaseManager.getInstance().closeDatabase();

        return risco;
    }//fim pesquisar()

    /**
     * Método para listar os risco de uma inspeção
     * para o relatório de riso, usodado pela classe
     * RelatorioHtml
     * */
    public  static List<Risco> listarTodos(int id_inspecao){
        List<Risco> listaRisco = new ArrayList<Risco>();
        String sql = "SELECT tipo_Risco,nome_Agentes_Causadores,grau_risco," +
                "recomendacoes_risco,data_risco FROM RISCO WHERE id_Inspecao = "+id_inspecao;
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                Risco risco = new Risco();

                risco.setTipo_Risco(cursor.getString(0));
                risco.setNome_Agentes_Causadores(cursor.getString(1));
                risco.setGrau_risco(cursor.getString(2));
                risco.setRecomendacoes_risco(cursor.getString(3));
                risco.setData_risco(cursor.getString(4));
                listaRisco.add(risco);

            }while (cursor.moveToNext());
        }

        return listaRisco;
    }

    public static Cursor cursorlistarTodos(int id_inspecao){

        String sql = "SELECT _id,tipo_Risco,nome_Agentes_Causadores,grau_risco," +
                "recomendacoes_risco,data_risco FROM RISCO WHERE id_Inspecao = "+id_inspecao;
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql,null);

        return cursor;
    }//fim listarTodos()

    public static int Contador(int id_inspecao){

        String sql = "SELECT COUNT(_id)FROM RISCO WHERE id_Inspecao = "+id_inspecao;
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql, null);
        int contador = 0;
        if(cursor.moveToFirst()){
            do {
                contador = cursor.getInt(0);

            }while (cursor.moveToNext());
        }

        return contador;
    }//fim listarTodos()

    public static int delete(int id_risco)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        int resultado = db.delete(Risco.NOME_TABELA_RISCO, " _id = " + id_risco, null);
        DataBaseManager.getInstance().closeDatabase();
        return resultado;
    }//fim delete()

    public static int deletarTudos(int id_inspecao)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        int resultado = db.delete(Risco.NOME_TABELA_RISCO, " id_inspecao = " + id_inspecao, null);
        DataBaseManager.getInstance().closeDatabase();
        return resultado;
    }//fim delete()
}
