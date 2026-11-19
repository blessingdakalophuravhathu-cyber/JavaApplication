package com.mycompany.javaapplication4;

import java.util.ArrayList;
import java.util.Scanner;

public class QuickChat {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("Welcome to QuickChat.");
        System.out.println("\n--- Registration ---");

        System.out.print("First name: ");
        login.firstName = scanner.nextLine();

        System.out.print("Last name: ");
        login.lastName = scanner.nextLine();

        System.out.print("Create username: ");
        login.username = scanner.nextLine();

        System.out.print("Create password: ");
        login.password = scanner.nextLine();

        System.out.print("Cellphone number (+27...): ");
        login.cellPhoneNumber = scanner.nextLine();

        String registration = login.registerUser();
        System.out.println(registration);

        if (!registration.equals("User successfully registered.")) {
            System.out.println("Registration failed. Restart and try again.");
            scanner.close();
            return;
        }

        System.out.println("\n--- Login ---");

        System.out.print("Username: ");
        login.loginUsername = scanner.nextLine();

        System.out.print("Password: ");
        login.loginPassword = scanner.nextLine();

        if (!login.loginUser()) {
            System.out.println(login.returnLoginStatus());
            scanner.close();
            return;
        }

        System.out.println(login.returnLoginStatus());

        // Load messages saved in JSON.
        Message.loadStoredMessages();

        int messageLimit;

        while (true) {

            System.out.print("How many messages may you enter? ");

            try {
                messageLimit = Integer.parseInt(scanner.nextLine().trim());

                if (messageLimit > 0) {
                    break;
                }

                System.out.println("Enter a number greater than zero.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }

        int messagesEntered = 0;
        int messageNumber = 0;
        boolean running = true;

        String sender = login.firstName + " " + login.lastName;

        while (running) {

            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":

                    if (messagesEntered >= messageLimit) {
                        System.out.println("You have reached your message limit.");
                        break;
                    }

                    // Required message-entry FOR loop.
                    int remainingMessages = messageLimit - messagesEntered;

                    for (int i = 0; i < remainingMessages; i++) {

                        System.out.println("\nMessage "
                                + (messagesEntered + 1) + " of "
                                + messageLimit);

                        System.out.print("Recipient (+27...): ");
                        String recipient = scanner.nextLine().trim();

                        System.out.print("Message: ");
                        String text = scanner.nextLine();

                        Message message = new Message(
                                messageNumber, sender, recipient, text);

                        System.out.println("Message ID generated: "
                                + message.getMessageID());

                        System.out.println("Message Hash: "
                                + message.getMessageHash());

                        System.out.println(message.checkRecipientCell());
                        System.out.println(message.checkMessageLength());

                        System.out.println(message.sentMessage(scanner));

                        messagesEntered++;
                        messageNumber++;

                        if (messagesEntered >= messageLimit) {
                            System.out.println("Message limit reached.");
                            break;
                        }

                        System.out.print("Enter another message? (yes/no): ");

                        if (!scanner.nextLine().trim()
                                .equalsIgnoreCase("yes")) {
                            break;
                        }
                    }

                    // Display complete details of all sent messages.
                    System.out.println("\n--- Sent Message Details ---");
                    System.out.println(Message.printMessages());

                    System.out.println("Total messages sent: "
                            + Message.returnTotalMessages());

                    break;

                case "2":

                    System.out.println("\n--- Recently Sent Messages ---");
                    System.out.println(Message.printMessages());

                    break;

                case "3":

                    running = false;
                    System.out.println("Thank you for using QuickChat.");
                    break;

                case "4":

                    storedMessagesMenu(scanner);
                    break;

                default:

                    System.out.println("Invalid option. Choose 1, 2, 3, or 4.");
            }
        }

        scanner.close();
    }

    // Stored Messages menu
    private static void storedMessagesMenu(Scanner scanner) {

        boolean inMenu = true;

        while (inMenu) {

            System.out.println("\n--- Stored Messages ---");
            System.out.println("1. Display sender and recipient of stored messages");
            System.out.println("2. Display the longest stored message");
            System.out.println("3. Search for a message ID");
            System.out.println("4. Search messages by recipient");
            System.out.println("5. Delete a message using its hash");
            System.out.println("6. Display message report");
            System.out.println("7. Search messages by sender");
            System.out.println("8. Return to main menu");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":

                    System.out.println(
                            Message.displayStoredSendersAndRecipients());
                    break;

                case "2":

                    Message longest = Message.getLongestStoredMessage();

                    if (longest == null) {
                        System.out.println("No stored messages.");
                    } else {
                        System.out.println("Longest stored message:");
                        System.out.println(longest.getMessageText());
                    }
                    break;

                case "3":

                    System.out.print("Enter message ID: ");
                    String id = scanner.nextLine().trim();

                    Message found = Message.searchByMessageID(id);

                    if (found == null) {
                        System.out.println("Message ID not found.");
                    } else {
                        System.out.println("Recipient: "
                                + found.getRecipient());

                        System.out.println("Message: "
                                + found.getMessageText());
                    }
                    break;

                case "4":

                    System.out.print("Enter recipient number: ");
                    String recipient = scanner.nextLine().trim();

                    ArrayList<Message> results =
                            Message.searchByRecipient(recipient);

                    if (results.isEmpty()) {
                        System.out.println(
                                "No messages found for this recipient.");
                    } else {

                        for (Message m : results) {

                            System.out.println("Message: "
                                    + m.getMessageText());

                            System.out.println("Status: "
                                    + m.getStatus());

                            System.out.println();
                        }
                    }
                    break;

                case "5":

                    System.out.print("Enter message hash: ");
                    String hash = scanner.nextLine().trim();

                    if (Message.deleteStoredMessageByHash(hash)) {
                        System.out.println(
                                "Message successfully deleted.");
                    } else {
                        System.out.println("Message hash not found.");
                    }
                    break;

                case "6":

                    System.out.println(Message.generateReport());
                    break;

                case "7":

                    System.out.print("Enter sender name: ");
                    String sender = scanner.nextLine().trim();

                    ArrayList<Message> senderResults =
                            Message.searchBySender(sender);

                    if (senderResults.isEmpty()) {
                        System.out.println("No messages found for this sender.");
                    } else {

                        for (Message m : senderResults) {

                            System.out.println("Message: "
                                    + m.getMessageText());

                            System.out.println("Recipient: "
                                    + m.getRecipient());

                            System.out.println("Status: "
                                    + m.getStatus());

                            System.out.println();
                        }
                    }
                    break;

                case "8":

                    inMenu = false;
                    break;

                default:

                    System.out.println("Invalid option. Choose 1 to 8.");
            }
        }
    }
}
