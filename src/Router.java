import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.security.MessageDigest;

public class Router {
    //Region properties
    private Pharmacy[] pharmacies;
    private static List<Assignment> bestAssignments;
    private static float bestCost;
    private static MessageDigest md;
    private static String OrderDestination;
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
    //EndRegion Getter/Setters

    //Region private helper functions
    /**
     * find the minimum estimate by generating all the combinations possible to get the order fulfilled
     * from the pharmacies recursively
     * @param items array of orderitem for an order
     * @param index index of the orderitem being processed from the array of orderitems
     * @param pharmacyOptions pharmacies that can fulfill this orderitem
     * @param assignments possible combination of pharmacy assignments that fulfills the order
     */
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

            if(assignments.get(p).getItemsList().size() == 0){
                assignments.remove(p);
            }
        }
    }

    /**
     * calculates the price of current assignment and overall best estimate based on that
     * @param assignments possible combination of pharmacy assignments that fulfills the order
     */
    private void calculateCurrentAssignmentCost(Map<Pharmacy, Assignment>assignments) {
        Map<OrderItem, List<Pharmacy>> map = new HashMap<>();
        float currentCost = 0.0f;

        for (Map.Entry<Pharmacy, Assignment> entry: assignments.entrySet()) {
            currentCost += getShippingCostFor(entry.getKey().getLocation())/100;
            currentCost += entry.getKey().estimateOrderCost(entry.getValue().getItems());
        }
        if (Float.compare(bestCost, currentCost) <= 0) return;

        bestCost = currentCost;
        bestAssignments.clear();

        for ( Assignment currAssignment : assignments.values() ) {
            bestAssignments.add(new Assignment(currAssignment));
        }
    }

    /**
     * gets the shipping price
     * @param location pharmacy location
     * @return shipping price
     */
    private static float getShippingCostFor(String location){
        //use cache
        String destLoc = OrderDestination + location;
        String hex = (new HexBinaryAdapter()).marshal(md.digest(destLoc.getBytes()));
        BigInteger bi = new BigInteger(hex,16);
        return bi.mod(new BigInteger("1000")).floatValue();
    }

    /**
     * check if we didn't find any pharmacy for an orderitem
     * @param pharmacyOptions list of pharmacies that can fulfill and orderitem
     * @return true if all orderitems have corresponding pharmacies else false
     */
    private boolean canFulfillOrder(List<List<Pharmacy>> pharmacyOptions){
        for (List<Pharmacy> list: pharmacyOptions) {
            if(list.size() == 0) return false;
        }

        return true;
    }

    /**+
     * generates the assignment array from bestassignments list
     * @return array of assignment
     */
    private Assignment[] generateBestAssignments(){
        Assignment[] assignmentsFound = new Assignment[bestAssignments.size()];

        for(int i = 0; i < bestAssignments.size(); i++){
            assignmentsFound[i] = bestAssignments.get(i);
        }
        return assignmentsFound;
    }
    //EndRegion private helper functions

    //Region public functions
    /**
     * finds the assignments to fulfill an order
     * @param order given order
     * @return array of assignments that fulfill the order
     */
    public Assignment[] assign(Order order){
        if(order == null || order.getItems() == null) return null;

        final int orderSize = order.getItems().length;
        List<List<Pharmacy>> pharmacyOptions = new ArrayList<>();
        Map<Pharmacy, Assignment> assignments = new HashMap<>();

        OrderDestination = order.getDestination();
        bestCost = Float.MAX_VALUE;
        bestAssignments = new ArrayList<>();

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

        //check if order cannot be fulfilled
        if(!canFulfillOrder(pharmacyOptions)) return new Assignment[0];

        //try all assignments
        assignHelper(items, 0, pharmacyOptions,assignments);
        Assignment[] assignmentsFound = generateBestAssignments();
        return assignmentsFound;
    }
    //EndRegion public functions
}
