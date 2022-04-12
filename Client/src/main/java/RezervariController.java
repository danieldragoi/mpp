
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class RezervariController {
    private IService service;
    private Cursa cursa;

    @FXML
    Label labelRezervat1;

    @FXML
    Label labelRezervat2;

    @FXML
    Label labelRezervat3;

    @FXML
    Label labelRezervat4;

    @FXML
    Label labelRezervat5;

    @FXML
    Label labelRezervat6;

    @FXML
    Label labelRezervat7;

    @FXML
    Label labelRezervat8;

    @FXML
    Label labelRezervat9;

    @FXML
    Label labelRezervat10;

    @FXML
    Label labelRezervat11;

    @FXML
    Label labelRezervat12;

    @FXML
    Label labelRezervat13;

    @FXML
    Label labelRezervat14;

    @FXML
    Label labelRezervat15;

    @FXML
    Label labelRezervat16;

    @FXML
    Label labelRezervat17;

    @FXML
    Label labelRezervat18;

    @FXML
    Label labelNume1;

    @FXML
    Label labelNume2;

    @FXML
    Label labelNume3;

    @FXML
    Label labelNume4;

    @FXML
    Label labelNume5;

    @FXML
    Label labelNume6;

    @FXML
    Label labelNume7;

    @FXML
    Label labelNume8;

    @FXML
    Label labelNume9;

    @FXML
    Label labelNume10;

    @FXML
    Label labelNume11;

    @FXML
    Label labelNume12;

    @FXML
    Label labelNume13;

    @FXML
    Label labelNume14;

    @FXML
    Label labelNume15;

    @FXML
    Label labelNume16;

    @FXML
    Label labelNume17;

    @FXML
    Label labelNume18;


    void initiateRezervariNu(){
        labelRezervat1.setText("nu");
        labelNume1.setText("-");

        labelRezervat2.setText("nu");
        labelNume2.setText("-");

        labelRezervat3.setText("nu");
        labelNume3.setText("-");

        labelRezervat4.setText("nu");
        labelNume4.setText("-");

        labelRezervat5.setText("nu");
        labelNume5.setText("-");

        labelRezervat6.setText("nu");
        labelNume6.setText("-");

        labelRezervat7.setText("nu");
        labelNume7.setText("-");

        labelRezervat8.setText("nu");
        labelNume8.setText("-");

        labelRezervat9.setText("nu");
        labelNume9.setText("-");

        labelRezervat10.setText("nu");
        labelNume10.setText("-");

        labelRezervat11.setText("nu");
        labelNume11.setText("-");

        labelRezervat12.setText("nu");
        labelNume12.setText("-");

        labelRezervat13.setText("nu");
        labelNume13.setText("-");

        labelRezervat14.setText("nu");
        labelNume14.setText("-");

        labelRezervat15.setText("nu");
        labelNume15.setText("-");

        labelRezervat16.setText("nu");
        labelNume16.setText("-");

        labelRezervat17.setText("nu");
        labelNume17.setText("-");

        labelRezervat18.setText("nu");
        labelNume18.setText("-");
    }

    void initiateRezervariDa(){
        List<Rezervare> rezervari = cursa.getRezervari();
        //labelNume1.setText(String.valueOf(rezervari.get(0).getId_cursa()) + String.valueOf(rezervari.get(0).getNr_loc()));
        int id_cursa = cursa.getId();
        if(rezervari.contains(new Rezervare(id_cursa, 1, ""))){
            labelRezervat1.setText("da");
            labelNume1.setText(rezervari.get(rezervari.indexOf(new Rezervare(id_cursa, 1, ""))).getNume_persoana());
        }
        if(rezervari.contains(new Rezervare(id_cursa, 2, ""))){
            labelRezervat2.setText("da");
            labelNume2.setText(rezervari.get(rezervari.indexOf(new Rezervare(id_cursa, 2, ""))).getNume_persoana());
        }
        if(rezervari.contains(new Rezervare(id_cursa, 3, ""))){
            labelRezervat3.setText("da");
            labelNume3.setText(rezervari.get(rezervari.indexOf(new Rezervare(id_cursa, 3, ""))).getNume_persoana());
        }
        if(rezervari.contains(new Rezervare(id_cursa, 4, ""))){
            labelRezervat4.setText("da");
            labelNume4.setText(rezervari.get(rezervari.indexOf(new Rezervare(id_cursa, 4, ""))).getNume_persoana());
        }
        if(rezervari.contains(new Rezervare(id_cursa, 5, ""))){
            labelRezervat5.setText("da");
            labelNume5.setText(rezervari.get(rezervari.indexOf(new Rezervare(id_cursa, 5, ""))).getNume_persoana());
        }
        if(rezervari.contains(new Rezervare(id_cursa, 6, ""))){
            labelRezervat6.setText("da");
            labelNume6.setText(rezervari.get(rezervari.indexOf(new Rezervare(id_cursa, 6, ""))).getNume_persoana());
        }
        if(rezervari.contains(new Rezervare(id_cursa, 7, ""))){
            labelRezervat7.setText("da");
            labelNume7.setText(rezervari.get(rezervari.indexOf(new Rezervare(id_cursa, 7, ""))).getNume_persoana());
        }
        if(rezervari.contains(new Rezervare(id_cursa, 8, ""))){
            labelRezervat8.setText("da");
            labelNume8.setText(rezervari.get(rezervari.indexOf(new Rezervare(id_cursa, 8, ""))).getNume_persoana());
        }
        if(rezervari.contains(new Rezervare(id_cursa, 9, ""))){
            labelRezervat9.setText("da");
            labelNume9.setText(rezervari.get(rezervari.indexOf(new Rezervare(id_cursa, 9, ""))).getNume_persoana());
        }
        if(rezervari.contains(new Rezervare(id_cursa, 10, ""))){
            labelRezervat10.setText("da");
            labelNume10.setText(rezervari.get(rezervari.indexOf(new Rezervare(id_cursa, 10, ""))).getNume_persoana());
        }
        //todo de terminat si aici
    }

    void intiateRezervari(){
        initiateRezervariNu();
        initiateRezervariDa();

    }

    public void setCursa(Cursa cursa) {
        this.cursa = cursa;
        intiateRezervari();
    }

    public void setService(IService service) {
        this.service = service;
    }


}
