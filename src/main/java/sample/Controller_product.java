package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller_product {

    public final static ObservableList<Product> listProduct = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> tableViewProduct;
    @FXML
    private TableColumn<Object, Object> columnid;
    @FXML
    private TableColumn<Object, Object> columnName;
    @FXML
    private TableColumn<Object, Object> columnDescription;
    @FXML
    private TableColumn<Object, Object> columnWeight;
    @FXML
    private TableColumn<Object, Object> columnid_client;
    @FXML
    private TableColumn<Object, Object> columnid_date;
    @FXML
    private TableColumn<Object, Object> columnid_place;

    @FXML
    void initialize() {

        columnid.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("discription"));
        columnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        columnid_client.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        columnid_place.setCellValueFactory(new PropertyValueFactory<>("id_place"));
        columnid_date.setCellValueFactory(new PropertyValueFactory<>("id_date"));

        tableViewProduct.setItems(listProduct);

        String query = "select * from \"public\".product";

        try {
            Statement statement = Controller_client.dbWorker.getConnection().createStatement();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                Product product = new Product();
                product.setId(set.getInt("id"));
                product.setName(set.getString("name"));
                product.setDiscription(set.getString("discription"));
                product.setWeight(set.getInt("weight"));
                product.setId_client(set.getInt("id_client"));
                product.setId_date(set.getInt("id_date"));
                product.setId_place(set.getInt("id_place"));
                listProduct.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handlerRefresh() {
        listProduct.clear();
        String query = "select * from \"public\".product";
        try {
            Statement statement = Controller_client.dbWorker.getConnection().createStatement();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                Product product = new Product();
                product.setId(set.getInt("id"));
                product.setName(set.getString("name"));
                product.setDiscription(set.getString("discription"));
                product.setWeight(set.getInt("weight"));
                product.setId_client(set.getInt("id_client"));
                product.setId_date(set.getInt("id_date"));
                product.setId_place(set.getInt("id_place"));
                listProduct.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlerDelete() {
        Product product = tableViewProduct.getSelectionModel().getSelectedItem();
        if (product != null) {
            try {
                String query = "delete from \"public\".product where \"id\" = " + product.getId();
                Statement statement = Controller_client.dbWorker.getConnection().createStatement();
                statement.executeUpdate(query);
                System.out.println(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            listProduct.remove(product);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Объект не выбран");
            alert.setContentText("Выберите объект для удаления");
            alert.show();
        }
    }

    @FXML
    private void handlerAdd() throws IOException {
        Product product = new Product();

        Controller_changeProduct.product = product;

        Stage stage = new Stage();
        stage.setTitle("Создать");
        stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("changeProduct.fxml"))));
        stage.setResizable(false);
        stage.showAndWait();

        if (Controller_changeProduct.zapros) {

            String query = "insert into \"public\".product values (" + product.getId() + ",'" + product.getName() + "','" +
                    product.getDiscription() + "'," + product.getWeight() + "," + product.getId_client() + "," + product.getId_date() +
                    "," + product.getId_place() + ")";
            System.out.println(query);

            listProduct.add(product);
            try {
                Statement statement = Controller_client.dbWorker.getConnection().createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handlerChange() throws IOException {
        Product product = tableViewProduct.getSelectionModel().getSelectedItem();
        if (product != null) {

            Controller_changeProduct.product = product;

            Stage stage = new Stage();
            stage.setTitle("Изменить");
            stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("changeProduct.fxml"))));
            stage.setResizable(false);
            stage.showAndWait();

            if (Controller_changeProduct.zapros) {

                String query = "update product set \"name\" = '" + product.getName() + "', \n" +
                        "                    \"discription\" = '" + product.getDiscription() + "',\n" +
                        "                    \"weight\" = " + product.getWeight() + ",\n" +
                        "                    \"id_client\" = " + product.getId_client() + ",\n" +
                        "                    \"id_date\" = " + product.getId_date() + ",\n" +
                        "                    \"id_place\" = " + product.getId_place() + "\n" +
                        "                    where \"id\" = " + product.getId() + ";";
                System.out.println(query);

                int selectclient = tableViewProduct.getSelectionModel().getSelectedIndex();
                listProduct.set(selectclient, product);
                try {
                    Statement statement = Controller_client.dbWorker.getConnection().createStatement();
                    statement.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Товар не выбран");
            alert.setContentText("Выберите товар ");
            alert.show();
        }
    }
}
