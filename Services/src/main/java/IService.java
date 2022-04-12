import java.util.ArrayList;
import java.util.List;

public interface IService {

    public Oficiu login(Oficiu oficiu, Observer observer) throws MyException;

    public void logout(String username) throws MyException;

    public List<CursaDTO> getCurse() throws MyException;

    public void addRezervari(Rezervare[] rezervari) throws MyException;

}
