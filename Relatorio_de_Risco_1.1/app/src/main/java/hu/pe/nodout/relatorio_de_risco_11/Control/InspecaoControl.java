package hu.pe.nodout.relatorio_de_risco_11.Control;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import hu.pe.nodout.relatorio_de_risco_11.DataBase.DataBaseManager;
import hu.pe.nodout.relatorio_de_risco_11.Model.Inspecao;
import hu.pe.nodout.relatorio_de_risco_11.Model.Inspecao_tecnico;
import hu.pe.nodout.relatorio_de_risco_11.Model.Local;
import hu.pe.nodout.relatorio_de_risco_11.Model.Tecnico;

/**
 * Created by Milton Matias on 03/02/2017.
 */
public class InspecaoControl {

    public static int insert(Inspecao inspecao)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        int resultado  = (int) db.insert(Inspecao.NOME_TABELA_INSPECAO, null,
                contentValuesIspecao(inspecao));
        //fechando a conexão com o banco!
        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }//fim insert();

    @NonNull
    private static ContentValues contentValuesIspecao(Inspecao inspecao) {
        ContentValues values = new ContentValues();
        values.put(Inspecao.COLUNA_data_Inicio_Inspecao, inspecao.getData_Inicio_Inspecao());
        values.put(Inspecao.COLUNA_data_Fim_Inspecao, inspecao.getData_Fim_Inspecao());
        values.put(Inspecao.COLUNA_id_Tecnico, inspecao.getId_Tecnico());
        values.put(Inspecao.COLUNA_id_Setor, inspecao.getId_Setor());
        return values;
    }

    public static int update(Inspecao inspecao)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        String id_inspecao = String.valueOf(inspecao.getId_Inspecao());
        String [] parametros = {id_inspecao};
        int resultado = db.update(Inspecao.NOME_TABELA_INSPECAO,
                contentValuesIspecao(inspecao)," _id = ?",parametros);

        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }//fim update()

    public Inspecao pesquisar(int id_inspecao){
        Inspecao inspecao = new Inspecao();
        String sql = "SELECT * FROM "+Inspecao.NOME_TABELA_INSPECAO+
                " WHERE _id = ?";

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();

        String [] parametros = {String.valueOf(id_inspecao)};

        Cursor cursor = db.rawQuery(sql, parametros);
        while (cursor.moveToNext()) {
            inspecao.setId_Inspecao(cursor.getInt(0));
            inspecao.setData_Inicio_Inspecao(cursor.getString(1));
            inspecao.setData_Fim_Inspecao(cursor.getString(2));
            inspecao.setId_Tecnico(cursor.getInt(3));
            inspecao.setId_Setor(cursor.getInt(4));
        }

        DataBaseManager.getInstance().closeDatabase();

        return inspecao;
    }//fim pesquisar()

    public static Cursor cursorPesquisa(int id_inspecao){
        String sql = "SELECT I._id, I.data_Inicio_Inspecao, I.data_Fim_Inspecao, " +
                "T._id, T.nome_Tecnico, S._id, S.nome_Setor " +
                "FROM INSPECAO I JOIN TECNICO T ON T._id = I.id_Tecnico " +
                "                JOIN SETOR S ON S._id = I.id_Setor " +
                "                WHERE I._id = ?";

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();

        String [] parametros = {String.valueOf(id_inspecao)};

        Cursor cursor = db.rawQuery(sql, parametros);

        return cursor;
    }

    public static Cursor pesquisar(String dataInicio, int id_setor){
        String sql = "SELECT I._id,I.data_Inicio_Inspecao,I.data_Fim_Inspecao,T.nome_Tecnico " +
                "FROM INSPECAO I JOIN TECNICO T ON T._id = I.id_Tecnico " +
                "WHERE I.data_Inicio_Inspecao LIKE '?%' AND I.id_Setor = ?";

        String [] parametros = {dataInicio,String.valueOf(id_setor)};

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql, parametros);

        return cursor;
    }//fim pesquisar()
    public static Cursor pesquisarTecnicoInspecao(int id_tecnico){
        String sql = "SELECT I._id ,I.data_Inicio_Inspecao,I.data_Fim_Inspecao," +
                "                            L.nome_Local,L._id AS 'id_local', " +
                "                            S.nome_Setor,S._id AS 'id_setor', " +
                "                            T.nome_Tecnico,T._id AS 'id_tecnico' " +
                "                FROM INSPECAO I JOIN TECNICO T ON T._id = I.id_Tecnico " +
                "                JOIN SETOR S ON S._id = I.id_Setor " +
                "                JOIN LOCAL L ON L._id = S.id_Local " +
                "                WHERE T._id = ?";

        String [] parametros = {String.valueOf(id_tecnico)};

        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql, parametros);

        return cursor;
    }//fim pesquisar()

    public static Cursor CursorlistarTodos(int id_Setor){
        String sql = "SELECT I._id,I.data_Inicio_Inspecao,I.data_Fim_Inspecao,T.nome_Tecnico " +
                     "FROM INSPECAO I JOIN TECNICO T ON T._id = I.id_Tecnico " +
                     "WHERE I.id_Setor = ?";
        String [] parametros = {String.valueOf(id_Setor)};
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        Cursor cursor = db.rawQuery(sql,parametros);


        return cursor;
    }//fim listarTodos()

    public static int atualizarDataFim_inspecao(Inspecao inspecao)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();
        String [] parametros = {String.valueOf(inspecao.getId_Inspecao())};
        ContentValues values = new ContentValues();
        values.put(Inspecao.COLUNA_data_Fim_Inspecao, inspecao.getData_Fim_Inspecao());
        int resultado = db.update(Inspecao.NOME_TABELA_INSPECAO,values," _id = ?",parametros);
        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }

    public static int delete(int id_inspecao)throws Exception{
        SQLiteDatabase db = DataBaseManager.getInstance().openDataBase();

        String [] parametros = {String.valueOf(id_inspecao)};

        int resultado = db.delete(Inspecao.NOME_TABELA_INSPECAO," _id = ?",parametros);
        DataBaseManager.getInstance().closeDatabase();

        return resultado;
    }//fim delete();
}
