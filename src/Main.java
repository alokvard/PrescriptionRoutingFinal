import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Main {
    private static ArrayList<String> drugsNames;
    private static ArrayList<String> pharmaciesNames;
    private static final int orderItemSize = 3;

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

    private static Inventory[] generateInventory(int size, int priceMin, int priceMax,
                                              int quantityMin, int quantityMax){
        if (size < 0) return null;

        size = Math.min(size, drugsNames.size());
        Inventory[] inventory = new Inventory[size];

        Set<Integer> set = new HashSet<>();
        Random rand = new Random();
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

    private static Pharmacy[] pharmacies;

    public static void main(String[] args) {
        String drugsFileName = "Resources/Drugs.txt";
        String pharmacyFileName = "Resources/Pharmacies.txt";
        drugsNames = readData(drugsFileName);
        pharmaciesNames = readData(pharmacyFileName);
        final int pharmaciesSize = pharmaciesNames.size();
        pharmacies = new Pharmacy[pharmaciesSize];

        for (int i = 0; i < pharmaciesSize; i++) {
            pharmacies[i] = new Pharmacy();
            pharmacies[i].setName(pharmaciesNames.get(i));
            Inventory[] currInventory = generateInventory(10, 0, 10, 1, 10);
            pharmacies[i].setInventory(currInventory);
        }

        testOrderFulfillment();
    }

    private static void testOrderFulfillment() {
        for (int orderCount = 0; orderCount < 10; orderCount++) {
            generateAndFulfilOrder();
        }
    }

    private static void generateAndFulfilOrder() {
        Order order = new Order();
        OrderItem[] items = generateOrderItems(orderItemSize, order, 1, 4);
        order.setItems(items);

        Router router = new Router(pharmacies);

        Assignment[] assignments = router.assign(order);

        //printAssignmentBreak();
        for (Assignment assignment : assignments) {
            System.out.println(assignment.toString());
        }
        printAssignmentBreak();
    }

    private static void printAssignmentBreak() {
        System.out.println("---------------------------------------");
    }
}
