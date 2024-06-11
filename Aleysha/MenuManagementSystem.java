import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

interface MenuItem {
    String getName();
    double getPrice();
    String getDescription();
}

class PromotionItem implements MenuItem {
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

    @Override
    public String getDescription() {
        return item.getDescription();
    }

    public String getPromotionDetails() {
        return promotionDetails;
    }

    public double getDiscountedPrice(double originalPrice) {
        return originalPrice - (originalPrice * discountRate);
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
}

class Dishes implements MenuItem {
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

    public String getDishType() {
        return dishType;
    }

    public char getSize() {
        return size;
    }

    public boolean isSpicy() {
        return spicy;
    }
}

class Drink implements MenuItem {
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
}

class Dessert implements MenuItem {
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
}

public class MenuManagementSystem {
    private static final String MENU_FILE = "Menu.txt";
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();

    public static void main(String[] args) {
        // Load the menu from a file
        loadMenuFromFile();

        // Main menu loop
        while (true) {
            System.out.println("1. View Menu");
            System.out.println("2. Add Item");
            System.out.println("3. Remove Item");
            System.out.println("4. Add Promotion");
            System.out.println("5. Exit");
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
                    addPromotion();
                    break;
                case 5:
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
                        break;
                    case "Drink":
                        double drinkPrice = Double.parseDouble(parts[2]);
                        double volume = Double.parseDouble(parts[3]);
                        double sugar = Double.parseDouble(parts[4]);
                        menu.addItem(new Drink(drinkPrice, parts[1], volume, sugar));
                        break;
                    case "Dessert":
                        double dessertPrice = Double.parseDouble(parts[2]);
                        double dessertSize = Double.parseDouble(parts[3]);
                        menu.addItem(new Dessert(dessertPrice, parts[1], dessertSize));
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading menu: " + e.getMessage());
        }
    }

    private static void saveMenuToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MENU_FILE))) {
            for (MenuItem item : menu.getItems()) {
                if (item instanceof Dishes) {
                    Dishes dish = (Dishes) item;
                    writer.write(String.format("Dishes,%s,%.2f,%c,%b\n", dish.getName(), dish.getPrice(), dish.getSize(), dish.isSpicy()));
                } else if (item instanceof Drink) {
                    Drink drink = (Drink) item;
                    writer.write(String.format("Drink,%s,%.2f,%.2f,%.2f\n", drink.getName(), drink.getPrice(), drink.getVolume(), drink.getSugar()));
                } else if (item instanceof Dessert) {
                    Dessert dessert = (Dessert) item;
                    writer.write(String.format("Dessert,%s,%.2f,%.2f\n", dessert.getName(), dessert.getPrice(), dessert.getSize()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving menu: " + e.getMessage());
        }
    }

    private static void viewMenu() {
        System.out.println("Menu Items:");
        for (MenuItem item : menu.getItems()) {
            System.out.println("Name: " + item.getName());
            System.out.println("Description: " + item.getDescription());
            System.out.println("Price: $" + item.getPrice());
            if (item instanceof PromotionItem) {
                PromotionItem promoItem = (PromotionItem) item;
                System.out.println("Promotion: " + promoItem.getPromotionDetails());
                System.out.println("Discounted Price: $" + promoItem.getDiscountedPrice(promoItem.getPrice()));
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
            menu.removeItem(item);
            saveMenuToFile();
            System.out.println("Item removed successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void addPromotion() {
        System.out.print("Enter the name of the item to add a promotion to: ");
        String name = scanner.nextLine();

        MenuItem item = menu.findItem(name);
        if (item != null) {
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
}