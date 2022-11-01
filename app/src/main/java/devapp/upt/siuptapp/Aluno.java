package devapp.upt.siuptapp;

public class Aluno  {

    private int alNum;
    private String alNome;
    private String alPass;
    private String alToken;


    public Aluno(int alnum, String alnome, String alpass, String alToken) {
        this.alNum = alnum;
        this.alNome = alnome;
        this.alPass = alpass;
        this.alToken = alToken;

    }

    public int getAlNum() {
        return alNum;
    }

    public void setAlNum(int alNum) {
        this.alNum = alNum;
    }

    public String getAlNome() {
        return alNome;
    }

    public void setAlNome(String alNome) {
        this.alNome = alNome;
    }

    public String getAlPass() {
        return alPass;
    }

    public void setAlPass(String alPass) {
        this.alPass = alPass;
    }

    public String getAlToken() {
        return alToken;
    }

    public void setAlToken(String alToken) {
        this.alToken = alToken;
    }



    @Override
    public String toString() {
        return "Aluno{" +
                "alnum=" + alNum +
                ", alnome='" + alNome + '\'' +
                ", alpass=" + alPass +
                ", altoken='" + alToken + '\'' +
                '}';
    }
}