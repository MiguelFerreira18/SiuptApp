package devapp.upt.siuptapp;

public class Uc {
    private int codUC;
    private String nome;
    private String prof;


    public Uc(int codUC, String nome, String prof) {
        this.codUC = codUC;
        this.nome = nome;
        this.prof = prof;

    }

    public Uc() {
        this.codUC = 0;
        this.nome = "";
        this.prof = "";

    }

    public int getCodUC() {
        return codUC;
    }

    public void setCodUC(int codUC) {
        this.codUC = codUC;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }


}
