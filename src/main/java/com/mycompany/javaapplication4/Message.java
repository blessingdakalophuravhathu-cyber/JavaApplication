package com.mycompany.javaapplication4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private static int totalMessagesSent = 0;

    // Five required arrays
    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static final ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static final ArrayList<Message> storedMessages = new ArrayList<>();
    private static final ArrayList<String> messageHashes = new ArrayList<>();
    private static final ArrayList<String> messageIDs = new ArrayList<>();

    private static final String JSON_FILE = "messages.json";

    private static final Gson gson =
            new GsonBuilder().setPrettyPrinting().create();

    // Message details
    private String sender;
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status = "Pending";

    // Original constructor
    public Message(int messageNumber, String recipient,
                   String messageText) {
        this(messageNumber, "Developer", recipient, messageText);
    }

    // Constructor with sender
    public Message(int messageNumber, String sender,
                   String recipient, String messageText) {

        this.messageNumber = messageNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;

        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();

        addToIndexes();
    }

    // Constructor for Gson
    private Message() {
    }

    // Generate a ten-digit message ID
    private String generateMessageID() {

        long number = 1000000000L
                + new Random().nextInt(900000000);

        return String.valueOf(number);
    }

    // Check message ID
    public boolean checkMessageD() {
        return messageID != null && messageID.length() == 10;
    }

    public boolean checkMessageID() {
        return checkMessageD();
    }

    // Validate recipient number
    public String checkRecipientCell() {

        if (recipient != null
                && recipient.matches("\\+27[0-9]{9}")) {

            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does "
                + "not contain an international code. Please correct "
                + "the number and try again.";
    }

    // Check message length
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

    // Create message hash
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

    // Add IDs and hashes to their arrays
    private void addToIndexes() {

        if (messageID != null && !messageIDs.contains(messageID)) {
            messageIDs.add(messageID);
        }

        if (messageHash != null && !messageHashes.contains(messageHash)) {
            messageHashes.add(messageHash);
        }
    }

    // Send, disregard or store message
    public String sentMessage(Scanner scanner) {

        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        System.out.print("Choose an option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {

            case "1":

                if (!"Message ready to send.".equals(checkMessageLength())) {
                    return checkMessageLength();
                }

                if (!"Cell phone number successfully captured."
                        .equals(checkRecipientCell())) {
                    return checkRecipientCell();
                }

                status = "Sent";

                if (!sentMessages.contains(this)) {
                    sentMessages.add(this);
                    totalMessagesSent++;
                }

                return "Message successfully sent.";

            case "2":

                status = "Disregarded";

                if (!disregardedMessages.contains(this)) {
                    disregardedMessages.add(this);
                }

                return "Press O to delete the message.";

            case "3":

                status = "Stored";

                if (!storedMessages.contains(this)) {
                    storedMessages.add(this);
                }

                storeAllStoredMessages();

                return "Message successfully stored.";

            default:

                return "Invalid option. Please choose 1, 2, or 3.";
        }
    }

    // Save stored messages to JSON
    public void storeMessage() {
        storeAllStoredMessages();
    }

    private static void storeAllStoredMessages() {

        try (FileWriter writer = new FileWriter(JSON_FILE)) {

            gson.toJson(storedMessages, writer);

        } catch (IOException e) {

            System.out.println("Could not store messages: "
                    + e.getMessage());
        }
    }

    // Load messages from JSON into the stored messages array
    public static void loadStoredMessages() {

        try (Reader reader = new FileReader(JSON_FILE)) {

            List<Message> loadedMessages = gson.fromJson(
                    reader,
                    new TypeToken<ArrayList<Message>>() {}.getType()
            );

            storedMessages.clear();

            if (loadedMessages != null) {
                storedMessages.addAll(loadedMessages);
            }

            rebuildIndexes();

        } catch (IOException e) {

            System.out.println("No stored messages file found yet.");

        } catch (com.google.gson.JsonParseException e) {

            System.out.println("Could not read messages.json: "
                    + e.getMessage());
        }
    }

    // Display sent messages
    public static String printMessages() {

        StringBuilder result = new StringBuilder();

        for (Message message : sentMessages) {

            result.append("Message ID: ")
                    .append(message.messageID).append("\n");

            result.append("Message Hash: ")
                    .append(message.messageHash).append("\n");

            result.append("Recipient: ")
                    .append(message.recipient).append("\n");

            result.append("Message: ")
                    .append(message.messageText).append("\n\n");
        }

        return result.length() == 0
                ? "No messages have been sent."
                : result.toString();
    }

    // Display stored senders and recipients
    public static String displayStoredSendersAndRecipients() {

        StringBuilder result = new StringBuilder();

        for (Message message : storedMessages) {

            result.append("Sender: ")
                    .append(message.sender).append("\n");

            result.append("Recipient: ")
                    .append(message.recipient).append("\n\n");
        }

        return result.length() == 0
                ? "No stored messages."
                : result.toString();
    }

    // Find longest stored message
    public static Message getLongestStoredMessage() {

        Message longest = null;

        for (Message message : storedMessages) {

            if (message.messageText != null
                    && (longest == null
                    || message.messageText.length()
                    > longest.messageText.length())) {

                longest = message;
            }
        }

        return longest;
    }

    // Search message ID across all arrays
    public static Message searchByMessageID(String id) {

        if (id == null) {
            return null;
        }

        for (Message message : sentMessages) {
            if (id.equalsIgnoreCase(message.messageID)) {
                return message;
            }
        }

        for (Message message : storedMessages) {
            if (id.equalsIgnoreCase(message.messageID)) {
                return message;
            }
        }

        for (Message message : disregardedMessages) {
            if (id.equalsIgnoreCase(message.messageID)) {
                return message;
            }
        }

        return null;
    }

    // Search sent and stored messages by recipient
    public static ArrayList<Message> searchByRecipient(String number) {

        ArrayList<Message> results = new ArrayList<>();

        if (number == null) {
            return results;
        }

        for (Message message : sentMessages) {

            if (number.equalsIgnoreCase(message.recipient)) {
                results.add(message);
            }
        }

        for (Message message : storedMessages) {

            if (number.equalsIgnoreCase(message.recipient)
                    && !results.contains(message)) {

                results.add(message);
            }
        }

        return results;
    }

    // Search messages by sender
    public static ArrayList<Message> searchBySender(String name) {

        ArrayList<Message> results = new ArrayList<>();

        if (name == null) {
            return results;
        }

        for (Message message : sentMessages) {

            if (message.sender != null
                    && message.sender.toLowerCase()
                            .contains(name.toLowerCase())) {

                results.add(message);
            }
        }

        for (Message message : storedMessages) {

            if (message.sender != null
                    && message.sender.toLowerCase()
                            .contains(name.toLowerCase())
                    && !results.contains(message)) {

                results.add(message);
            }
        }

        return results;
    }

    // Delete stored message using its hash
    public static boolean deleteStoredMessageByHash(String hash) {

        if (hash == null) {
            return false;
        }

        boolean deleted = false;

        for (int i = 0; i < storedMessages.size(); i++) {

            if (storedMessages.get(i).messageHash != null
                    && storedMessages.get(i).messageHash
                            .equalsIgnoreCase(hash)) {

                storedMessages.remove(i);
                deleted = true;
                break;
            }
        }

        if (deleted) {
            rebuildIndexes();
            storeAllStoredMessages();
        }

        return deleted;
    }

    // Rebuild ID and hash arrays
    private static void rebuildIndexes() {

        messageIDs.clear();
        messageHashes.clear();

        for (Message message : sentMessages) {
            message.addToIndexes();
        }

        for (Message message : disregardedMessages) {
            message.addToIndexes();
        }

        for (Message message : storedMessages) {
            message.addToIndexes();
        }
    }

    // Display full message report
    public static String generateReport() {

        StringBuilder report = new StringBuilder();

        ArrayList<Message> allMessages = new ArrayList<>();

        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);
        allMessages.addAll(disregardedMessages);

        for (Message message : allMessages) {

            report.append("Message ID: ")
                    .append(message.messageID).append("\n");

            report.append("Message Hash: ")
                    .append(message.messageHash).append("\n");

            report.append("Sender: ")
                    .append(message.sender).append("\n");

            report.append("Recipient: ")
                    .append(message.recipient).append("\n");

            report.append("Message: ")
                    .append(message.messageText).append("\n");

            report.append("Status: ")
                    .append(message.status).append("\n");

            report.append("-----------------------------\n");
        }

        return report.length() == 0
                ? "No messages available."
                : report.toString();
    }

    // Total messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Get copies of the arrays
    public static ArrayList<Message> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }

    public static ArrayList<Message> getDisregardedMessages() {
        return new ArrayList<>(disregardedMessages);
    }

    public static ArrayList<Message> getStoredMessages() {
        return new ArrayList<>(storedMessages);
    }

    public static ArrayList<String> getMessageHashes() {
        return new ArrayList<>(messageHashes);
    }

    public static ArrayList<String> getMessageIDs() {
        return new ArrayList<>(messageIDs);
    }

    // Clear arrays for unit testing
    public static void clearAllMessages() {

        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();

        totalMessagesSent = 0;
    }

    // Getters
    public String getSender() {
        return sender;
    }

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
