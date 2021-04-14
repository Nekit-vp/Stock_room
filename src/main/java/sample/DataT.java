package sample;

public class DataT {
    private int id;
    private String data_input;
    private String data_output;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DataT() {
    }

    public DataT(int id, String data_input, String data_output) {
        this.id = id;
        this.data_input = data_input;
        this.data_output = data_output;
    }

    public String getData_input() {
        return data_input;
    }

    public void setData_input(String data_input) {
        this.data_input = data_input;
    }

    public String getData_output() {
        return data_output;
    }

    public void setData_output(String data_output) {
        this.data_output = data_output;
    }
}
