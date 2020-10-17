public class Inventory {
    private int cost;
    private String drug;

    //assumption
    private int quantity;

    public Inventory(int cost, String drug, int quantity){
        this.cost = cost;
        this.drug = drug;
        this.quantity = quantity;
    }

    public Inventory() {

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateQuantity(int quantityChange,  boolean added){

        this.quantity += added ? quantityChange : -1 * quantityChange;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }
}
