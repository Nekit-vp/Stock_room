package sample;

public class Product {
    private int id;
    private String name;
    private String discription;
    private int weight;
    private int id_client;
    private int id_date;
    private int id_place;

    public Product() {
    }

    public Product(int id, String name, String discription, int weight, int id_client, int id_date, int id_place) {
        this.id = id;
        this.name = name;
        this.discription = discription;
        this.weight = weight;
        this.id_client = id_client;
        this.id_date = id_date;
        this.id_place = id_place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_date() {
        return id_date;
    }

    public void setId_date(int id_date) {
        this.id_date = id_date;
    }

    public int getId_place() {
        return id_place;
    }

    public void setId_place(int id_place) {
        this.id_place = id_place;
    }
}
