package sample;

public class Stock {
    private int id;
    private String address;
    private int all;
    private int fact;

    public Stock() {
    }

    public Stock(int id, String address, int all, int fact) {
        this.id = id;
        this.address = address;
        this.all = all;
        this.fact = fact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getFact() {
        return fact;
    }

    public void setFact(int fact) {
        this.fact = fact;
    }
}
