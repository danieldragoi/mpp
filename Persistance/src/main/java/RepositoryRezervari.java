
import java.util.List;

public interface RepositoryRezervari{

    /**
     * Adauga o rezervare in baza de date
     * @param elem - rezervarea pe care vrei sa o adaugi in db
     *        elem - trebuie sa fie valid
     */
    void add(Rezervare elem);

    /**
     * Sterge o rezervare din baza de date
     * @param elem - rezervarea pe care vrei sa o stergi din db
     *        elem - trebuie sa exista deja in db
     */
    void delete(Rezervare elem);

    /**
     * Actualizeaza o rezervare din baza de date
     * @param elem - rezervarea noua
     * @param id_cursa - id-ul cursei de care face parte rezervarea
     * @param nr_loc - nr locului rezervarii
     */
    void update(Rezervare elem, Integer id_cursa, Integer nr_loc);

    /**
     * Cauta o rezervare dupa id_cursa si nr_loc in baza de date
     * @param id_cursa - id-ul cursei
     * @param nr_loc - nr locului rezervarii
     * @return Rezervare sau null daca nu exista
     */
    Rezervare findById(Integer id_cursa, Integer nr_loc);

    /**
     * Returneaza o lista cu toate rezervarile pentru o cursa din baza de date
     * @return o lista de rezervari
     */
    List<Rezervare> getAll(Integer id_cursa);
}
