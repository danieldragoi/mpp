
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MeniuController  implements Observer{
    private IService service;
    private Oficiu oficiu;

    @FXML
    Label labelOficiu;

    @FXML
    ListView listView;

    @FXML
    TextField fieldSearch;

    @FXML
    Button butonRezervari;

    @FXML
    Button butonRezervaLocuri;

    @FXML
    TextField fieldNumeRezervare;

    @FXML
    TextField fieldLocuriRezervare;


    void initListView(List<CursaDTO> curse){
        ObservableList<CursaDTO> observableList = FXCollections.observableArrayList();
        observableList.addAll(curse);
        listView.setItems(observableList);
    }

    public void setService(IService service) throws MyException {
        this.service = service;
        initListView(service.getCurse());

    }

    public void setOficiu(Oficiu oficiu) {
        this.oficiu = oficiu;
        labelOficiu.setText(oficiu.getNume());
    }

    @FXML
    public void onKeyTypedSearch() throws MyException {
        String filter = fieldSearch.getText();
        List<CursaDTO> curse = service.getCurse();
        ObservableList<CursaDTO> observableList = FXCollections.observableArrayList();
        observableList.addAll(curse);
        ObservableList<CursaDTO> curseFiltrate = observableList.filtered(x->x.getCursa().getDestinatie().contains(filter));
        listView.setItems(curseFiltrate);
    }


    @FXML
    public void onActionButonRezervari(){
        //Deschid fereastra de locuri libere
        if(listView.getSelectionModel().getSelectedItems().size() > 0){ //doar daca a fost selectata o cursa
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(RezervariController.class.getResource("/rezervari.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 300, 550);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Rezervari");
            stage.setScene(scene);

            //cauta cursa pe care s-a apasat
            CursaDTO cursaDTO = (CursaDTO)listView.getSelectionModel().getSelectedItems().get(0);

            //trimit utilizatorul la controllerul de mesaje
            RezervariController mesajeController = fxmlLoader.getController();
            mesajeController.setCursa(cursaDTO.getCursa());
            mesajeController.setService(service);

            stage.show();
        }else
        {
            //daca nu a selectat nicio cursa din tabel
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nu ai selectat o cursa!");
            alert.show();
        }
    }

    @FXML
    public void onActionButonRezervaLocuri(){
        if(listView.getSelectionModel().getSelectedItems().size() > 0){ //doar daca a fost selectata o cursa
            String nume = fieldNumeRezervare.getText();
            String locuri_rezervare = fieldLocuriRezervare.getText();
            List<Integer> locuri = new ArrayList<>();
            try{
                locuri = Arrays.stream(locuri_rezervare.split(", ")).map(Integer::parseInt).collect(Collectors.toList());
                CursaDTO cursaDTO = (CursaDTO)listView.getSelectionModel().getSelectedItems().get(0);
                Rezervare[] rezervari = new Rezervare[locuri.size()];
                int i=0;
                for(Integer loc : locuri){
                    rezervari[i] = new Rezervare(cursaDTO.getCursa().getId(), loc, nume);
                    i++;
                }
                service.addRezervari(rezervari);
            }catch(NumberFormatException ex){
                //daca ai introdus numere gresit
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Locuri introduse gresit");
                alert.show();
            }catch (MyException ex){
                //daca ai introdus ceva gresit pt bd
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(ex.getMessage());
                alert.show();
            }
        }else
        {
            //daca nu a selectat nicio cursa din tabel
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nu ai selectat o cursa!");
            alert.show();
        }
    }

    @Override
    public void doUpdate() throws MyException {
        Platform.runLater(() ->{
            try {
                initListView(service.getCurse());
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

    }

}
