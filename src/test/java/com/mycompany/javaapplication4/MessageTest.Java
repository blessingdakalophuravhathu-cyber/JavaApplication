/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaapplication4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

public class MessageTest {

    @Test
    public void testMessageID() {
        Message m = new Message(
                0, "+27718693002", "Hi Mike, can you join us tonight?");

        assertTrue(m.checkMessageD());
        assertEquals(10, m.getMessageID().length());
    }

    @Test
    public void testRecipientSuccess() {
        Message m = new Message(
                0, "+27718693002", "Hello");

        assertEquals("Cell phone number successfully captured.",
                m.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        Message m = new Message(
                0, "08575975889", "Hello");

        assertEquals(
                "Cell phone number is incorrectly formatted or does "
                + "not contain an international code. Please correct "
                + "the number and try again.",
                m.checkRecipientCell());
    }

    @Test
    public void testMessageLengthSuccess() {
        Message m = new Message(
                0, "+27718693002", "Hello Mike");

        assertEquals("Message ready to send.",
                m.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        Message m = new Message(
                0, "+27718693002", "A".repeat(251));

        assertEquals(
                "Message exceeds 250 characters by 1; please reduce "
                + "the size.",
                m.checkMessageLength());
    }

    @Test
    public void testMessageHash() {
        Message m = new Message(
                0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        String expected = m.getMessageID().substring(0, 2)
                + ":0:HITONIGHT";

        assertEquals(expected, m.createMessageHash());
    }

    @Test
    public void testSendMessage() {
        Message m = new Message(
                0, "+27718693002", "Hello Mike");

        int before = m.returnTotalMessages();

        assertEquals("Message successfully sent.",
                m.sentMessage(new Scanner("1\n")));

        assertEquals("Sent", m.getStatus());
        assertEquals(before + 1, m.returnTotalMessages());
    }

    @Test
    public void testDisregardMessage() {
        Message m = new Message(
                1, "+27718693002", "Hello Keegan");

        assertEquals("Press O to delete the message.",
                m.sentMessage(new Scanner("2\n")));

        assertEquals("Disregarded", m.getStatus());
    }

    @Test
    public void testStoreMessage() {
        Message m = new Message(
                2, "+27718693002", "Hello Keegan");

        assertEquals("Message successfully stored.",
                m.sentMessage(new Scanner("3\n")));

        assertEquals("Stored", m.getStatus());
    }

    @Test
    public void testInvalidOption() {
        Message m = new Message(
                0, "+27718693002", "Hello");

        assertEquals("Invalid option. Please choose 1, 2, or 3.",
                m.sentMessage(new Scanner("9\n")));
    }
}
