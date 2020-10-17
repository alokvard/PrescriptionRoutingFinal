import java.util.*;

public class Router {
    private Pharmacy[] pharmacies;

    public Router(Pharmacy[] pharmacies) {
        this.pharmacies = pharmacies;
    }

    public Router() {

    }

    public Pharmacy[] getPharmacies() {
        return pharmacies;
    }

    /**+
     *
     * @param pharmacies
     */
    public void setPharmacies(Pharmacy[] pharmacies) {
        this.pharmacies = pharmacies;
    }

    /**+
     *
     * @param order
     * @return
     */
    public Assignment[] assign(Order order){
        if(order == null || order.getItems() == null) return null;

        final int orderSize = order.getItems().length;
        if(orderSize == 0) return null;

        int[] minEstimates = new int[orderSize];

        Pharmacy[] minPricePharmacies = new Pharmacy[orderSize];

        Arrays.fill(minEstimates, Integer.MAX_VALUE);

        for(int i = 0; i < pharmacies.length; i++){
            Pharmacy currentPharmacy = pharmacies[i];
            int[] currPharmacyEstimates = currentPharmacy.estimateEachItemCost(order.getItems());

            for(int j = 0; j < minEstimates.length; j++){
                if(minEstimates[j] < currPharmacyEstimates[j]) continue;

                minEstimates[j] = currPharmacyEstimates[j];
                minPricePharmacies[j] = currentPharmacy;
            }
        }

        Map<Pharmacy, List<OrderItem>> assignmentMap = new HashMap<>();

        for(int i = 0; i < orderSize; i++){
            if(minEstimates[i] == Integer.MAX_VALUE) return null;
            if(!assignmentMap.containsKey(minPricePharmacies[i])){
                assignmentMap.put(minPricePharmacies[i], new ArrayList<>());
            }
            assignmentMap.get(minPricePharmacies[i]).add(order.getItems()[i]);
        }

        Assignment[] assignments = new Assignment[assignmentMap.size()];
        int i = 0;

        for(Map.Entry<Pharmacy, List<OrderItem>> entry : assignmentMap.entrySet()){
            assignments[i] = new Assignment();
            assignments[i].setPharmacy(entry.getKey());
            assignments[i].setItems(entry.getValue().toArray(new OrderItem[0]));
            i++;
        }

        return assignments;
    }
}
