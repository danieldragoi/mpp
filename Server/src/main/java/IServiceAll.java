import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IServiceAll implements IService {
    private RepositoryOficiu repoOficiu;
    private RepositoryCurse repoCurse;
    private RepositoryRezervari repoRezervari;
    private Map<String, Observer> loggedUsers;

    public IServiceAll(RepoOficiu repoOficiu, RepoCurse repoCurse, RepoRezervari repoRezervari) {
        this.repoOficiu = repoOficiu;
        this.repoCurse = repoCurse;
        this.repoRezervari = repoRezervari;
        this.loggedUsers = new ConcurrentHashMap<>();
    }

    public synchronized Oficiu login(Oficiu oficiu, Observer observer) throws MyException {
        List<Oficiu> oficii =  repoOficiu.getAll();
        for (Oficiu of : oficii){
            if(of.getUsername().equals(oficiu.getUsername()) && of.getPassword().equals(oficiu.getPassword())) {
                if(this.loggedUsers.get(oficiu.getUsername()) != null)
                    throw new MyException("Oficiu already connected!");

                this.loggedUsers.put(oficiu.getUsername(), observer);
                return of;
            }
        }
        throw new MyException("Incorect credentials!");
    }


    @Override
    public synchronized void logout(String username) {
        this.loggedUsers.remove(username);
    }

    public synchronized int getNrLocuriLibere(Cursa cursa){
        int nr=18;
        for (Rezervare rezervare : cursa.getRezervari()){
            nr--;
        }
        return nr;
    }


    public synchronized List<CursaDTO> getCurse(){
        List<Cursa> curse = repoCurse.getAll();
        List<CursaDTO> curseDTO = new ArrayList<>();
        for (Cursa cursa : curse){
            CursaDTO cursaDTO = new CursaDTO(cursa, getNrLocuriLibere(cursa));
            curseDTO.add(cursaDTO);
        }
        return curseDTO;
    }

    public synchronized void addRezervari(Rezervare[] rezervari) throws MyException {
        for(Rezervare rezervare : rezervari){
            repoRezervari.add(rezervare);
        }

        updateAllObservers();
    }

    public synchronized List<Rezervare> getRezervari(Cursa cursa){
        return repoRezervari.getAll(cursa.getId());
    }

    public synchronized Cursa getCursa(int id_cursa){
        return repoCurse.findById(id_cursa);
    }

    public synchronized void updateAllObservers() throws MyException {
        for(Map.Entry<String, Observer> loggedUser : this.loggedUsers.entrySet()){
            loggedUser.getValue().doUpdate();
        }
    }
}
