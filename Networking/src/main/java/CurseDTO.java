import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class CurseDTO implements Serializable {
    private int id;
    private String nume_destinatie;
    private LocalDateTime data_plecarii;
    private List<Rezervare> rezervari;

    public CurseDTO(String nume_destinatie, LocalDateTime data_plecarii, List<Rezervare> rezervari) {
        this.nume_destinatie = nume_destinatie;
        this.data_plecarii = data_plecarii;
        this.rezervari = rezervari;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public String getNume_destinatie() {
        return nume_destinatie;
    }

    public void setNume_destinatie(String nume_destinatie) {
        this.nume_destinatie = nume_destinatie;
    }

    public LocalDateTime getData_plecarii() {
        return data_plecarii;
    }

    public void setData_plecarii(LocalDateTime data_plecarii) {
        this.data_plecarii = data_plecarii;
    }

    public List<Rezervare> getRezervari() {
        return rezervari;
    }

    public void setRezervari(List<Rezervare> rezervari) {
        this.rezervari = rezervari;
    }

    @Override
    public String toString() {
        return "CurseDTO{" +
                "nume_destinatie='" + nume_destinatie + '\'' +
                '}';
    }
}
