package Models;

public class Producto {
    private String barcode;
    private String name;
    private float cost;
    private Categoria category; // enum

    public Producto(String barcode, String name, float cost, Categoria category) {
        this.barcode = barcode;
        this.name = name;
        this.cost = cost;
        this.category = category;
    }


    public String getBarcode() { return barcode; }
    public String getName() { return name; }
    public float getCost() { return cost; }
    public Categoria getCategory() { return category; }

    public void setBarcode(String barcode) { this.barcode = barcode; }
    public void setName(String name) { this.name = name; }
    public void setCost(float cost) { this.cost = cost; }
    public void setCategory(Categoria category) { this.category = category; }
}
