package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_changeData {

    public static DataT dataT;

    @FXML
    private TextField dataInputField;
    @FXML
    private TextField dataOutputField;

    public static boolean zapros;


    @FXML
    void initialize(){

        zapros = false;

        if(dataT.getData_input() != null) {
            dataInputField.setText(dataT.getData_input());
           dataOutputField.setText(dataT.getData_output());
        }
    }

    @FXML
    private void handlerCancel(){
        Stage stage = (Stage) dataInputField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handlerOk(){
        if (dataT.getData_input() == null) {
            int max = 0;
            for (DataT cl: Controller_data.listData)
                if (cl.getId() > max) max = cl.getId();
            dataT.setId(max + 1);
        }
        dataT.setData_input(dataInputField.getText());
        dataT.setData_output(dataOutputField.getText());

        zapros = true;

        handlerCancel();
    }
}
