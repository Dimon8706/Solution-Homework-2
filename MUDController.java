//Solution_Homework2
import java.util.*;

public class MUDController {

    private final Player player;
    private boolean running;

    public MUDController(Player player) {
        this.player = player;
        this.running = true;
    }

    public void runGameLoop() {
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();
            handleInput(input);
        }
        scanner.close();
    }

    public void handleInput(String input) {
        if (input.startsWith("move")) {
            String direction = input.substring(5).trim();
            move(direction);
        } else if (input.equals("look")) {
            lookAround();
        } else if (input.startsWith("pick up")) {
            String itemName = input.substring(8).trim();
            pickUp(itemName);
        } else if (input.equals("inventory")) {
            checkInventory();
        } else if (input.equals("help")) {
            showHelp();
        } else if (input.equals("quit") || input.equals("exit")) {
            System.out.println("Exiting the game...");
            running = false;
        } else {
            System.out.println("Unknown command.");
        }
    }

    private void lookAround() {
        Room currentRoom = player.getCurrentRoom();
        System.out.println("Room: " + currentRoom.getName());
        System.out.println(currentRoom.getDescription());
        if (currentRoom.getItem() != null) {
            System.out.println("Items here: " + currentRoom.getItem().getName());
        }
        if (currentRoom.getNPC() != null) {
            System.out.println("NPC here: " + currentRoom.getNPC().describe());
        }
    }

    private void move(String direction) {
        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            player.setCurrentRoom(nextRoom);
            System.out.println("You moved " + direction + " to " + nextRoom.getName());
        } else {
            System.out.println("You can't go that way!");
        }
    }

    private void pickUp(String itemName) {
        Room currentRoom = player.getCurrentRoom();
        Item item = currentRoom.getItem();
        if (item != null && item.getName().equalsIgnoreCase(itemName)) {
            player.addItemToInventory(item);
            currentRoom.setItem(null);
            System.out.println("You picked up the " + itemName);
        } else {
            System.out.println("No item named '" + itemName + "' here!");
        }
    }

    private void checkInventory() {
        if (player.getInventory().isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("You are carrying:");
            player.getInventory().forEach(item -> System.out.println(item.getName() + ": " + item.getDescription()));
        }
    }

    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("look - Look around the current room.");
        System.out.println("move <direction> - Move in the specified direction (north, south, east, west).");
        System.out.println("pick up <itemName> - Pick up an item from the room.");
        System.out.println("inventory - Show your inventory.");
        System.out.println("help - Show this help message.");
        System.out.println("quit/exit - Exit the game.");
    }
}