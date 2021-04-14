package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_change {


    public static Client client;

    @FXML
    private TextField clientFirmaField;
    @FXML
    private TextField clientKField;
    @FXML
    private TextField clientCarField;
    @FXML
    private TextField clientPhoneField;

    public static boolean zapros;


    @FXML
    void initialize(){

        zapros = false;

        if (client.getFirma() != null) {
            System.out.println("Заполняем данные");
            clientCarField.setText(client.getCar());
            clientFirmaField.setText(client.getFirma());
            clientKField.setText(client.getK() + "");
            clientPhoneField.setText(client.getPhone());
        }

    }

    @FXML
    private void handlerCancel(){
        Stage stage = (Stage) clientCarField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handlerOk(){
        if (client.getFirma() == null) {
            int max = 0;
            for (Client cl: Controller_client.listClient)
                if (cl.getId() > max) max = cl.getId();
            client.setId(max + 1);
        }
        client.setPhone(clientPhoneField.getText());
        client.setCar(clientCarField.getText());
        client.setK(Integer.parseInt(clientKField.getText()));
        client.setFirma(clientFirmaField.getText());

        zapros = true;

        handlerCancel();
    }
}
