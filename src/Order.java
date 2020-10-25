import java.util.Arrays;

public class Order {

    //Region properties
    private OrderItem[] items;
    private static int _currentOrderId = 100;
    private String destination;
    private int _thisOrderId;
    //EndRegion properties

    //Region constructor
    public Order(OrderItem[] items) {
        _thisOrderId = _currentOrderId++;
        this.items = items;
    }

    public Order() {
        _thisOrderId = _currentOrderId++;
    }
    //EndRegion constructor

    //Region Getter/Setters
    public OrderItem[] getItems() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = items;
    }

    public int getOrderId() {
        return _thisOrderId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    //EndRegion Getter/Setters

    @Override
    public String toString() {
        return "{\n" +
                "\titems=" + Arrays.toString(items) +
                ",\n\tOrderId=" + _thisOrderId +
                ",\n\tDestination=" + destination +
                "\n}\n";
    }
}
