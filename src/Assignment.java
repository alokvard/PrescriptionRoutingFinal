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
    public Assignment() {

    }

    public Assignment(List<OrderItem> items, Pharmacy pharmacy) {
        this.items = items;
        this.pharmacy = pharmacy;
    }

    public Assignment(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
        this.items = new ArrayList<>();
    }

    public Assignment(Assignment assignment) {
        this.pharmacy = assignment.pharmacy;
        this.items = new ArrayList<>();
        for (OrderItem cloneOrderItem: assignment.getItemsList()) {
            OrderItem curr = new OrderItem(cloneOrderItem);
            this.addItem(curr);
        }
    }
    //EndRegion constructor

    //Region Getter/Setters
    public OrderItem[] getItems() {
        OrderItem[] ordItems = new OrderItem[items.size()];
        for(int i = 0; i < items.size(); i++){
            ordItems[i] = items.get(i);
        }
        return ordItems;
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

    //region public methods

    /**+
     * adds an orderitem to this assignment
     * @param item orderitem to be added
     */
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
    //EndRegion public methods
}
