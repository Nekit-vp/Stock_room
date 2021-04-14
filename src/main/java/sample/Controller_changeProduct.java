package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_changeProduct {

    public static Product product;

    @FXML
    private TextField productNameField;
    @FXML
    private TextField productDisField;
    @FXML
    private TextField productWeightField;

    @FXML
    private ComboBox<Integer> comboBox_client;
    @FXML
    private ComboBox<Integer> comboBox_place;
    @FXML
    private ComboBox<Integer> comboBox_data;

    private ObservableList<Integer> list_client = FXCollections.observableArrayList();
    private ObservableList<Integer> list_place = FXCollections.observableArrayList();
    private ObservableList<Integer> list_data = FXCollections.observableArrayList();

    public static boolean zapros;

    @FXML
    void initialize(){

        zapros = false;

        for (Client client : Controller_client.listClient) list_client.add(client.getId());
        for (Stock stock : Controller_stock.listStock) list_place.add(stock.getId());
        for (DataT dataT : Controller_data.listData) list_data.add(dataT.getId());

        comboBox_client.setItems(list_client);
        comboBox_data.setItems(list_data);
        comboBox_place.setItems(list_place);

        if(product.getName() != null) {
            productNameField.setText(product.getName());
            productDisField.setText(product.getDiscription());
            productWeightField.setText(product.getWeight() + "");
            comboBox_place.setValue(product.getId_place());
            comboBox_data.setValue(product.getId_date());
            comboBox_client.setValue(product.getId_client());
        }

    }

    @FXML
    private void handlerCancel(){
        Stage stage = (Stage) comboBox_client.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handlerOk(){
        if (product.getName() == null) {
            int max = 0;
            for (Product cl: Controller_product.listProduct)
                if (cl.getId() > max) max = cl.getId();
            product.setId(max + 1);
        }
        product.setName(productNameField.getText());
        product.setDiscription(productDisField.getText());
        product.setWeight(Integer.parseInt(productWeightField.getText()));
        product.setId_client(comboBox_client.getValue());
        product.setId_place(comboBox_place.getValue());
        product.setId_date(comboBox_data.getValue());

        zapros = true;

        handlerCancel();
    }


}
