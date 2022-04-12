import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class LoginController {
    private IService service;
    private Oficiu oficiu;

    public void setService(IService service){
        this.service = service;
    }

    @FXML
    public TextField usernameField;

    @FXML
    public TextField passwordField;

    @FXML
    public void onClickButonNext(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        Oficiu of = new Oficiu(username, password, "", "");
        try{
            //deschid noua fereastra
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(MeniuController.class.getResource("/meniu-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                e.printStackTrace();
            }

            MeniuController meniuController = fxmlLoader.getController();

            //incerc sa ma loghez
            oficiu = service.login(of, ((MeniuController)fxmlLoader.getController()));

            //daca a gasit contul
            //inchid fereastra de login
            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();

            //deschid noua fereastra

            stage.setTitle("Meniu");
            stage.setScene(scene);
            stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);

            //trimit utilizatorul la controllerul de meniu

            meniuController.setService(service);
            meniuController.setOficiu(oficiu);

            stage.show();
        }catch (MyException ex){
            //nu a gasit contul
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.show();
        }


    }
    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ... (UI)");

        try {
            service.logout(oficiu.getUsername());
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
