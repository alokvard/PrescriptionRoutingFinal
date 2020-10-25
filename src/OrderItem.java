public class OrderItem {

    //Region properties
    private String drug;
    private Order order;
    private int quantity;
    //EndRegion properties

    //Region constructor
    public OrderItem(String drug, Order order, int quantity) {
        this.drug = drug;
        this.order = order;
        this.quantity = quantity;
    }

    public OrderItem() {

    }

    public OrderItem(OrderItem cloneOrderItem) {
        this.drug = cloneOrderItem.getDrug();
        this.order = cloneOrderItem.getOrder();
        this.quantity = cloneOrderItem.getQuantity();
    }
    //EndRegion constructor

    //Region Getter/Setters
    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    //EndRegion Getter/Setters

    @Override
    public String toString() {
        return "OrderItem {" +
                "drug = '" + drug + '\'' +
                ", order = " + order.getOrderId() +
                ", quantity = " + quantity +
                '}';
    }
}
