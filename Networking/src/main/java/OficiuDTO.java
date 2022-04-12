import java.io.Serializable;

public class OficiuDTO implements Serializable {
    private String id;
    private String passwd;
    private String adresa;
    private String nume;

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public OficiuDTO(String id) {
        this(id,"", "", "");
    }

    public OficiuDTO(String id, String passwd, String nume, String adresa) {
        this.id = id;
        this.passwd = passwd;
        this.adresa = adresa;
        this.nume = nume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    @Override
    public String toString(){
        return "OficiuDTO["+ id +' ' + passwd + " " + nume + "]";
    }
}
