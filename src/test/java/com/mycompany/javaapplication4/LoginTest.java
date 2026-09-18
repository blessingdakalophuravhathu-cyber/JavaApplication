
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.javaapplication4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testCheckUserNameValid() {
        Login login = new Login();
        login.username = "kyl_1";

        assertTrue(login.checkUserName());
    }

    @Test
    public void testCheckUserNameInvalid() {
        Login login = new Login();
        login.username = "kyle123";

        assertFalse(login.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexityValid() {
        Login login = new Login();
        login.password = "Password1!";

        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexityInvalid() {
        Login login = new Login();
        login.password = "password";

        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumberValid() {
        Login login = new Login();
        login.cellPhoneNumber = "+27821234567";

        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumberInvalid() {
        Login login = new Login();
        login.cellPhoneNumber = "0821234567";

        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testLoginUserValid() {
        Login login = new Login();

        login.username = "kyl_1";
        login.password = "Password1!";
        login.loginUsername = "kyl_1";
        login.loginPassword = "Password1!";

        assertTrue(login.loginUser());
    }

    @Test
    public void testLoginUserInvalid() {
        Login login = new Login();

        login.username = "kyl_1";
        login.password = "Password1!";
        login.loginUsername = "wrong";
        login.loginPassword = "wrong";

        assertFalse(login.loginUser());
    }

    @Test
    public void testReturnLoginStatusSuccessful() {
        Login login = new Login();

        login.firstName = "John";
        login.lastName = "Smith";
        login.loginStatus = true;

        assertEquals(
            "Welcome John, Smith it is great to see you again.",
            login.returnLoginStatus()
        );
    }

    @Test
    public void testReturnLoginStatusFailed() {
        Login login = new Login();

        login.loginStatus = false;

        assertEquals(
            "Username or password incorrect, please try again.",
            login.returnLoginStatus()
        );
    }
}
