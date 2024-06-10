package Harriz;

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

abstract class Recipe {
    private String name;
    private String description;
    private ArrayList<String> ingredients;
    private String instructions;

    public Recipe(String name, String description, String instructions) {
        this.name = name;
        this.description = description;
        this.ingredients = new ArrayList<>();
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }

    public abstract void printRecipe();
}

class DishRecipe extends Recipe {
    public DishRecipe(String name, String description, String instructions) {
        super(name, description, instructions);
    }

    @Override
    public void printRecipe() {
        System.out.println("Dish Recipe: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.println("Ingredients: " + String.join(", ", getIngredients()));
        System.out.println("Instructions: " + getInstructions());
    }
}

class DrinkRecipe extends Recipe {
    public DrinkRecipe(String name, String description, String instructions) {
        super(name, description, instructions);
    }

    @Override
    public void printRecipe() {
        System.out.println("Drink Recipe: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.println("Ingredients: " + String.join(", ", getIngredients()));
        System.out.println("Instructions: " + getInstructions());
    }
}

class DessertRecipe extends Recipe {
    public DessertRecipe(String name, String description, String instructions) {
        super(name, description, instructions);
    }

    @Override
    public void printRecipe() {
        System.out.println("Dessert Recipe: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.println("Ingredients: " + String.join(", ", getIngredients()));
        System.out.println("Instructions: " + getInstructions());
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
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();

    public static void main(String[] args) {
        // Pre-populate the menu with some items
        populateMenu();

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
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void populateMenu() {
        Dishes dish1 = new Dishes(12.99, "Nasi Goreng", 'M', true);
        Dishes dish2 = new Dishes(15.49, "Chicken Satay", 'L', false);
        menu.addItem(dish1);
        menu.addItem(dish2);

        Drink drink1 = new Drink(3.99, "Teh Tarik", 250, 10);
        Drink drink2 = new Drink(4.49, "Bandung", 300, 20);
        menu.addItem(drink1);
        menu.addItem(drink2);

        Dessert dessert1 = new Dessert(5.99, "Pisang Goreng", 150);
        Dessert dessert2 = new Dessert(6.49, "Cendol", 200);
        menu.addItem(dessert1);
        menu.addItem(dessert2);
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
                System.out.println("Invalid option.");
        }
    }

    private static void addDish() {
        System.out.print("Enter dish name: ");
        String name = scanner.nextLine();
        System.out.print("Enter dish price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter dish size (S, M, L): ");
        char size = scanner.nextLine().charAt(0);
        System.out.print("Is the dish spicy? (true/false): ");
        boolean spicy = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        Dishes dish = new Dishes(price, name, size, spicy);
        menu.addItem(dish);
        System.out.println("Dish added successfully.");
    }

    private static void addDrink() {
        System.out.print("Enter drink name: ");
        String name = scanner.nextLine();
        System.out.print("Enter drink price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter drink volume (ml): ");
        double volume = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter drink sugar content (g): ");
        double sugar = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Drink drink = new Drink(price, name, volume, sugar);
        menu.addItem(drink);
        System.out.println("Drink added successfully.");
    }

    private static void addDessert() {
        System.out.print("Enter dessert name: ");
        String name = scanner.nextLine();
        System.out.print("Enter dessert price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter dessert size (g): ");
        double size = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Dessert dessert = new Dessert(price, name, size);
        menu.addItem(dessert);
        System.out.println("Dessert added successfully.");
    }

    private static void removeItem() {
        System.out.print("Enter the name of the item to remove: ");
        String name = scanner.nextLine();

        MenuItem item = menu.findItem(name);
        if (item != null) {
            menu.removeItem(item);
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
            System.out.println("Promotion added successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }
}
