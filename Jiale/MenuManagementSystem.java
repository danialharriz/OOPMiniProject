package Aleysha;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface PriceCalculable{
    public final static double SERVICE_TAX = 0.06;
    public double calcTax(double tax);
}

abstract class MenuItem {
    private double price;
    public abstract String getName();
    public abstract double getPrice();
    public abstract String getDescription();
    public abstract double getTaxPrice();
    public abstract boolean checkItemExist(MenuItem item);
    public double calcTax(double tax){
        return tax*(1+PriceCalculable.SERVICE_TAX);
    }
}

class PromotionItem extends MenuItem {
    private MenuItem item;
    private String promotionDetails;
    private double discountRate;

    public PromotionItem(MenuItem item, String promotionDetails, double discountRate) {
        this.item = item;
        this.promotionDetails = promotionDetails;
        this.discountRate = discountRate;
    }

    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public double getPrice() {
        return item.getPrice();
    }

    public double getTaxPrice(){
        return item.getTaxPrice();
    }

    @Override
    public String getDescription() {
        return item.getDescription();
    }

    public String getPromotionDetails() {
        return promotionDetails;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public MenuItem getItem() {return item;}

    public boolean checkItemExist(MenuItem items){
        String name = item.getName();
        if(items.getName().equals(name)){
            return true;
        }
        else return false;
    };

    public double getDiscountedPrice() {
        return item.getPrice() - (item.getPrice() * discountRate);
    }
}

class Menu {
    private ArrayList<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    public MenuItem findItem(String name) {
        for (MenuItem item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public ArrayList<MenuItem> getPromotionItems() {
        ArrayList<MenuItem> promotionItems = new ArrayList<>();
        for (MenuItem item : items) {
            if (item instanceof PromotionItem) {
                promotionItems.add(item);
            }
        }
        return promotionItems;
    }
}

class Dishes extends MenuItem {
    private double price;
    private char size;
    private String dishType;
    private boolean spicy;

    public Dishes(double price, String dishType, char size, boolean spicy) {
        this.price = price;
        this.dishType = dishType;
        this.size = size;
        this.spicy = spicy;
    }

    @Override
    public String getName() {
        return dishType;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return "Dish Type: " + dishType + ", Size: " + size + ", Spicy: " + spicy;
    }

    public double getTaxPrice() {
        return calcTax(price);
    }

    public char getSize() {
        return size;
    }

    public boolean isSpicy() {
        return spicy;
    }
    public boolean checkItemExist(MenuItem items){
        if(items.getName().equals(dishType)){
            return true;
        }
        else return false;
    };
}

class Drink extends MenuItem {
    private double price;
    private double volume;
    private double sugar;
    private String drinkType;

    public Drink(double price, String drinkType, double volume, double sugar) {
        this.price = price;
        this.drinkType = drinkType;
        this.volume = volume;
        this.sugar = sugar;
    }

    @Override
    public String getName() {
        return drinkType;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public double getTaxPrice() {
        return calcTax(price);
    }

    @Override
    public String getDescription() {
        return "Drink Type: " + drinkType + ", Volume: " + volume + "ml, Sugar: " + sugar + "g";
    }

    public String getDrinkType() {
        return drinkType;
    }

    public double getVolume() {
        return volume;
    }

    public double getSugar() {
        return sugar;
    }
    public boolean checkItemExist(MenuItem items){
        if(items.getName().equals(drinkType)){
            return true;
        }
        else return false;
    };
}

class Dessert extends MenuItem {
    private double price;
    private String dessertType;
    private double size;

    public Dessert(double price, String dessertType, double size) {
        this.price = price;
        this.dessertType = dessertType;
        this.size = size;
    }

    @Override
    public String getName() {
        return dessertType;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public double getTaxPrice() {
        return calcTax(price);
    }

    @Override
    public String getDescription() {
        return "Dessert Type: " + dessertType + ", Size: " + size + "g";
    }

    public String getDessertType() {
        return dessertType;
    }

    public double getSize() {
        return size;
    }
    public boolean checkItemExist(MenuItem items){
        if(items.getName().equals(dessertType)){
            return true;
        }
        else return false;
    };
}

public class MenuManagementSystem {
    private static final String MENU_FILE = "Menu.txt";
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();

    public static void main(String[] args) {
        // Load the menu from a file
        loadMenuFromFile();
        System.out.println("------------------------------------------------------");
        System.out.println("| Welcome to Universal Sambal Menu Management System |");
        System.out.println("------------------------------------------------------");

        // Main menu loop
        while (true) {
            System.out.println("1. View Menu");
            System.out.println("2. Add Item");
            System.out.println("3. Remove Item");
            System.out.println("4. Add Promotion");
            System.out.println("5. View Promotion Items");
            System.out.println("6. Remove Promotion");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    addItem();
                    break;
                case 3:
                    removeItem();
                    break;
                case 4:
                    viewPromotionItems();
                    addPromotion();
                    break;
                case 5:
                    viewPromotionItems();
                    break;
                case 6:
                    removePromotion();
                    break;
                case 7:
                    saveMenuToFile();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void loadMenuFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                switch (parts[0]) {
                    case "Dishes":
                        double dishPrice = Double.parseDouble(parts[2]);
                        char size = parts[3].charAt(0);
                        boolean spicy = Boolean.parseBoolean(parts[4]);
                        menu.addItem(new Dishes(dishPrice, parts[1], size, spicy));
                        if(parts.length > 5 && parts[5].equals("Promotion")){
                            String promotionDetails = parts[7];
                            double discountRate = Double.parseDouble(parts[8]);
                            MenuItem baseItem = menu.findItem(parts[6]);
                            if (baseItem != null) {
                                PromotionItem promoItem = new PromotionItem(baseItem, promotionDetails, discountRate);
                                menu.addItem(promoItem);
                            }
                        }
                        break;
                    case "Drink":
                        double drinkPrice = Double.parseDouble(parts[2]);
                        double volume = Double.parseDouble(parts[3]);
                        double sugar = Double.parseDouble(parts[4]);
                        menu.addItem(new Drink(drinkPrice, parts[1], volume, sugar));
                        if(parts.length > 5 && parts[5].equals("Promotion")){
                            String promotionDetails = parts[7];
                            double discountRate = Double.parseDouble(parts[8]);
                            MenuItem baseItem = menu.findItem(parts[6]);
                            if (baseItem != null) {
                                PromotionItem promoItem = new PromotionItem(baseItem, promotionDetails, discountRate);
                                menu.addItem(promoItem);
                            }
                        }
                        break;
                    case "Dessert":
                        double dessertPrice = Double.parseDouble(parts[2]);
                        double dessertSize = Double.parseDouble(parts[3]);
                        menu.addItem(new Dessert(dessertPrice, parts[1], dessertSize));
                        if(parts.length > 4 && parts[4].equals("Promotion")){
                            String promotionDetails = parts[6];
                            double discountRate = Double.parseDouble(parts[7]);
                            MenuItem baseItem = menu.findItem(parts[5]);
                            if (baseItem != null) {
                                PromotionItem promoItem = new PromotionItem(baseItem, promotionDetails, discountRate);
                                menu.addItem(promoItem);
                            }
                        }
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading menu: " + e.getMessage());
        }
    }

    private static void saveMenuToFile() {
        List<String> updatedMenuItems = new ArrayList<>();

        for (MenuItem item : menu.getItems()) {
            String itemName = item.getName();
            String newItemLine = getItemLine(item);
            boolean itemExists = false;

            // Use checkItemExist method to check if the item exists
            for (MenuItem existingItem : menu.getItems()) {
                if (existingItem.checkItemExist(item)) {
                    // Find the corresponding line in updatedMenuItems and update it
                    for (int i = 0; i < updatedMenuItems.size(); i++) {
                        if (updatedMenuItems.get(i).contains(itemName)) {
                            updatedMenuItems.set(i, newItemLine);
                            itemExists = true;
                            break;
                        }
                    }
                    break;
                }
            }

            if (!itemExists) {
                updatedMenuItems.add(newItemLine);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MENU_FILE))) {
            for (String line : updatedMenuItems) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving menu: " + e.getMessage());
        }
    }

    private static String getItemLine(MenuItem item) {
        if (item instanceof PromotionItem promoItem) {
            MenuItem baseItem = promoItem.getItem();
            if (baseItem instanceof Dishes dish) {
                return String.format("Dishes,%s,%.2f,%c,%b,Promotion,%s,%s,%.2f",
                        dish.getName(), dish.getPrice(), dish.getSize(), dish.isSpicy(),
                        promoItem.getName(), promoItem.getPromotionDetails(), promoItem.getDiscountRate());
            } else if (baseItem instanceof Drink drink) {
                return String.format("Drink,%s,%.2f,%.2f,%.2f,Promotion,%s,%s,%.2f",
                        drink.getName(), drink.getPrice(), drink.getVolume(), drink.getSugar(),
                        promoItem.getName(), promoItem.getPromotionDetails(), promoItem.getDiscountRate());
            } else if (baseItem instanceof Dessert dessert) {
                return String.format("Dessert,%s,%.2f,%.2f,Promotion,%s,%s,%.2f",
                        dessert.getName(), dessert.getPrice(), dessert.getSize(),
                        promoItem.getName(), promoItem.getPromotionDetails(), promoItem.getDiscountRate());
            }
        } else if (item instanceof Dishes dish) {
            return String.format("Dishes,%s,%.2f,%c,%b",
                    dish.getName(), dish.getPrice(), dish.getSize(), dish.isSpicy());
        } else if (item instanceof Drink drink) {
            return String.format("Drink,%s,%.2f,%.2f,%.2f",
                    drink.getName(), drink.getPrice(), drink.getVolume(), drink.getSugar());
        } else if (item instanceof Dessert dessert) {
            return String.format("Dessert,%s,%.2f,%.2f",
                    dessert.getName(), dessert.getPrice(), dessert.getSize());
        }
        return "";
    }

    private static void viewMenu() {
        ArrayList<MenuItem> dishes = new ArrayList<>();
        ArrayList<MenuItem> drinks = new ArrayList<>();
        ArrayList<MenuItem> desserts = new ArrayList<>();
        ArrayList<PromotionItem> promoItems = new ArrayList<>();

        // Separate promotion items first
        for (MenuItem item : menu.getItems()) {
            if (item instanceof PromotionItem) {
                promoItems.add((PromotionItem) item);
            }
        }

        // Process promotion items first to avoid duplicates
        for (PromotionItem promo : promoItems) {
            MenuItem baseItem = promo.getItem();
            if (baseItem instanceof Dishes) {
                dishes.add(promo);
            } else if (baseItem instanceof Drink) {
                drinks.add(promo);
            } else if (baseItem instanceof Dessert) {
                desserts.add(promo);
            }
        }

        // Process other items, add only if they don't have promotions
        for (MenuItem item : menu.getItems()) {
            boolean hasPromotion = false;
            for (PromotionItem promo : promoItems) {
                if (promo.getItem().checkItemExist(item)) {
                    hasPromotion = true;
                    break;
                }
            }

            if (!hasPromotion) {
                if (item instanceof Dishes) {
                    dishes.add(item);
                } else if (item instanceof Drink) {
                    drinks.add(item);
                } else if (item instanceof Dessert) {
                    desserts.add(item);
                }
            }
        }

        System.out.println("\nDishes:");
        printItemsInTable(dishes);
        System.out.println();

        System.out.println("Drinks:");
        printItemsInTable(drinks);
        System.out.println();

        System.out.println("Desserts:");
        printItemsInTable(desserts);
        System.out.println();
    }
    
    private static void viewPromotionItems() {
        ArrayList<MenuItem> promotionItems = menu.getPromotionItems();
        if (promotionItems.isEmpty()) {
            System.out.println("No discount item available");
            return;
        }
    
        System.out.println("\nPromotion Items:");
        printItemsInTable(promotionItems);
    }
    
    private static void printItemsInTable(ArrayList<MenuItem> items) {
        if (items.isEmpty()) {
            System.out.println("No items in this category.");
            return;
        }
    
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-60s | %-10s | %-10s | %-30s | %-15s | %-15s |\n", "Name", "Description", "Price", "Tax Price", "Promotion Details", "Discount Rate", "Discounted Price");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    
        for (MenuItem item : items) {
            if (item instanceof PromotionItem) {
                PromotionItem promoItem = (PromotionItem) item;
                System.out.printf("| %-20s | %-60s | $%-9.2f | $%-9.2f | %-30s | %-15.0f | $%-14.2f |\n",
                        promoItem.getName(),
                        promoItem.getDescription(),
                        promoItem.getPrice(),
                        promoItem.getTaxPrice(),
                        promoItem.getPromotionDetails(),
                        promoItem.getDiscountRate() * 100,
                        promoItem.getDiscountedPrice());
            } else {
                System.out.printf("| %-20s | %-60s | $%-9.2f | $%-9.2f | %-30s | %-15s | %-15s |\n",
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getTaxPrice(),
                        "N/A",
                        "N/A",
                        "N/A");
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    private static void printItems(ArrayList<MenuItem> items) {
        if (items.isEmpty()) {
            System.out.println("No items in this category.");
            return;
        }
        for (MenuItem item : items) {
            System.out.println("Name: " + item.getName());
            System.out.println("Description: " + item.getDescription());
            System.out.println("Original Price: $" + item.getPrice());
            if (item instanceof PromotionItem) {
                PromotionItem promoItem = (PromotionItem) item;
                double discountAmount = promoItem.getDiscountRate() * 100;
                double discountedPrice = promoItem.getDiscountedPrice();
                System.out.println("Promotion: " + promoItem.getPromotionDetails());
                System.out.println("Discount Amount: " + discountAmount + "%");
                System.out.println("Discounted Price: $" + discountedPrice);
            }
            System.out.println();
        }
    }

    private static void addItem() {
        System.out.println("1. Add Dish");
        System.out.println("2. Add Drink");
        System.out.println("3. Add Dessert");
        System.out.print("Select an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (option) {
            case 1:
                addDish();
                break;
            case 2:
                addDrink();
                break;
            case 3:
                addDessert();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void addDish() {
        System.out.print("Enter dish name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter size (S, M, L): ");
        char size = scanner.next().charAt(0);
        System.out.print("Is it spicy? (true/false): ");
        boolean spicy = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        Dishes dish = new Dishes(price, name, size, spicy);
        menu.addItem(dish);
        saveMenuToFile();
        System.out.println("Dish added successfully.");
    }

    private static void addDrink() {
        System.out.print("Enter drink name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter volume (ml): ");
        double volume = scanner.nextDouble();
        System.out.print("Enter sugar content (g): ");
        double sugar = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Drink drink = new Drink(price, name, volume, sugar);
        menu.addItem(drink);
        saveMenuToFile();
        System.out.println("Drink added successfully.");
    }

    private static void addDessert() {
        System.out.print("Enter dessert name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter size (g): ");
        double size = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Dessert dessert = new Dessert(price, name, size);
        menu.addItem(dessert);
        saveMenuToFile();
        System.out.println("Dessert added successfully.");
    }

    private static void removeItem() {
        System.out.print("Enter the name of the item to remove: ");
        String name = scanner.nextLine();

        MenuItem item = menu.findItem(name);
        if (item != null) {
            // Remove the promotion if it exists
            PromotionItem promoItem = null;
            for (MenuItem menuItem : menu.getItems()) {
                if (menuItem instanceof PromotionItem) {
                    PromotionItem tempPromoItem = (PromotionItem) menuItem;
                    if (tempPromoItem.getName().equals(name)) {
                        promoItem = tempPromoItem;
                        break;
                    }
                }
            }
            if (promoItem != null) {
                menu.removeItem(promoItem);
            }
            menu.removeItem(item);
            saveMenuToFile();
            System.out.println("Item and its promotion removed successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void addPromotion() {
        System.out.print("Enter the name of the item to add a promotion to: ");
        String name = scanner.nextLine();
    
        MenuItem item = menu.findItem(name);
        if (item != null) {
            if (item instanceof PromotionItem) {
                System.out.println("This item already has a promotion. Please remove the existing promotion before adding a new one.");
                return;
            }
    
            System.out.print("Enter promotion details: ");
            String promotionDetails = scanner.nextLine();
            System.out.print("Enter discount rate (e.g., 0.20 for 20%): ");
            double discountRate = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
    
            PromotionItem promoItem = new PromotionItem(item, promotionDetails, discountRate);
            menu.addItem(promoItem);
            saveMenuToFile();
            System.out.println("Promotion added successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void removePromotion() {
        ArrayList<MenuItem> promotionItems = menu.getPromotionItems();
        if (promotionItems.isEmpty()) {
            System.out.println("No promotions available to remove.");
            return;
        }
        
        System.out.print("Enter the name of the item to remove the promotion from: ");
        String name = scanner.nextLine();
    
        PromotionItem promoItem = null;
        for (MenuItem menuItem : menu.getItems()) {
            if (menuItem instanceof PromotionItem) {
                PromotionItem tempPromoItem = (PromotionItem) menuItem;
                if (tempPromoItem.getName().equals(name)) {
                    promoItem = tempPromoItem;
                    break;
                }
            }
        }
        if (promoItem != null) {
            menu.removeItem(promoItem);
            saveMenuToFile();
            System.out.println("Promotion removed successfully.");
        } else {
            System.out.println("Promotion not found.");
        }
    }
      
}
