package com.mycompany.javaapplication4;

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
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        if (password == null) {
            return false;
        }

        boolean capital = false;
        boolean number = false;
        boolean special = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                capital = true;
            }
            if (Character.isDigit(c)) {
                number = true;
            }
            if (!Character.isLetterOrDigit(c)) {
                special = true;
            }
        }

        return password.length() >= 8
                && capital && number && special;
    }

    public boolean checkCellPhoneNumber() {
        return cellPhoneNumber != null
                && cellPhoneNumber.matches("\\+27[0-9]{9}");
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure "
                    + "that your username contains an underscore and is "
                    + "no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure "
                    + "that the password contains at least eight "
                    + "characters, a capital letter, a number, "
                    + "and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number is incorrectly formatted or does "
                    + "not contain an international code. Please correct "
                    + "the number and try again.";
        }

        return "User successfully registered.";
    }

    public boolean loginUser() {
        loginStatus = loginUsername != null
                && loginPassword != null
                && loginUsername.equals(username)
                && loginPassword.equals(password);

        return loginStatus;
    }

    public String returnLoginStatus() {
        if (loginStatus) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}
