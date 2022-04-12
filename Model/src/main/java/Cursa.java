import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Cursa {
    private int id;
    private String nume_destinatie;
    private LocalDateTime data_plecarii;
    private List<Rezervare> rezervari;

    @Override
    public String toString() {
        return "Cursa{" +
                "nume_destinatie='" + nume_destinatie + '\'' +
                '}';
    }

    public Cursa(String nume_destinatie, LocalDateTime data_plecarii, List<Rezervare> rezervari) {
        this.nume_destinatie = nume_destinatie;
        this.data_plecarii = data_plecarii;
        this.rezervari = rezervari;
    }

    public List<Rezervare> getRezervari() {
        return rezervari;
    }

    public void setRezervari(List<Rezervare> rezervari) {
        this.rezervari = rezervari;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cursa)) return false;
        Cursa cursa = (Cursa) o;
        return getId() == cursa.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestinatie() {
        return nume_destinatie;
    }

    public void setDestinatie(String nume_destinatie) {
        this.nume_destinatie = nume_destinatie;
    }

    public LocalDateTime getData_plecarii() {
        return data_plecarii;
    }

    public void setData_plecarii(LocalDateTime data_plecarii) {
        this.data_plecarii = data_plecarii;
    }

}
