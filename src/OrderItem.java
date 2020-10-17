public class OrderItem {
    private String drug;
    private Order order;
    private int quantity;

    public OrderItem(String drug, Order order, int quantity) {
        this.drug = drug;
        this.order = order;
        this.quantity = quantity;
    }

    public OrderItem() {

    }

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

    @Override
    public String toString() {
        return "OrderItem{" +
                "drug='" + drug + '\'' +
                ", order=" + order +
                ", quantity=" + quantity +
                '}';
    }
}
