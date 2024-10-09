/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package store.with.dialogue;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class StoreWithDialogue {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean program = true;
        RUNNING:
        do {
            boolean nextCustomer = true;

            // Overall sales data for end of day
            String[] items = {"Pencils@PHP 5.00", "Ballpoint Pens@PHP 10.00", "Notebooks@PHP 50.00", "Sharpeners@PHP 15.00", "Erasers@PHP 5.00"};
            double[] prices = {5.00, 10.00, 50.00, 15.00, 5.00};
            double allCost = 0.0;
            int totalTransactions = 0;
            int[] totalItemQuantities = new int[items.length];
            double[] totalItemSales = new double[items.length]; // Track total sales per item
            NEXTCUST:
            do {
                int Enter = JOptionPane.showConfirmDialog(null, "Enter?", "Welcome to Karl's Bookstore!", JOptionPane.YES_NO_OPTION);
                switch (Enter) {
                    case -1 -> {
                        boolean exitProgram = closeButton();
                        if (exitProgram) {
                            break RUNNING;  // Exit the outer loop if the user chooses to close
                        } else {
                            program = true;  // Continue with the next customer
                        }
                    }
                    case 0 -> {
                        ArrayList<String> cartItems = new ArrayList<>();
                        ArrayList<Integer> cartQuantities = new ArrayList<>();
                        ArrayList<Double> cartCosts = new ArrayList<>();
                        double totalCost = 0.0;
                        boolean Browsing = true;
                        BROWSING:
                        do {
                            // Use showOptionDialog to present item choices as buttons
                            int choice = JOptionPane.showOptionDialog(
                                    null,
                                    "Choose an item to buy or remove from your cart:",
                                    "Item Selection",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    new String[]{"Add Item", "Remove Item", "Exit"},
                                    null);

                            switch (choice) {
                                case -1 -> {
                                    boolean exitProgram = closeButton();
                                    if (exitProgram) {
                                        break RUNNING;  // Exit the outer loop if the user chooses to close
                                    } else {
                                        program = true;  // Continue with the program
                                    }
                                }
                                case 0 -> {
                                    // Add item
                                    int itemChoice = JOptionPane.showOptionDialog(
                                            null,
                                            "Choose an item to add:",
                                            "Item Selection",
                                            JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.PLAIN_MESSAGE,
                                            null,
                                            items, null);
                                    if (itemChoice == -1) {
                                        boolean exitProgram = closeButton();
                                        if (exitProgram) {
                                            break RUNNING;  // Exit the outer loop if the user chooses to close
                                        } else {
                                            program = true;  // Continue with the next customer
                                        }
                                    }
                                    if (itemChoice >= 0 && itemChoice <= 4) {  // Valid item selected
                                        String itemName = items[itemChoice];
                                        double itemCost = prices[itemChoice];

                                        String quantityStr = JOptionPane.showInputDialog("You selected " + itemName + ". Enter the quantity:");
                                        if (quantityStr == null) {
                                            // User clicked "Cancel" or closed the dialog
                                            JOptionPane.showMessageDialog(null, "You canceled the quantity input. No item was added to the cart.");
                                            break;
                                        }
                                        int quantity = Integer.parseInt(quantityStr);

                                        if (quantity > 0) {
                                            boolean itemExistsInCart = false;

                                            // Check if the item is already in the cart
                                            for (int i = 0; i < cartItems.size(); i++) {
                                                if (cartItems.get(i).equals(itemName)) {
                                                    // Update the existing item in the cart
                                                    cartQuantities.set(i, cartQuantities.get(i) + quantity);
                                                    cartCosts.set(i, cartCosts.get(i) + itemCost * quantity);
                                                    itemExistsInCart = true;
                                                    break;
                                                }
                                            }

                                            if (!itemExistsInCart) {
                                                // If item not found, add it as a new item
                                                cartItems.add(itemName);
                                                cartQuantities.add(quantity);
                                                cartCosts.add(itemCost * quantity);
                                            }

                                            totalCost += itemCost * quantity;

                                            // Build a message for added items and updated cart
                                            StringBuilder addedItemsMessage = new StringBuilder(String.format("Added %d %s to your cart.\nYour cart contains:\n", quantity, itemName));
                                            for (int i = 0; i < cartItems.size(); i++) {
                                                addedItemsMessage.append(cartItems.get(i))
                                                        .append(" (Qty: ")
                                                        .append(cartQuantities.get(i))
                                                        .append(")\n");
                                            }
                                            addedItemsMessage.append(String.format("Current total cost: PHP %.2f", totalCost));

                                            // Show the message
                                            JOptionPane.showMessageDialog(null, addedItemsMessage.toString());

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter a positive number.");
                                        }
                                    }
                                }
                                case 1 -> {
                                    // Remove item
                                    if (cartItems.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Your cart is empty. There is nothing to remove.");
                                    } else {
                                        String[] cartArray = cartItems.toArray(new String[0]);
                                        int removeChoice = JOptionPane.showOptionDialog(
                                                null,
                                                "Select an item to remove from your cart:",
                                                "Remove Item",
                                                JOptionPane.DEFAULT_OPTION,
                                                JOptionPane.PLAIN_MESSAGE,
                                                null,
                                                cartArray, null);

                                        if (removeChoice >= 0) {
                                            String removedItem = cartItems.get(removeChoice);
                                            double removedCost = cartCosts.get(removeChoice);
                                            int removedQuantity = cartQuantities.get(removeChoice);

                                            totalCost -= removedCost;
                                            cartItems.remove(removeChoice);
                                            cartCosts.remove(removeChoice);
                                            cartQuantities.remove(removeChoice);

                                            JOptionPane.showMessageDialog(null, String.format("Removed %d %s from the cart. Current total cost: PHP %.2f", removedQuantity, removedItem, totalCost));

                                        }
                                    }
                                }
                                case 2 -> {
                                    // "Exit" was selected
                                    Browsing = false;
                                    JOptionPane.showMessageDialog(null, "Exiting the bookstore.");
                                }
                                default -> {
                                    Browsing = false;
                                    JOptionPane.showMessageDialog(null, "Transaction canceled.");
                                }
                            }

                            if (Browsing && !cartItems.isEmpty()) {
                                int continueShopping = JOptionPane.showConfirmDialog(null, "Do you want to continue browsing?", "Continue", JOptionPane.YES_NO_OPTION);
                                if (continueShopping == JOptionPane.YES_OPTION) {
                                    Browsing = false;
                                } else if (continueShopping == JOptionPane.NO_OPTION) {
                                    Browsing = false;
                                } else if (continueShopping == JOptionPane.CLOSED_OPTION) {
                                    // Call the closeButton method
                                    boolean exitProgram = closeButton();
                                    if (exitProgram) {
                                        break RUNNING;  // Exit the outer loop if the user chooses to close
                                    } else {
                                        nextCustomer = true;  // Continue with the next customer
                                    }
                                }

                            }

                        } while (Browsing);

                        if (!cartItems.isEmpty()) {
                            double amountPaid = 0.0;
                            int failedAttempts = 0;
                            double change = 0.0;

                            while (amountPaid < totalCost && failedAttempts < 3) {
                                String additionalPaymentStr = JOptionPane.showInputDialog(String.format("Total cost: PHP %.2f\nEnter the amount of money you have: PHP", totalCost));
                                if (additionalPaymentStr == null) {
                                    JOptionPane.showMessageDialog(null, "Transaction canceled.");
                                    break;
                                }

                                double additionalPayment = Double.parseDouble(additionalPaymentStr);
                                amountPaid += additionalPayment;

                                if (amountPaid >= totalCost) {
                                    change = amountPaid - totalCost;
                                    JOptionPane.showMessageDialog(null, String.format("Payment successful! Your change is: PHP %.2f", change));
                                } else {
                                    double amountOwed = totalCost - amountPaid;
                                    failedAttempts++;
                                    String warningMessage = String.format("Failed attempts: %d/3 \nNot enough money. You still owe: PHP %.2f.", failedAttempts, amountOwed);
                                    int addMore = JOptionPane.showConfirmDialog(null, warningMessage + "\nDo you want to add more money?", "Add More Money", JOptionPane.YES_NO_OPTION);

                                    if (addMore == JOptionPane.NO_OPTION || failedAttempts == 3) {
                                        JOptionPane.showMessageDialog(null, "Transaction canceled. Exiting the bookstore.");
                                        break;
                                    }
                                }
                            }

                            if (amountPaid >= totalCost) {
                                totalTransactions++;
                                allCost += totalCost;

                                StringBuilder finalReceipt = new StringBuilder("Final receipt:\n");
                                for (int i = 0; i < cartItems.size(); i++) {
                                    finalReceipt.append(cartItems.get(i))
                                            .append(" - Qty: ")
                                            .append(cartQuantities.get(i))
                                            .append(" - Cost: PHP ")
                                            .append(String.format("%.2f", cartCosts.get(i)))
                                            .append("\n");

                                    for (int j = 0; j < items.length; j++) {
                                        if (items[j].equals(cartItems.get(i))) {
                                            totalItemQuantities[j] += cartQuantities.get(i);
                                            totalItemSales[j] += cartCosts.get(i);
                                        }
                                    }
                                }
                                finalReceipt.append("Total cost: PHP ").append(String.format("%.2f", totalCost))
                                        .append("\nAmount Paid: PHP ").append(String.format("%.2f", amountPaid))
                                        .append("\nChange: PHP ").append(String.format("%.2f", change));

                                JOptionPane.showMessageDialog(null, finalReceipt.toString());
                            }
                        }

                    }
                    case 1 -> {
                        JOptionPane.showMessageDialog(null, "Thank you for stopping by, have a good day!");
                    }

                }

                int nextCus = nextcust();
                switch (nextCus) {
                    case 0 -> {
                        nextCustomer = true;
                    }
                    case 1 -> {
                        nextCustomer = false;
                    }
                    default -> {
                        // Call the closeButton method
                        boolean exitProgram = closeButton();
                        if (exitProgram) {
                            break RUNNING;  // Exit the outer loop if the user chooses to close
                        } else {
                            nextCustomer = true;  // Continue with the next customer
                        }
                    }
                }
            } while (nextCustomer);
            // Final end of day summary after processing all customers
            StringBuilder endOfDaySummary = new StringBuilder(String.format("End of day summary:\nTotal transactions: %d\nTotal sales: PHP %.2f\n\nSales Breakdown:\n", totalTransactions, allCost));
            for (int i = 0;
                    i < items.length;
                    i++) {
                if (totalItemQuantities[i] > 0) {
                    endOfDaySummary.append(items[i])
                            .append(" - Qty Sold: ")
                            .append(totalItemQuantities[i])
                            .append(" - Total Sales: PHP ")
                            .append(String.format("%.2f", totalItemSales[i]))
                            .append("\n");
                }
            }

            JOptionPane.showMessageDialog(
                    null, endOfDaySummary.toString());
            boolean exitProgram = closeButton();
            if (exitProgram) {
                break RUNNING;  // Exit the outer loop if the user chooses to close
            } else {
                program = true;  // Continue with the next customer
            }

        } while (program);
        {
            JOptionPane.showMessageDialog(null, "Thank you for using Karl's program.");
        }
    }

    public static boolean closeButton() {
        int exitProg = JOptionPane.showConfirmDialog(null, "Do you want to exit the program?", "Close Button", JOptionPane.YES_NO_OPTION);

        switch (exitProg) {
            case -1: // User closed the dialog (clicked the "X" button)
                JOptionPane.showMessageDialog(null, "You closed the dialog. Program will not exit.");
                return false; // Continue running the program

            case 0: // User clicked "Yes"
                JOptionPane.showMessageDialog(null, "You selected 'Yes'. Exiting the program.");
                return true; // Exit the program

            case 1: // User clicked "No"
                JOptionPane.showMessageDialog(null, "You selected 'No'. Continuing the program.");
                return false; // Continue running the program

            default:
                return false; // Fallback to continue if something unexpected happens
        }
    }

    public static int unfinished() {
        int unfin = JOptionPane.showOptionDialog(
                null,
                "What would you like to do?",
                "You still have items in your cart",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"Check out", "Return Items and exit store"},
                null);
        switch (unfin) {
            case -1: // User closed the dialog (clicked the "X" button)
                JOptionPane.showMessageDialog(null, "You closed the dialog. Program will not exit.");
                return -1; // Continue running the program

            case 0: // User clicked "Yes"
                JOptionPane.showMessageDialog(null, "Proceeding to check out");
                return 0; // Exit the program

            case 1: // User clicked "No"
                JOptionPane.showMessageDialog(null, "Returning items");
                return 1; // Continue running the program

            default:
                return 0; // Fallback to continue if something unexpected happens
        }
    }

    public static int nextcust() {
                        int nextCustomerOption = JOptionPane.showConfirmDialog(null, "Next customer?", "", JOptionPane.YES_NO_OPTION);
        switch (nextCustomerOption) {
            case 0 -> {
                return 0;
            }
            case 1 -> {
                return 1;
            }
            case -1 -> {
                return -1;
            }
            default -> {
                return 0;}
        }
    }
}
