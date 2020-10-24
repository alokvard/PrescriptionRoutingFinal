import sun.misc.ASCIICaseInsensitiveComparator;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.security.MessageDigest;

public class Router {
    //Region properties
    private Pharmacy[] pharmacies;
    //EndRegion properties

    //Region constructor
    public Router(Pharmacy[] pharmacies) {
        this.pharmacies = pharmacies;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Router() {}
    //EndRegion constructor

    //Region Getter/Setters
    public Pharmacy[] getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(Pharmacy[] pharmacies) {
        this.pharmacies = pharmacies;
    }

    static List<Assignment> bestAssignments;
    static float bestCost;
    static MessageDigest md;
    static String OrderDestination;
    //EndRegion Getter/Setters


    //Region private helper functions
    /**
     * generates assignments array from the assignments map
     * @param assignmentMap assignments HashMap which maps a pharmacy to the list of ordertitems it fulfilled
     * @return returns array of assignments
     */
    private Assignment[] generateAssignments(Map<Pharmacy, List<OrderItem>> assignmentMap){
        Assignment[] assignments = new Assignment[assignmentMap.size()];
        int i = 0;
        //generate assignments from map
        for(Map.Entry<Pharmacy, List<OrderItem>> entry : assignmentMap.entrySet()){
            assignments[i] = new Assignment(entry.getValue(), entry.getKey());
            assignments[i].setItems(entry.getValue().toArray(new OrderItem[0]));
            i++;
        }

        return assignments;
    }
    //EndRegion private helper functions

    //Region public functions
    /**
     * finds assignments to fulfil an order
     * @param order input order
     * @return array of assignments to fulfill the order
     *         returns null if the order can't be fulfilled
     */
    public Assignment[] assign(Order order){
        if(order == null || order.getItems() == null) return null;

        final int orderSize = order.getItems().length;
        if(orderSize == 0) return null;

        int[] minEstimates = new int[orderSize];
        OrderDestination = order.getDestination();
        bestCost = Float.MIN_VALUE;
        bestAssignments = new ArrayList<>();
        Pharmacy[] minPricePharmacies = new Pharmacy[orderSize];
        Arrays.fill(minEstimates, Integer.MAX_VALUE);

        //find min prices
        for (Pharmacy currentPharmacy : pharmacies) {
            int[] currPharmacyEstimates = currentPharmacy.estimateEachItemCost(order.getItems());

            for (int j = 0; j < minEstimates.length; j++) {
                if (minEstimates[j] <= currPharmacyEstimates[j]) continue;
                minEstimates[j] = currPharmacyEstimates[j];
                minPricePharmacies[j] = currentPharmacy;
            }
        }

        Map<Pharmacy, List<OrderItem>> assignmentMap = new HashMap<>();
        //combine pharmacies
        for(int i = 0; i < orderSize; i++){
            if(minEstimates[i] == Integer.MAX_VALUE) return null;
            if(!assignmentMap.containsKey(minPricePharmacies[i])){
                assignmentMap.put(minPricePharmacies[i], new ArrayList<>());
            }
            assignmentMap.get(minPricePharmacies[i]).add(order.getItems()[i]);
        }

        Assignment[] assignments = generateAssignments(assignmentMap);

        return assignments;
    }


    public Assignment[] assign1(Order order){
        if(order == null || order.getItems() == null) return null;
        final int orderSize = order.getItems().length;
        Map<OrderItem, List<Pharmacy>> map = new HashMap<>();
        List<List<Pharmacy>> pharmacyOptions = new ArrayList<>();
        int[] minEstimates = new int[orderSize];

        for(int i = 0; i < orderSize; i++){
            pharmacyOptions.add(new ArrayList<>());
        }

        //get all possible pharmacies for orderitem
        OrderItem[] items = order.getItems();
        for (Pharmacy currentPharmacy : pharmacies) {
            int[] currPharmacyEstimates = currentPharmacy.estimateEachItemCost(items);

            for (int j = 0; j < currPharmacyEstimates.length; j++) {
               if(currPharmacyEstimates[j] == Integer.MAX_VALUE) continue;
               pharmacyOptions.get(j).add(currentPharmacy);
            }
        }
        //order cannot be fulfilled
        for (List<Pharmacy> list: pharmacyOptions) {
            if(list.size() == 0) return new Assignment[0];
        }

        //try all assignments
        Map<Pharmacy, Assignment>assignments = new HashMap<>();

        assignHelper(items, 0, pharmacyOptions,assignments);

        return (Assignment[]) bestAssignments.toArray();
    }

    private void assignHelper(OrderItem[] items, int index, List<List<Pharmacy>> pharmacyOptions,Map<Pharmacy, Assignment> assignments){
        if(index == items.length){
            calculateCurrentAssignmentCost(assignments);
            return;
        }

        for (Pharmacy p: pharmacyOptions.get(index)) {

            if(!assignments.containsKey(p)){
                assignments.put(p, new Assignment(p));
            }

            assignments.get(p).addItem(items[index]);
            assignHelper(items, index + 1, pharmacyOptions, assignments);
            assignments.get(p).getItemsList().remove(assignments.get(p).getItemsList().size() - 1);
        }

    }

    private void calculateCurrentAssignmentCost(Map<Pharmacy, Assignment>assignments) {
        Map<OrderItem, List<Pharmacy>> map = new HashMap<>();
        float currentCost = 0.0f;

        for (Map.Entry<Pharmacy, Assignment> entry: assignments.entrySet()) {
            currentCost += getShippingCostFor(entry.getKey().getLocation())/100;
            currentCost += entry.getKey().estimateOrderCost(entry.getValue().getItems());
        }
        if (Float.compare(bestCost, currentCost) <= 0) return;

        bestCost = currentCost;
        bestAssignments.addAll(assignments.values());
    }

    private static float getShippingCostFor(String location){
        //use cache
        String destLoc = OrderDestination + location;
        String hex = (new HexBinaryAdapter()).marshal(md.digest(destLoc.getBytes()));
        final float n = (float) Integer.parseInt(hex, 16);
        return n%1000;
    }

    //EndRegion public functions
}
