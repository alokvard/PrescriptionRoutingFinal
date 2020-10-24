import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assignment {

    //Region properties
    private List<OrderItem> items;
    private Pharmacy pharmacy;
    //EndRegion properties

    //Region constructor
    public Assignment(List<OrderItem> items, Pharmacy pharmacy) {
        this.items = items;
        this.pharmacy = pharmacy;
    }

    public Assignment(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
        this.items = new ArrayList<>();
    }

    //EndRegion constructor

    //Region Getter/Setters
    public OrderItem[] getItems() {
        return (OrderItem[]) items.toArray();
    }

    public List<OrderItem> getItemsList() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = new ArrayList<>(Arrays.asList(items));
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
    //EndRegion Getter/Setters

    public void addItem(OrderItem item){
        items.add(item);
    }

    @Override
    public String toString() {
        return "{\n" +
                "\titems = " + items.toString() +
                ",\n\tpharmacy = " + pharmacy.getName() +
                "\n}\n";
    }
}
