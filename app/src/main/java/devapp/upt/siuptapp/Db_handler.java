package devapp.upt.siuptapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Db_handler extends SQLiteOpenHelper {

    Context ct;

    //Nome das tabelas
    public static final String DB_UC_TABLE = "UC";
    public static final String DB_AL_TABLE = "AL";
    public static final String DB_AL_UC_HORARIO_TABLE = "HOR";

    //TABLE UC
    public static final String UCCOD = "UCCOD";
    public static final String UCPROF = "UCPROF";
    public static final String UCNOME = "UCNOME";
    //TABLE ALUNO
    public static final String ALNUM = "ALNUM";
    public static final String ALNOME = "ALNOME";
    public static final String ALPASS = "ALPASS";
    public static final String ALTOKEN = "ALTOKEN";
    //TABLE INTERMEDIA "HORARIO"
    public static final String HORID = "HORID";
    public static final String AL_HOR_NUM = "NUMALUNO";
    public static final String UC_HORARIO_COD = "CODUC";
    public static final String HORDIA = "HORDIA";
    public static final String HORI = "HORI";
    public static final String HORF = "HORF";
    public static final String HORTIPO = "HORTIPO";

    public static final String DBNAME = "mydatabase";
    public static final int VERSION = 1;//Alterar este valor sempre que se quiser uma base de dados nova


    //Construtor
    public Db_handler(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
        this.ct = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String queryUCTable = String.format("CREATE TABLE %s( %s INTEGER PRIMARY KEY,%s TEXT, %s TEXT)", DB_UC_TABLE, UCCOD, UCPROF, UCNOME);
        String queryALTable = String.format("CREATE TABLE %s( %s INTEGER PRIMARY KEY,%s TEXT,%s INTEGER,%s TEXT)", DB_AL_TABLE, ALNUM, ALNOME, ALPASS, ALTOKEN);
        String queryHorarioTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER,%s INTEGER,%s TEXT,%s INTEGER,%s INTEGER,%s TEXT, FOREIGN KEY(%s) REFERENCES %s(%s), FOREIGN KEY(%s) REFERENCES %s(%s))", DB_AL_UC_HORARIO_TABLE, HORID, AL_HOR_NUM, UC_HORARIO_COD, HORDIA, HORI, HORF, HORTIPO, AL_HOR_NUM, DB_AL_TABLE, ALNUM, UC_HORARIO_COD, DB_UC_TABLE, UCCOD);
        sqLiteDatabase.execSQL(queryUCTable);
        sqLiteDatabase.execSQL(queryALTable);
        sqLiteDatabase.execSQL(queryHorarioTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_UC_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_AL_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_AL_UC_HORARIO_TABLE);
        onCreate(sqLiteDatabase);
    }

    /**
     * adiciona um aluno na base de dados
     * @param a
     */
    public void addAluno(Aluno a) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("INSERT INTO %s(%s,%s,%s,%s) VALUES('%s','%s','%s','%s');", DB_AL_TABLE, ALNUM, ALNOME, ALPASS, ALTOKEN, a.getAlnum(), a.getAlnome(), a.getAlpass(), a.getAltoken());
        db.execSQL(query);
    }

    /**
     * adiciona um novo horario na base de dados
     * @param h
     */
    public void addHorario(Horario h) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("INSERT INTO %s(%s,%s,%s,%s,%s,%s) VALUES('%s','%s','%s','%s','%s','%s');", DB_AL_UC_HORARIO_TABLE, AL_HOR_NUM, UC_HORARIO_COD, HORDIA, HORI, HORF, HORTIPO, h.getNumAluno(), h.getCodUC(), h.getDia(), h.getHoraInicio(), h.getHoraFim(), h.getTipo());
        db.execSQL(query);
    }

    /**
     * adiciona uma nova uc na base dados
     * @param c
     */
    public void addUc(Uc c) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("INSERT INTO %s(%s,%s,%s) VALUES('%s','%s','%s');", DB_UC_TABLE, UCCOD, UCPROF, UCNOME, c.getCodUC(), c.getProf(), c.getNome());
        db.execSQL(query);
    }

    /**
     * devolve as ucs do aluno
     *
     * @param tokenAl
     * @return
     */
    public ArrayList<Uc> getUcs(String tokenAl) {
        ArrayList<Uc> ucs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        int alNum = checkToken(tokenAl);
        String query = String.format("Select %s FROM %s,%s WHERE %s = %s AND %s = %s", UCNOME, DB_UC_TABLE, DB_AL_TABLE, DB_AL_UC_HORARIO_TABLE, alNum, UCCOD, UC_HORARIO_COD);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Uc u = new Uc();
                u.setCodUC(cursor.getInt(0));
                u.setProf(cursor.getString(1));
                u.setNome(cursor.getString(2));
                ucs.add(u);
            } while (cursor.moveToNext());
        }
        return ucs;
    }

    /**
     * devolve o horario do aluno
     *
     * @param tokenAl
     * @return
     */
    public ArrayList<Horario> getHorarioAluno(String tokenAl) {
        ArrayList<Horario> ucs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        int alNum = checkToken(tokenAl);
        String query = String.format("SELECT %s.* FROM %s,%s WHERE %s = %s AND %s = %s", DB_AL_UC_HORARIO_TABLE, DB_UC_TABLE, DB_AL_UC_HORARIO_TABLE, AL_HOR_NUM, alNum, UCCOD, UC_HORARIO_COD);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Horario h = new Horario();
                h.setNumAluno(cursor.getInt(1));
                h.setCodUC(cursor.getInt(2));
                h.setDia(cursor.getInt(3));
                h.setHoraInicio(cursor.getInt(4));
                h.setHoraFim(cursor.getInt(5));
                h.setTipo(cursor.getString(6));
                ucs.add(h);
            } while (cursor.moveToNext());
        }
        return ucs;
    }


    //devolve o id do Aluno dado um token
    public int checkToken(String token) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'", DB_AL_TABLE, ALTOKEN, token);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            //get numAluno with token
            int alnum = cursor.getInt(0);
            //get ucs with numAluno
            return alnum;
        } else {
            return -1;
        }
    }

    /**
     * Verifica se o numero do aluno e a password são iguais e devolve um token
     *
     * @param nome
     * @param pass
     * @return
     */
    public String authCheck(String nome, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        String queryAl = "SELECT * FROM " + DB_AL_TABLE;
        Cursor c = null;
        if (db != null) {
            c = db.rawQuery(queryAl, null);
        }
        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                if (nome.equalsIgnoreCase(String.valueOf(c.getInt(0))) && pass.equalsIgnoreCase(c.getString(2))) {
                    return c.getString(3);
                }
            }
        }
        return null;
    }

}
