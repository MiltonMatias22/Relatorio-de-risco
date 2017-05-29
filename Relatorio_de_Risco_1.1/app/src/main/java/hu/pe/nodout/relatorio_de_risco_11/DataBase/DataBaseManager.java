package hu.pe.nodout.relatorio_de_risco_11.DataBase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Milton Matias on 03/02/2017.
 */
public class DataBaseManager {
    private Integer mOpenCounter = 0;

    private static DataBaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabese;

    public static  synchronized void inicializarInstance(SQLiteOpenHelper helper){
        if(instance == null){
            instance = new DataBaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DataBaseManager getInstance(){
        if(instance == null){
            throw new IllegalStateException(DataBaseManager.class.getSimpleName()+
                      "Não é inicializado, chame o método initializeInstance (..) primeiro.");
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDataBase(){
        mOpenCounter +=1;
        if(mOpenCounter == 1){
            //abrir novo banco de dados
            mDatabese = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabese;
    }

    public synchronized void closeDatabase(){
        mOpenCounter -= 1;
        if(mOpenCounter == 0){
            //fechar banco de dados
            mDatabese.close();
        }
    }

}
