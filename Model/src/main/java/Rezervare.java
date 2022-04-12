import java.io.Serializable;
import java.util.Objects;

public class Rezervare implements Serializable {
    private int id_cursa;
    private int nr_loc;
    private String nume_persoana;

    @Override
    public String toString() {
        return "Rezervare{" +
                "nume_persoana='" + nume_persoana + '\'' +
                '}';
    }

    public Rezervare(int id_cursa, int nr_loc, String nume_persoana) {
        this.id_cursa = id_cursa;
        this.nr_loc = nr_loc;
        this.nume_persoana = nume_persoana;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rezervare)) return false;
        Rezervare rezervare = (Rezervare) o;
        return getId_cursa() == rezervare.getId_cursa() && getNr_loc() == rezervare.getNr_loc();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_cursa(), getNr_loc());
    }

    public int getId_cursa() {
        return id_cursa;
    }

    public void setId_cursa(int id_cursa) {
        this.id_cursa = id_cursa;
    }

    public int getNr_loc() {
        return nr_loc;
    }

    public void setNr_loc(int nr_loc) {
        this.nr_loc = nr_loc;
    }

    public String getNume_persoana() {
        return nume_persoana;
    }

    public void setNume_persoana(String nume_persoana) {
        this.nume_persoana = nume_persoana;
    }
}
