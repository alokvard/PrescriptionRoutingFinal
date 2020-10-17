import java.util.HashMap;
import java.util.Map;

public class Pharmacy {
    private Inventory[] inventory;
    private String name;

    private Map<String , Inventory> inventoryItems;
    
    public Pharmacy() {
        inventory = new Inventory[0];
        name = "";
        inventoryItems = new HashMap<>();
    }

    public Pharmacy(Inventory[] inventory, String name) {
        this.inventory = inventory;

        if(this.inventory == null){
            this.inventory = new Inventory[0];
        }

        this.name = name;
        if(this.name == null){
            this.name = "";
        }

        inventoryItems = new HashMap<>();
        defaultPopulateInventory();
    }

    private void defaultPopulateInventory() {
        for(Inventory inventoryItem : this.inventory){
            inventoryItems.put(inventoryItem.getDrug(), inventoryItem);
        }
    }

    public Inventory[] getInventory() {
        return inventory;
    }

    public void setInventory(Inventory[] inventory) {

        this.inventory = inventory;
        defaultPopulateInventory();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void updateInventorySize(Inventory inventory, int quantity){
        if(inventory == null || quantity <=0){
            return;
        }

        String drugName = inventory.getDrug();

        if(drugName == null || drugName.isEmpty()) return;

        inventory.setQuantity(quantity);
    }

    public void updateInventoryCost(Inventory inventory, int cost){
        if(inventory == null || cost <= 0){
            return;
        }

        String drugName = inventory.getDrug();

        if(drugName == null || drugName.isEmpty()) return;

        inventory.setCost(cost);
    }

    public int estimateOrderItemCost(OrderItem orderItem){

        if(!inventoryItems.containsKey(orderItem.getDrug())){
            return Integer.MAX_VALUE;
        }

        long quantity = inventoryItems.get(orderItem.getDrug()).getQuantity();

        if(quantity < orderItem.getQuantity()) return Integer.MAX_VALUE;

        int estimateCost = orderItem.getQuantity() * inventoryItems.get(orderItem.getDrug()).getCost();

        return estimateCost == 0? Integer.MAX_VALUE : estimateCost;
    }

    public int estimateOrderCost(OrderItem[] orderItems){

        int orderItemEstimate = 0;
        int totOrderItemEstimate = 0;
        for(OrderItem item : orderItems){
            orderItemEstimate = estimateOrderItemCost(item);
            if(orderItemEstimate == Integer.MAX_VALUE){
                return Integer.MAX_VALUE;
            }

            totOrderItemEstimate += orderItemEstimate;
        }
        return totOrderItemEstimate;
    }

    public int[] estimateEachItemCost(OrderItem[] orderItems){
        if(orderItems == null || orderItems.length == 0) return new int[0];

        int[] estimates = new int[orderItems.length];

        for(int i = 0; i < orderItems.length; i++){
            estimates[i] = estimateOrderItemCost(orderItems[i]);
        }
        return estimates;
    }
}
