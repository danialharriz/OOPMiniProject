import java.util.ArrayList;

public class MenuManagementSystem {
    private Menu menu;

    public MenuManagementSystem() {
        menu = new Menu();
    }

    public void addItem(MenuItem item) {
        menu.addItem(item);
        System.out.println("Added: " + item.getName());
    }

    public void removeItem(MenuItem item) {
        menu.removeItem(item);
        System.out.println("Removed: " + item.getName());
    }

    public MenuItem findItem(String name) {
        MenuItem item = menu.findItem(name);
        if (item != null) {
            System.out.println("Found: " + item.getName());
        } else {
            System.out.println("Item not found: " + name);
        }
        return item;
    }

    public void displayMenu() {
        ArrayList<MenuItem> items = menu.getItems();
        System.out.println("Menu:");
        for (MenuItem item : items) {
            System.out.println("- " + item.getName() + ": " + item.getPrice() + " - " + item.getDescription());
        }
    }

    public static void main(String[] args) {
        MenuManagementSystem system = new MenuManagementSystem();

        // Create some items
        Dishes dish1 = new Dishes(10.5, 'L', "Pasta", true);
        Drink drink1 = new Drink(2.5, 500, 10, "Cola");
        Dessert dessert1 = new Dessert(5.0, 150, "Cheesecake");

        // Add items to the menu
        system.addItem(dish1);
        system.addItem(drink1);
        system.addItem(dessert1);

        // Display menu
        system.displayMenu();

        // Find an item
        system.findItem("Pasta");

        // Remove an item
        system.removeItem(dessert1);

        // Display menu again
        system.displayMenu();

        // Create and add a promotion item
        PromotionItem promoDish = new PromotionItem(dish1, "20% off", 0.2);
        system.addItem(promoDish);
        
        // Display promotion item details
        System.out.println("Promotion Details: " + promoDish.getPromotionDetails());
        System.out.println("Discounted Price: " + promoDish.getDiscountedPrice(dish1.getPrice()));
        
        // Display final menu
        system.displayMenu();
    }
}
