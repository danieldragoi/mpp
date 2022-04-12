import java.time.format.DateTimeFormatter;

public class CursaDTO {
    private Cursa cursa;
    private int nrLocuriLibere;

    public CursaDTO(Cursa cursa, int nrLocuriLibere) {
        this.cursa = cursa;
        this.nrLocuriLibere = nrLocuriLibere;
    }

    @Override
    public String toString() {
        return  cursa.getId() + " " +
                cursa.getDestinatie() + " \t"
                + cursa.getData_plecarii().format(DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm")) + "  \t"
                + "nrLocuriLibere=" + nrLocuriLibere;
    }

    public Cursa getCursa() {
        return cursa;
    }

    public void setCursa(Cursa cursa) {
        this.cursa = cursa;
    }

    public int getNrLocuriLibere() {
        return nrLocuriLibere;
    }

    public void setNrLocuriLibere(int nrLocuriLibere) {
        this.nrLocuriLibere = nrLocuriLibere;
    }
}
