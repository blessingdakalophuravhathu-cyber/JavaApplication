// Cellphone number regex researched from Warren (2016).
// Source: Luke Warren Development, "Regex: South African Phone number".
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaapplication4;

/**
 *
 * @author Student
 */

public class Login {

    String username;
    String password;
    String cellPhoneNumber;

    String firstName;
    String lastName;

    String loginUsername;
    String loginPassword;

    boolean loginStatus;

    public boolean checkUserName() {

        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPasswordComplexity() {

        boolean capitalLetter = false;
        boolean number = false;
        boolean specialCharacter = false;

        for (int i = 0; i < password.length(); i++) {

            char character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                capitalLetter = true;
            }

            if (Character.isDigit(character)) {
                number = true;
            }

            if (!Character.isLetterOrDigit(character)) {
                specialCharacter = true;
            }
        }

        if (password.length() >= 8
                && capitalLetter
                && number
                && specialCharacter) {

            return true;

        } else {
            return false;
        }
    }

    public boolean checkCellPhoneNumber() {

        if (cellPhoneNumber.matches("^\\+27[0-9]{9}$")) {
            return true;
        } else {
            return false;
        }
    }

    public String registerUser() {

        if (!checkUserName()) {

            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

        } else if (!checkPasswordComplexity()) {

            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

        } else {

            return "User successfully registered.";
        }
    }

    public boolean loginUser() {

        if (loginUsername.equals(username)
                && loginPassword.equals(password)) {

            return true;

        } else {

            return false;
        }
    }

    public String returnLoginStatus() {

        if (loginStatus) {

            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";
        }
    }
}
