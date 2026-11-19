package com.mycompany.javaapplication4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

public class MessageTestPart3 {

    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    @BeforeEach
    public void setUp() {

        Message.clearAllMessages();

        message1 = new Message(
                0, "Developer", "+27834557896",
                "Did you get the cake?");

        message2 = new Message(
                1, "Customer A", "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");

        message3 = new Message(
                2, "Customer B", "+27834484567",
                "Yohoooo, I am at your gate.");

        message4 = new Message(
                3, "Developer 0838884567", "+27834557896",
                "It is dinner time !");

        message5 = new Message(
                4, "Customer A", "+27838884567",
                "Ok, I am leaving without you.");

        // Populate the collections using the required message statuses.
        message1.sentMessage(new Scanner("1\n"));
        message2.sentMessage(new Scanner("3\n"));
        message3.sentMessage(new Scanner("2\n"));
        message4.sentMessage(new Scanner("1\n"));
        message5.sentMessage(new Scanner("3\n"));
    }

    @Test
    public void testSentMessagesArray() {
        assertEquals(2, Message.getSentMessages().size());

        assertTrue(Message.getSentMessages().contains(message1));
        assertTrue(Message.getSentMessages().contains(message4));
    }

    @Test
    public void testDisregardedMessagesArray() {
        assertEquals(1, Message.getDisregardedMessages().size());

        assertTrue(Message.getDisregardedMessages().contains(message3));
    }

    @Test
    public void testStoredMessagesArray() {
        assertEquals(2, Message.getStoredMessages().size());

        assertTrue(Message.getStoredMessages().contains(message2));
        assertTrue(Message.getStoredMessages().contains(message5));
    }

    @Test
    public void testMessageHashesAndIDsArrays() {
        assertEquals(5, Message.getMessageHashes().size());
        assertEquals(5, Message.getMessageIDs().size());
    }

    @Test
    public void testDisplayLongestMessage() {
        Message longest = Message.getLongestStoredMessage();

        assertNotNull(longest);
        assertEquals(message2.getMessageText(), longest.getMessageText());
    }

    @Test
    public void testSearchByMessageID() {
        Message found = Message.searchByMessageID(
                message2.getMessageID());

        assertNotNull(found);
        assertEquals(message2.getRecipient(), found.getRecipient());
        assertEquals(message2.getMessageText(), found.getMessageText());
    }

    @Test
    public void testSearchByRecipient() {
        assertEquals(2,
                Message.searchByRecipient("+27838884567").size());
    }

    @Test
    public void testSearchBySender() {
        assertEquals(2,
                Message.searchBySender("Developer").size());
    }

    @Test
    public void testDeleteMessageUsingHash() {
        String hash = message2.getMessageHash();

        assertTrue(Message.deleteStoredMessageByHash(hash));
        assertNull(Message.searchByMessageID(message2.getMessageID()));
        assertEquals(1, Message.getStoredMessages().size());
    }

    @Test
    public void testDisplayReport() {
        String report = Message.generateReport();

        assertTrue(report.contains(message1.getMessageHash()));
        assertTrue(report.contains(message1.getRecipient()));
        assertTrue(report.contains(message1.getMessageText()));

        assertTrue(report.contains(message4.getMessageHash()));
        assertTrue(report.contains(message4.getMessageText()));
    }

    @Test
    public void testReadStoredMessagesFromJSON() {
        // The setup has already written the two stored messages to JSON.
        Message.clearAllMessages();

        Message.loadStoredMessages();

        assertEquals(2, Message.getStoredMessages().size());
        assertTrue(Message.getStoredMessages().stream()
                .anyMatch(m -> m.getMessageText().equals(
                        "Where are you? You are late! I have asked you to be on time.")));
    }
}
