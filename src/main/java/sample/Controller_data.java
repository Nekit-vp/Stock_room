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

public class Controller_data {

    public final static ObservableList<DataT> listData = FXCollections.observableArrayList();

    @FXML
    private TableView<DataT> tableViewData;
    @FXML
    private TableColumn<Object, Object> columnid;
    @FXML
    private TableColumn<Object, Object> columndata_input;
    @FXML
    private TableColumn<Object, Object> columndata_output;

    @FXML
    void initialize(){

        columnid.setCellValueFactory(new PropertyValueFactory<>("id"));
        columndata_input.setCellValueFactory(new PropertyValueFactory<>("data_input"));
        columndata_output.setCellValueFactory(new PropertyValueFactory<>("data_output"));

        tableViewData.setItems(listData);

        String query = "select * from \"date\"";

        try {
            Statement statement = Controller_client.dbWorker.getConnection().createStatement();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                DataT dataT = new DataT();
                dataT.setId(set.getInt("id"));
                dataT.setData_input(set.getString("date_input"));
                dataT.setData_output(set.getString("date_output"));
                listData.add(dataT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handlerRefresh() {
        listData.clear();
        String query = "select * from \"date\"";
        try {
            Statement statement = Controller_client.dbWorker.getConnection().createStatement();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                DataT dataT = new DataT();
                dataT.setId(set.getInt("id"));
                dataT.setData_input(set.getString("date_input"));
                dataT.setData_output(set.getString("date_output"));
                listData.add(dataT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlerDelete() {
        DataT dataT = tableViewData.getSelectionModel().getSelectedItem();
        if (dataT != null) {

            boolean del = true;
            for (Product product :
                    Controller_product.listProduct) {
                if (product.getId_date() == dataT.getId()) del = false;
            }

            if (del) {
                try {
                    String query = "delete from \"public\".\"date\" where \"id\" = " + dataT.getId();
                    Statement statement = Controller_client.dbWorker.getConnection().createStatement();
                    statement.executeUpdate(query);
                    System.out.println(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                listData.remove(dataT);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Данная дата связана в главной таблице с товаром");
                alert.setContentText("Удалите сначала товар, с которым связан данная дата");
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
    private void handlerAdd() throws IOException {
        DataT dataT = new DataT();

        Controller_changeData.dataT = dataT;

        Stage stage = new Stage();
        stage.setTitle("Создать");
        stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("changeData.fxml"))));
        stage.setResizable(false);
        stage.showAndWait();

        if (Controller_changeData.zapros) {

            String query = "insert into \"public\".\"date\" values (" + dataT.getId() + ",'" + dataT.getData_input() + "','" +
                    dataT.getData_output() + "')";
            System.out.println(query);

            listData.add(dataT);
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
        DataT dataT = tableViewData.getSelectionModel().getSelectedItem();
        if (dataT != null) {

            Controller_changeData.dataT = dataT;

            Stage stage = new Stage();
            stage.setTitle("Изменить");
            stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("changeData.fxml"))));
            stage.setResizable(false);
            stage.showAndWait();

            if (Controller_changeData.zapros) {

                String quety1 = "update \"public\".\"date\" set \n" +
                        "        \"date_input\" = '" + dataT.getData_input() + "', \n" +
                        "        \"date_output\" = '" + dataT.getData_output() + "'\n" +
                        "        where \"id\"=1;";

                System.out.println(quety1);


                int selectstock = tableViewData.getSelectionModel().getSelectedIndex();
                listData.set(selectstock, dataT);
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
}
