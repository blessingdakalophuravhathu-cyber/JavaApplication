/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaapplication4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private static int totalMessagesSent = 0;

    private static final List<Message> sentMessages =
            new ArrayList<>();

    private static final List<Message> storedMessages =
            new ArrayList<>();

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    private String status = "Pending";

    // Constructor
    public Message(int messageNumber, String recipient,
                   String messageText) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;

        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generate a random ten-digit message ID
    private String generateMessageID() {

        long number = 1000000000L
                + new Random().nextInt(900000000);

        return String.valueOf(number);
    }

    // Check that the message ID contains no more than ten characters
    public boolean checkMessageD() {

        return messageID != null
                && messageID.length() <= 10;
    }

    // Alternative method name for compatibility with earlier tests
    public boolean checkMessageID() {

        return checkMessageD();
    }

    // Validate the recipient's South African international number
    public String checkRecipientCell() {

        if (recipient != null
                && recipient.matches("\\+27[0-9]{9}")) {

            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does "
                + "not contain an international code. Please correct "
                + "the number and try again.";
    }

    // Validate the message length
    public String checkMessageLength() {

        if (messageText == null) {

            return "Please enter a message of less than 250 characters.";
        }

        if (messageText.length() > 250) {

            return "Message exceeds 250 characters by "
                    + (messageText.length() - 250)
                    + "; please reduce the size.";
        }

        return "Message ready to send.";
    }

    // Create the message hash
    public String createMessageHash() {

        String[] words;

        if (messageText == null || messageText.trim().isEmpty()) {
            words = new String[0];
        } else {
            words = messageText.trim().split("\\s+");
        }

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0
                ? words[words.length - 1] : "";

        firstWord = firstWord.replaceAll("[^a-zA-Z0-9]", "");
        lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");

        String firstTwoDigits = messageID.substring(0, 2);

        return (firstTwoDigits + ":"
                + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    // Allow the user to send, disregard or store a message
    public String sentMessage(Scanner scanner) {

        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        System.out.print("Choose an option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {

            case "1":

                if (!"Message ready to send.".equals(
                        checkMessageLength())) {

                    return checkMessageLength();
                }

                if (!"Cell phone number successfully captured.".equals(
                        checkRecipientCell())) {

                    return checkRecipientCell();
                }

                status = "Sent";

                sentMessages.add(this);
                totalMessagesSent++;

                return "Message successfully sent.";

            case "2":

                status = "Disregarded";

                return "Press O to delete the message.";

            case "3":

                status = "Stored";

                storedMessages.add(this);
                storeMessage();

                return "Message successfully stored.";

            default:

                return "Invalid option. Please choose 1, 2, or 3.";
        }
    }

    // Store messages in a JSON file
    public void storeMessage() {

        try (FileWriter writer = new FileWriter("messages.json")) {

            writer.write("[\n");

            for (int i = 0; i < storedMessages.size(); i++) {

                Message message = storedMessages.get(i);

                writer.write("  {\n");

                writer.write("    \"messageID\": \""
                        + escapeJson(message.messageID) + "\",\n");

                writer.write("    \"messageHash\": \""
                        + escapeJson(message.messageHash) + "\",\n");

                writer.write("    \"recipient\": \""
                        + escapeJson(message.recipient) + "\",\n");

                writer.write("    \"message\": \""
                        + escapeJson(message.messageText) + "\",\n");

                writer.write("    \"status\": \""
                        + escapeJson(message.status) + "\"\n");

                writer.write("  }");

                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]\n");

        } catch (IOException e) {

            System.out.println("Could not store message: "
                    + e.getMessage());
        }
    }

    // Escape special characters so the JSON remains valid
    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Display all messages sent during this program run
    public String printMessages() {

        StringBuilder result = new StringBuilder();

        for (Message message : sentMessages) {

            result.append("Message ID: ")
                    .append(message.messageID)
                    .append("\n");

            result.append("Message Hash: ")
                    .append(message.messageHash)
                    .append("\n");

            result.append("Recipient: ")
                    .append(message.recipient)
                    .append("\n");

            result.append("Message: ")
                    .append(message.messageText)
                    .append("\n\n");
        }

        if (result.length() == 0) {
            return "No messages have been sent.";
        }

        return result.toString();
    }

    // Return the total number of successfully sent messages
    public int returnTotalMessages() {

        return totalMessagesSent;
    }

    // Getters
    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getStatus() {
        return status;
    }
}
