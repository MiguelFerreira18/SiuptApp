package devapp.upt.siuptapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Db_handler extends SQLiteOpenHelper {

    Context ct;

    //Nome das tabelas
    public static final String DB_UC_TABLE = "UC";
    public static final String DB_AL_TABLE = "AL";
    public static final String DB_AL_UC_HORARIO_TABLE = "HOR";
    public static final String DB_AL_UC_INSCR = "UCINCR";
    public static final String DB_AL_UC_NOTAS_TABLE = "NOTAS";

    //TABLE UC
    public static final String UCCOD = "UCCOD";
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

    //TABLE INTERMEDIA NOTAS
    public static final String NOTID = "NOT_ID";
    public static final String AL_NOT_NUM = "NUMALUNO";
    public static final String UC_NOT_COD = "CODUC";
    public static final String NOTA = "NOTA";
    //TABLE INTERMEDIA UCINSCRITA
    public static final String UCINSCID = "UCINSCID";
    public static final String AL_UCINSC_NUM = "NUMALUNO";
    public static final String UC_UCINSC_COD = "CODUC";




    //DATABASE DEFINITIONS
    public static final String DBNAME = "mydatabase";


   

    public static final int VERSION = 37;//Alterar este valor sempre que se quiser uma base de dados nova


    //Construtor
    public Db_handler(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
        this.ct = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String queryUCTable = String.format("CREATE TABLE %s( %s INTEGER PRIMARY KEY, %s TEXT)", DB_UC_TABLE, UCCOD, UCNOME);
        String queryALTable = String.format("CREATE TABLE %s( %s INTEGER PRIMARY KEY,%s TEXT,%s Text,%s TEXT)", DB_AL_TABLE, ALNUM, ALNOME, ALPASS, ALTOKEN);
        String queryHorarioTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER,%s INTEGER,%s TEXT,%s INTEGER,%s INTEGER,%s TEXT, FOREIGN KEY(%s) REFERENCES %s(%s), FOREIGN KEY(%s) REFERENCES %s(%s))", DB_AL_UC_HORARIO_TABLE, HORID, AL_HOR_NUM, UC_HORARIO_COD, HORDIA, HORI, HORF, HORTIPO, AL_HOR_NUM, DB_AL_TABLE, ALNUM, UC_HORARIO_COD, DB_UC_TABLE, UCCOD);
        String queryNotaTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER,%s INTEGER,%s INTEGER, FOREIGN KEY(%s) REFERENCES %s(%s), FOREIGN KEY(%s) REFERENCES %s(%s))", DB_AL_UC_NOTAS_TABLE, NOTID, AL_NOT_NUM, UC_NOT_COD, NOTA, AL_NOT_NUM, DB_AL_TABLE, ALNUM, UC_NOT_COD, DB_UC_TABLE, UCCOD);
        String queryUCInscritaTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER,%s INTEGER, FOREIGN KEY(%s) REFERENCES %s(%s), FOREIGN KEY(%s) REFERENCES %s(%s))",DB_AL_UC_INSCR, UCINSCID, AL_UCINSC_NUM, UC_UCINSC_COD, AL_UCINSC_NUM, DB_AL_TABLE, ALNUM, UC_UCINSC_COD, DB_UC_TABLE, UCCOD);
        sqLiteDatabase.execSQL(queryUCTable);
        sqLiteDatabase.execSQL(queryALTable);
        sqLiteDatabase.execSQL(queryHorarioTable);
        sqLiteDatabase.execSQL(queryNotaTable);
        sqLiteDatabase.execSQL(queryUCInscritaTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_UC_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_AL_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_AL_UC_HORARIO_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_AL_UC_INSCR);
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", DB_AL_UC_NOTAS_TABLE));
        onCreate(sqLiteDatabase);
    }
/*LATERAR O SITIO DISTO*/
    public void addInscr(Inscricao inscricao) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("INSERT INTO %s(%s,%s) VALUES(%s,%s)", DB_AL_UC_INSCR, AL_UCINSC_NUM, UC_UCINSC_COD, inscricao.getAlunoId(), inscricao.getUcId());
        db.close();
    }
    public ArrayList<String> getInscritas(String token){
ArrayList<String> inscritas = new ArrayList<>();
ArrayList<Integer> ucCods = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        int al = checkToken(token);
        String query = String.format("SELECT %s FROM %s WHERE %s = %s", UC_UCINSC_COD, DB_AL_UC_INSCR, AL_UCINSC_NUM, al);
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                ucCods.add(cursor.getInt(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        for (int i = 0; i < ucCods.size(); i++) {
            inscritas.add(getUc(ucCods.get(i)).getNome());
        }
        return inscritas;
    }


    /**
     * adiciona um aluno na base de dados
     * funciona
     *
     * @param a
     */
    public void addAluno(Aluno a) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("INSERT INTO %s(%s,%s,%s,%s) VALUES('%s','%s','%s','%s');", DB_AL_TABLE, ALNUM, ALNOME, ALPASS, ALTOKEN, a.getAlNum(), a.getAlNome(), a.getAlPass(), a.getAlToken());
        db.execSQL(query);
    }

    /**
     * adiciona uma nota na tabela de notas
     * funciona
     *
     * @param n
     */
    public void addNota(Nota n) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("INSERT INTO %s(%s,%s,%s) VALUES('%s','%s','%s');", DB_AL_UC_NOTAS_TABLE, AL_NOT_NUM, UC_NOT_COD, NOTA, n.getNumAluno(), n.getCodUC(), n.getNota());
        db.execSQL(query);
    }

    /**
     * adiciona um novo horario na base de dados
     * funciona
     *
     * @param h
     */
    public void addHorario(Horario h) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("INSERT INTO %s(%s,%s,%s,%s,%s,%s) VALUES('%s','%s','%s','%s','%s','%s');", DB_AL_UC_HORARIO_TABLE, AL_HOR_NUM, UC_HORARIO_COD, HORDIA, HORI, HORF, HORTIPO, h.getNumAluno(), h.getCodUC(), h.getDia(), h.getHoraInicio(), h.getHoraFim(), h.getTipo());
        db.execSQL(query);
    }

    /**
     * adiciona uma nova uc na base dados
     * funciona
     *
     * @param c
     */
    public void addUc(Uc c) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(checkForOneUc(c.getCodUC())){
        String query = String.format("INSERT INTO %s(%s,%s) VALUES('%s','%s');", DB_UC_TABLE, UCCOD, UCNOME, c.getCodUC(), c.getNome());
        db.execSQL(query);
        }
    }

    /**
     * devolve uma uc dado um id
     * está correta
     *
     * @param ucCod
     * @return
     */
    public Uc getUc(int ucCod) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = %s", DB_UC_TABLE, UCCOD, ucCod);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Uc uc = new Uc(cursor.getInt(0), cursor.getString(1).replace("\n", ""));
            return uc;
        }
        return null;
    }

    /**
     * devolve as ucs do aluno
     * Funciona
     *
     * @param tokenAl
     * @return
     */
    public ArrayList<Uc> getUcs(String tokenAl) {
        ArrayList<Uc> ucs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        int alNum = checkToken(tokenAl);
        String query = String.format("Select * FROM %s,%s WHERE %s = %s AND %s = %s", DB_UC_TABLE, DB_AL_UC_HORARIO_TABLE, alNum, AL_HOR_NUM, UC_HORARIO_COD, UCCOD);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Uc u = new Uc();
                u.setCodUC(cursor.getInt(0));
                u.setNome(cursor.getString(1).replace("\n", ""));
                ucs.add(u);
            } while (cursor.moveToNext());
        }
        return ucs;
    }

    /**
     * Devolve um arraylist de notas de um dado aluno
     * está correta
     *
     * @param token
     * @return
     */
    public ArrayList<Nota> getNotas(String token) {
        ArrayList<Nota> notas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        int alNum = checkToken(token);
        String query = String.format("Select * FROM %s WHERE %s = %s", DB_AL_UC_NOTAS_TABLE, alNum, AL_NOT_NUM);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Nota n = new Nota();
                n.setNumAluno(cursor.getInt(1));
                n.setCodUC(cursor.getInt(2));
                n.setNota(cursor.getInt(3));
                notas.add(n);
            } while (cursor.moveToNext());
        }
        return notas;
    }

    /**
     * devolve o horario do aluno
     * Funciona
     *
     * @param tokenAl
     * @return
     */
    public ArrayList<Horario> getHorarioAluno(String tokenAl) {
        ArrayList<Horario> ucs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        int alNum = checkToken(tokenAl);
        String query = String.format("SELECT * FROM %s WHERE %s = %s ", DB_AL_UC_HORARIO_TABLE, AL_HOR_NUM, alNum);
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


    /**
     * Verifica se o numero do aluno e a password são iguais e devolve um token
     * Funciona
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
        return "";
    }

    /**
     * devolve um id dado um token
     * Funciona
     *
     * @param token
     * @return
     */
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

    public boolean checkForOneUc(int codUC) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = %s", DB_UC_TABLE,UCCOD,codUC);
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount() == 0;
    }

    //check if  horarios table is empty
    public boolean checkHorariosTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s", DB_AL_UC_HORARIO_TABLE);
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount() == 0;
    }

    //check if ucs table is empty
    public boolean checkUcsTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s", DB_UC_TABLE);
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount() == 0;
    }

    //check if aluno table is empty
    public boolean checkAlunoTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s", DB_AL_TABLE);
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount() == 0;

    }

    public boolean checkHorarioAlunoTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s", DB_AL_UC_HORARIO_TABLE);
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount() == 0;
    }
}
