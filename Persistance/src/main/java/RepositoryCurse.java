

import java.util.List;

public interface RepositoryCurse extends Repository<Cursa, Integer>{
    /**
     * Adauga o cursa in baza de date
     * @param elem - cursa pe care vrei sa o adaugi in db
     *        elem - trebuie sa fie valid
     */
    @Override
    void add(Cursa elem);

    /**
     * Sterge o cursa din baza de date
     * @param elem - cursa pe care vrei sa o stergi din db
     *        elem - trebuie sa exista deja in db
     */
    @Override
    void delete(Cursa elem);

    /**
     * Actualizeaza o cursa din baza de date
     * @param elem - cursa noua
     * @param id - cursa care trebuie actualizata
     */
    @Override
    void update(Cursa elem, Integer id);

    /**
     * Cauta o cursa dupa id din baza de date
     * @param id - id-ul cursei
     * @return Cursa sau null daca nu exista
     */
    @Override
    Cursa findById(Integer id);

    /**
     * Returneaza o lista cu toate cursele din baza de date
     * @return o lista de curse
     */
    @Override
    List<Cursa> getAll();

}
