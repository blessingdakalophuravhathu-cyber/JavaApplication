*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaapplication4;

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

        while (running) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (messagesEntered >= messageLimit) {
                        System.out.println("You have reached your message limit.");
                        break;
                    }

                    while (messagesEntered < messageLimit) {
                        System.out.println("\nMessage "
                                + (messagesEntered + 1) + " of "
                                + messageLimit);

                        System.out.print("Recipient (+27...): ");
                        String recipient = scanner.nextLine().trim();

                        System.out.print("Message: ");
                        String text = scanner.nextLine();

                        Message message = new Message(
                                messageNumber, recipient, text);

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

                    Message summary = new Message(
                            0, "+27718693002", "Summary message");

                    System.out.println("\nTotal messages sent: "
                            + summary.returnTotalMessages());

                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    running = false;
                    System.out.println("Thank you for using QuickChat.");
                    break;

                default:
                    System.out.println("Invalid option. Choose 1, 2, or 3.");
            }
        }

        scanner.close();
    }
}
