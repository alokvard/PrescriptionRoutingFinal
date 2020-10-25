import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Main {
    //Region properties
    private static ArrayList<String> drugsNames;
    private static ArrayList<String> pharmaciesNames;
    private static ArrayList<String> destinations;
    private static ArrayList<String> locations;
    private static Pharmacy[] pharmacies;
    private static Random rand = new Random();
    private static final int orderItemSize = 3;
    //EndRegion properties

    //Region private methods
    /**
     * reads Data from a file
     * @param fileName file to be read
     * @return arraylist of lines of file data
     */
    private static ArrayList<String> readData(String fileName){
        ArrayList<String> result = new ArrayList<>();

        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                result.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * generates Inventory randomly
     * @param size size of the inventory to be generated
     * @param priceMin minimum price for the inventory
     * @param priceMax maximum price for the inventory
     * @param quantityMin minimum quantity for the inventory
     * @param quantityMax maximum quantity for the inventory
     * @return array of inventory
     */
    private static Inventory[] generateInventory(int size, int priceMin, int priceMax,
                                              int quantityMin, int quantityMax){
        if (size < 0) return null;

        size = Math.min(size, drugsNames.size());
        Inventory[] inventory = new Inventory[size];

        Set<Integer> set = new HashSet<>();
        int counter = 0;
        while (counter < size) {
            int drugIndex = rand.nextInt(drugsNames.size());
            if (set.contains(drugIndex)) continue;
            set.add(drugIndex);
            String drugName = drugsNames.get(drugIndex);
            inventory[counter] = new Inventory();
            inventory[counter].setDrug(drugName);
            inventory[counter].setCost(priceMin + rand.nextInt(priceMax - priceMin));
            inventory[counter].setQuantity(quantityMin + rand.nextInt(quantityMax - quantityMin));
            counter++;
        }
        return inventory;
    }

    /**
     * generates OrderItems randomly
     * @param size size of the orderitem array to be generated
     * @param order Order linked to these objects
     * @param quantityMin minimum quantity for an orderitem
     * @param quantityMax maximum quantity for an orderitem
     * @return array of orderitem
     */
    private static OrderItem[] generateOrderItems(int size, Order order,
                                              int quantityMin, int quantityMax){
        if(size < 0) return null;

        size  = Math.min(size, drugsNames.size());
        OrderItem[] items = new OrderItem[size];

        Set<Integer> set = new HashSet<>();
        Random rand = new Random();
        int counter = 0;

        while(counter < size) {
            int ind = rand.nextInt(drugsNames.size());
            String drugName = drugsNames.get(ind);
            if (set.contains(ind)) continue;
            set.add(ind);
            items[counter] = new OrderItem();
            items[counter].setDrug(drugName);
            items[counter].setQuantity(quantityMin + rand.nextInt(quantityMax - quantityMin + 1));
            items[counter].setOrder(order);
            counter++;
        }

        return items;
    }

    /**
     * testing the order fulfilment
     */
    private static void testOrderFulfillment() {
        for (int orderCount = 0; orderCount < 10; orderCount++) {
            generateAndFulfilOrder();
        }
    }

    /**
     * generates an order and tries to fulfill the order
     */
    private static void generateAndFulfilOrder() {
        Order order = new Order();
        OrderItem[] items = generateOrderItems(orderItemSize, order, 1, 4);
        order.setItems(items);
        int randInd = rand.nextInt(destinations.size());
        order.setDestination(destinations.get(randInd));

        Router router = new Router(pharmacies);
        Assignment[] assignments = router.assign(order);

        if(assignments == null){
            return;
        }

        //print order
        System.out.println("Order :");
        System.out.println(order.toString());

        System.out.println("Assignments :");
        for (Assignment assignment : assignments) {
            System.out.println(assignment.toString());
        }
        printAssignmentBreak();
    }

    /**
     * prints line break
     */
    private static void printAssignmentBreak() {
        System.out.println("---------------------------------------");
    }

    //EndRegion private methods

    /**
     * runner function
     */
    public static void main(String[] args) {
        String drugsFileName = "./Resources/Drugs.txt";
        String pharmacyFileName = "./Resources/Pharmacies.txt";
        String locationsFileName = "./Resources/Locations.txt";
        String destinationFileName = "./Resources/Destinations.txt";
        drugsNames = readData(drugsFileName);
        pharmaciesNames = readData(pharmacyFileName);
        locations = readData(locationsFileName);
        destinations = readData(destinationFileName);

        final int pharmaciesSize = pharmaciesNames.size();
        pharmacies = new Pharmacy[pharmaciesSize];

        for (int i = 0; i < pharmaciesSize; i++) {
            pharmacies[i] = new Pharmacy();
            pharmacies[i].setName(pharmaciesNames.get(i));
            pharmacies[i].setLocation(locations.get(i));
            Inventory[] currInventory = generateInventory(10, 0, 10, 1, 10);
            pharmacies[i].setInventory(currInventory);
        }
        testOrderFulfillment();
    }
}
