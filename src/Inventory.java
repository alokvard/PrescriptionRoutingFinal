public class Inventory {
    //Region properties
    private int cost;
    private String drug;

    //assumption : assuming that quantity of a drug is stored in the Inventory class, since we are storing information
    //pertaining to the drug inventory in Inventory Object
    private int quantity;
    //EndRegion properties

    //Region constructor
    public Inventory(int cost, String drug, int quantity){
        this.cost = cost;
        this.drug = drug;
        this.quantity = quantity;
    }

    public Inventory() {

    }
    //EndRegion constructor

    //Region Getter/Setters
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
    //EndRegion Getter/Setters

    //Region Public methods
    /**
     * updates the quantity of the drug
     * @param quantityChange change in the amount of drug
     * @param added pass true if the quantity increased by adding more quantity
     */
    public void updateQuantity(int quantityChange,  boolean added){
        this.quantity += added ? quantityChange : -1 * quantityChange;
    }
    //EndRegion Public methods
}
