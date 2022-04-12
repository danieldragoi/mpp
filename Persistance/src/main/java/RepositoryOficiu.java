
import java.util.List;

public interface RepositoryOficiu extends Repository<Oficiu, Integer> {


    /**
     * Adauga un oficiu in baza de date
     * @param elem - oficiul pe care vrei sa il adaugi in db
     *        elem - trebuie sa fie valid
     */
    @Override
    void add(Oficiu elem);

    /**
     * Sterge un oficiu din baza de date
     * @param elem - oficiu pe care vrei sa o stergi din db
     *        elem - trebuie sa exista deja in db
     */
    @Override
    void delete(Oficiu elem);

    /**
     * Actualizeaza un oficiu din baza de date
     * @param elem - oficiul nou
     * @param id - id-ul oficiului care trebuie actualizat
     */
    @Override
    void update(Oficiu elem, Integer id);

    /**
     * Cauta un oficiu dupa id din baza de date
     * @param id - id-ul oficiului
     * @return Oficiu sau null daca nu exista
     */
    @Override
    Oficiu findById(Integer id);

    /**
     * Returneaza o lista cu toate oficiile din baza de date
     * @return o lista de oficii
     */
    @Override
    List<Oficiu> getAll();
}
