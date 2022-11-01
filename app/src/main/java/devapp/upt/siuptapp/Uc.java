package devapp.upt.siuptapp;

public class Uc {
    private int codUC;
    private String nome;


    public Uc(int codUC, String nome) {
        this.codUC = codUC;
        this.nome = nome;

    }

    public Uc() {
        this.codUC = 0;
        this.nome = "";
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


    @Override
    public String toString() {
        return "Uc{" +
                "codUC=" + codUC +
                ", nome='" + nome + '\'' +
                '}';
    }
}
