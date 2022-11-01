package devapp.upt.siuptapp;

public class Aluno  {

    private int alnum;
    private String alnome;
    private int alpass;
    private String altoken;


    public Aluno(int alnum, String alnome, int alpass, String altoken) {
        this.alnum = alnum;
        this.alnome = alnome;
        this.alpass = alpass;
        this.altoken = altoken;

    }

    public int getAlnum() {
        return alnum;
    }

    public void setAlnum(int alnum) {
        this.alnum = alnum;
    }

    public String getAlnome() {
        return alnome;
    }

    public void setAlnome(String alnome) {
        this.alnome = alnome;
    }

    public int getAlpass() {
        return alpass;
    }

    public void setAlpass(int alpass) {
        this.alpass = alpass;
    }

    public String getAltoken() {
        return altoken;
    }

    public void setAltoken(String altoken) {
        this.altoken = altoken;
    }



    @Override
    public String toString() {
        return "Aluno{" +
                "alnum=" + alnum +
                ", alnome='" + alnome + '\'' +
                ", alpass=" + alpass +
                ", altoken='" + altoken + '\'' +
                '}';
    }
}