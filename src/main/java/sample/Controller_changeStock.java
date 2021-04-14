package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_changeStock {

    public static Stock stock;

    @FXML
    private TextField stockAddressField;
    @FXML
    private TextField stockAllField;
    @FXML
    private TextField stockFactField;

    public static boolean zapros;

    @FXML
    void initialize(){

        zapros = false;


        if (stock.getAddress() != null) {
            System.out.println("Заполняем данные");
            stockAddressField.setText(stock.getAddress());
            stockAllField.setText(stock.getAll() + "");
            stockFactField.setText(stock.getFact() + "");
        }

    }

    @FXML
    private void handlerCancel(){
        Stage stage = (Stage) stockFactField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handlerOk(){
        if (stock.getAddress() == null) {
            System.out.println(1);
            int max = 0;
            for (Stock cl: Controller_stock.listStock)
                if (cl.getId() > max) max = cl.getId();
            stock.setId(max + 1);
        }
        stock.setFact(Integer.parseInt(stockFactField.getText()));
        stock.setAll(Integer.parseInt(stockAllField.getText()));
        stock.setAddress(stockAddressField.getText());

        zapros = true;

        handlerCancel();
    }
}
