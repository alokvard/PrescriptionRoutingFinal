import java.util.Arrays;

public class Assignment {

    //start properties
    private OrderItem[] items;
    private Pharmacy pharmacy;
    //end properties

    public Assignment(OrderItem[] items, Pharmacy pharmacy) {
        this.items = items;
        this.pharmacy = pharmacy;
    }

    public Assignment() {

    }

    public OrderItem[] getItems() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = items;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "items=" + Arrays.toString(items) +
                ", pharmacy=" + pharmacy.getName() +
                '}';
    }
}
