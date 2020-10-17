public class Order {
    private OrderItem[] items;
    private static int _currentOrderId = 100;
    private int _thisOrderId;

    public Order(OrderItem[] items) {
        _thisOrderId = _currentOrderId++;
        this.items = items;
    }

    public Order() {
        _thisOrderId = _currentOrderId++;
    }

    public OrderItem[] getItems() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return String.valueOf(_thisOrderId);
    }
}
