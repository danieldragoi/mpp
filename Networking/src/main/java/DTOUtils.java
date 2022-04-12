
import java.time.LocalDateTime;
import java.util.List;


public class DTOUtils {

    public static Oficiu getFromDTO(OficiuDTO ofdto){
        String id = ofdto.getId();
        String pass = ofdto.getPasswd();
        String adresa = ofdto.getAdresa();
        String nume = ofdto.getNume();
        return new Oficiu(id, pass, adresa, nume);

    }
    public static OficiuDTO getDTO(Oficiu oficiu){
        String id = oficiu.getUsername();
        String pass = oficiu.getPassword();
        String adresa = oficiu.getAdresa();
        String nume = oficiu.getNume();
        return new OficiuDTO(id, pass, nume, adresa);
    }

    public static Cursa getFromDTO(CurseDTO mdto){
        String destinatie = mdto.getNume_destinatie();
        LocalDateTime data = mdto.getData_plecarii();
        List<Rezervare> rezervari = mdto.getRezervari();
        int id = mdto.getId();
        Cursa cursa = new Cursa(destinatie, data, rezervari);
        cursa.setId(id);
        return cursa;

    }

    public static CurseDTO getDTO(Cursa cursa){
        String destinatie = cursa.getDestinatie();
        LocalDateTime data = cursa.getData_plecarii();
        List<Rezervare> rezervari = cursa.getRezervari();
        CurseDTO cursaDTO = new CurseDTO(destinatie, data, rezervari);
        cursaDTO.setId(cursa.getId());
        return cursaDTO;
    }

    public static CurseDTO[] getDTO(Cursa[] curse){
        CurseDTO[] frDTO=new CurseDTO[curse.length];
        for(int i=0; i<curse.length; i++)
            frDTO[i] = getDTO(curse[i]);
        return frDTO;
    }

    public static Cursa[] getFromDTO(CurseDTO[] users){
        Cursa[] friends = new Cursa[users.length];
        for(int i=0; i<users.length; i++){
            friends[i] = getFromDTO(users[i]);
        }
        return friends;
    }

    public static OficiuDTO[] getDTO(Oficiu[] users){
        OficiuDTO[] frDTO=new OficiuDTO[users.length];
        for(int i=0; i<users.length; i++)
            frDTO[i] = getDTO(users[i]);
        return frDTO;
    }

    public static Oficiu[] getFromDTO(OficiuDTO[] users){
        Oficiu[] friends = new Oficiu[users.length];
        for(int i=0; i<users.length; i++){
            friends[i] = getFromDTO(users[i]);
        }
        return friends;
    }

    public static Rezervare getFromDTO(RezervareDTO rezervareDTO){
        int id_cursa = rezervareDTO.getId_cursa();
        int nr_loc = rezervareDTO.getNr_loc();
        String nume = rezervareDTO.getNume_persoana();
        return new Rezervare(id_cursa, nr_loc, nume);

    }


    public static RezervareDTO getDTO(Rezervare rezervare){
        int id_cursa = rezervare.getId_cursa();
        int nr_loc = rezervare.getNr_loc();
        String nume = rezervare.getNume_persoana();
        return new RezervareDTO(id_cursa, nr_loc, nume);
    }

    public static RezervareDTO[] getDTO(Rezervare[] users){
        RezervareDTO[] frDTO = new RezervareDTO[users.length];
        for(int i=0; i<users.length; i++)
            frDTO[i] = getDTO(users[i]);
        return frDTO;
    }

    public static Rezervare[] getFromDTO(RezervareDTO[] users){
        Rezervare[] friends = new Rezervare[users.length];
        for(int i=0; i<users.length; i++){
            friends[i] = getFromDTO(users[i]);
        }
        return friends;
    }
}
