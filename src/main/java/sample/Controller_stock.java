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

public class Controller_stock {

    public final static ObservableList<Stock> listStock = FXCollections.observableArrayList();

    @FXML
    private TableView<Stock> tableViewStock;
    @FXML
    private TableColumn<Object, Object> columnid;
    @FXML
    private TableColumn<Object, Object> columnAddress;
    @FXML
    private TableColumn<Object, Object> columnAll;
    @FXML
    private TableColumn<Object, Object> columnFact;

    @FXML
    void initialize(){

        columnid.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnAll.setCellValueFactory(new PropertyValueFactory<>("all"));
        columnFact.setCellValueFactory(new PropertyValueFactory<>("fact"));

        tableViewStock.setItems(listStock);

        String query = "select * from \"public\".stocks";

        try {
            Statement statement = Controller_client.dbWorker.getConnection().createStatement();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                Stock stock = new Stock();
                stock.setId(set.getInt("id"));
                stock.setAddress(set.getString("adress"));
                stock.setAll(set.getInt("capacity_all"));
                stock.setFact(set.getInt("capacity_fact"));
                listStock.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handlerRefresh() {
        listStock.clear();
        String query = "select * from \"public\".stocks";
        try {
            Statement statement = Controller_client.dbWorker.getConnection().createStatement();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                Stock stock = new Stock();
                stock.setId(set.getInt("id"));
                stock.setAddress(set.getString("adress"));
                stock.setAll(set.getInt("capacity_all"));
                stock.setFact(set.getInt("capacity_fact"));
                listStock.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlerDelete() {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        if (stock != null) {

            boolean del = true;
            for (Product product :
                    Controller_product.listProduct) {
                if (product.getId_place() == stock.getId()) del = false;
            }

            if (del) {
                try {
                    String query = "delete from \"public\".stocks where \"id\" = " + stock.getId();
                    Statement statement = Controller_client.dbWorker.getConnection().createStatement();
                    statement.executeUpdate(query);
                    System.out.println(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                listStock.remove(stock);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Данный склад связан в главной таблице с товаром");
                alert.setContentText("Удалите сначала товар, с которым связан данный склад");
                alert.show();
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Объект не выбран");
            alert.setContentText("Выберите объект для удаления");
            alert.show();
        }
    }


    @FXML
    private void handlerChange() throws IOException {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        if (stock != null) {

            Controller_changeStock.stock = stock;

            Stage stage = new Stage();
            stage.setTitle("Изменить");
            stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("changeStock.fxml"))));
            stage.setResizable(false);
            stage.showAndWait();

            if (Controller_changeStock.zapros) {

                String quety1 = "update stocks set \"adress\" = '" + stock.getAddress() + "', \n" +
                        "                    \"capacity_all\" = " + stock.getAll() + ",\n" +
                        "                    \"capacity_fact\" = " + stock.getFact() + "\n" +
                        "                    where \"id\"=" + stock.getId() + ";";

                System.out.println(quety1);


                int selectstock = tableViewStock.getSelectionModel().getSelectedIndex();
                listStock.set(selectstock, stock);
                try {
                    Statement statement = Controller_client.dbWorker.getConnection().createStatement();
                    statement.executeUpdate(quety1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Товар не выбран");
            alert.setContentText("Выберите товар");
            alert.show();
        }
    }

    @FXML
    private void handlerAdd() throws IOException {
        Stock stock = new Stock();

        Controller_changeStock.stock = stock;

        Stage stage = new Stage();
        stage.setTitle("Создать");
        stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("changeStock.fxml"))));
        stage.setResizable(false);
        stage.showAndWait();

        if (Controller_changeStock.zapros) {
            String query = "insert into \"public\".stocks values (" + stock.getId() + ",'" + stock.getAddress() + "'," +
                    stock.getAll() + "," + stock.getFact() + ")";
            System.out.println(query);

            listStock.add(stock);
            try {
                Statement statement = Controller_client.dbWorker.getConnection().createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }






}
