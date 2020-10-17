public class Order {
    private OrderItem[] items;

    public Order(OrderItem[] items) {
        this.items = items;
    }

    public Order() {

    }

    public OrderItem[] getItems() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = items;
    }
}
