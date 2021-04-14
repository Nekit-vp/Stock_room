package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller_client {

    public final static ObservableList<Client> listClient = FXCollections.observableArrayList();

    @FXML
    private Tab tabData;
    @FXML
    private Tab tabProduct;
    @FXML
    private Tab tabStock;
    @FXML
    private Tab tabStructure;
    @FXML
    private javafx.scene.control.TabPane TabPane;

    @FXML
    private TableView<Client> tableViewClient;
    @FXML
    private TableColumn<Object, Object> columnId;
    @FXML
    private TableColumn<Object, Object> columnFirma;
    @FXML
    private TableColumn<Object, Object> columnK;
    @FXML
    private TableColumn<Object, Object> columnCar;
    @FXML
    private TableColumn<Object, Object> columnPhone;

    public static DBWorker dbWorker;

    @FXML
    void initialize() throws IOException {


        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnFirma.setCellValueFactory(new PropertyValueFactory<>("firma"));
        columnK.setCellValueFactory(new PropertyValueFactory<>("k"));
        columnCar.setCellValueFactory(new PropertyValueFactory<>("car"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        tableViewClient.setItems(listClient);

        dbWorker = new DBWorker();

        String query = "select * from \"public\".client";

        try {
            Statement statement = dbWorker.getConnection().createStatement();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                Client client = new Client();
                client.setId(set.getInt("id"));
                client.setFirma(set.getString("firma"));
                client.setPhone(set.getString("namber_phone"));
                client.setCar(set.getString("namber_car"));
                client.setK(set.getInt("k"));
                listClient.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("stock.fxml"));
        tabStock.setContent((Node) loader.load());

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("product.fxml"));
        tabProduct.setContent((Node) loader2.load());

        FXMLLoader loader3 = new FXMLLoader();
        loader3.setLocation(getClass().getResource("data.fxml"));
        tabData.setContent((Node) loader3.load());

        FXMLLoader loader4 = new FXMLLoader();
        loader4.setLocation(getClass().getResource("pictureImage.fxml"));
        tabStructure.setContent((Node) loader4.load());


    }


    @FXML
    private void handlerDelete() {
        Client client = tableViewClient.getSelectionModel().getSelectedItem();
        if (client != null) {
            boolean del = true;
            for (Product product :
                    Controller_product.listProduct) {
                if (product.getId_client() == client.getId()) del = false;
            }

            if (del) {
                try {
                    String query = "delete from \"public\".client where \"id\" = " + client.getId();
                    Statement statement = dbWorker.getConnection().createStatement();
                    statement.executeUpdate(query);
                    System.out.println(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                listClient.remove(client);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Клинт связан с главной талбицей");
                alert.setContentText("Удалите сначла продукт с которым связян данный клиент");
                alert.show();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Товар не выбран");
            alert.setContentText("Выберите товар для удаления");
            alert.show();
        }
    }

    @FXML
    private void handlerChange() throws IOException {
        Client client = tableViewClient.getSelectionModel().getSelectedItem();
        if (client != null) {

            Controller_change.client = client;

            Stage stage = new Stage();
            stage.setTitle("Создать");
            stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("change.fxml"))));
            stage.setResizable(false);
            stage.showAndWait();

            if (Controller_change.zapros) {

                String query = "update client set \"firma\" = '" + client.getFirma() + "', \n" +
                        "                    \"namber_car\" = '" + client.getCar() + "',\n" +
                        "                    \"namber_phone\" = '" + client.getPhone() + "', \n" +
                        "                    \"k\" = " + client.getK() + "\n" +
                        "                    where \"id\"=" + client.getId() + ";";
                System.out.println(query);

                int selectclient = tableViewClient.getSelectionModel().getSelectedIndex();
                listClient.set(selectclient, client);
                try {
                    Statement statement = dbWorker.getConnection().createStatement();
                    statement.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Товар не выбран");
            alert.setContentText("Выберите товар для удаления");
            alert.show();
        }
    }

    @FXML
    private void handlerAdd() throws IOException {
        Client client = new Client();

        Controller_change.client = client;

        Stage stage = new Stage();
        stage.setTitle("Создать");
        stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("change.fxml"))));
        stage.setResizable(false);
        stage.showAndWait();

        if(Controller_change.zapros) {

            String query = "insert into \"public\".client values (" + client.getId() + ",'" + client.getFirma() + "','" +
                    client.getPhone() + "','" + client.getCar() + "'," + client.getK() + ")";
            System.out.println(query);

            listClient.add(client);
            try {
                Statement statement = dbWorker.getConnection().createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handlerRefresh() {

        System.out.println(1);
        listClient.clear();
        DBWorker dbWorker1 = new DBWorker();
        dbWorker = dbWorker1;

        String query = "select * from \"public\".client";

        try {
            Statement statement = dbWorker.getConnection().createStatement();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                Client client = new Client();
                client.setId(set.getInt("id"));
                client.setFirma(set.getString("firma"));
                client.setPhone(set.getString("namber_phone"));
                client.setCar(set.getString("namber_car"));
                client.setK(set.getInt("k"));
                listClient.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
