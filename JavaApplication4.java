/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javaapplication4;

import java.util.Scanner;

public class JavaApplication4 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Login login = new Login();

        System.out.print("Enter your first name: ");
        login.firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        login.lastName = input.nextLine();

        System.out.print("Enter your username: ");
        login.username = input.nextLine();

        System.out.print("Enter your password: ");
        login.password = input.nextLine();

        System.out.print("Enter your cell phone number: ");
        login.cellPhoneNumber = input.nextLine();

        System.out.println();

        if (login.checkUserName()) {
            System.out.println("Username successfully captured.");
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }

        if (login.checkPasswordComplexity()) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }

        if (login.checkCellPhoneNumber()) {
            System.out.println("Cell number successfully captured.");
        } else {
            System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
        }

        if (login.checkUserName()
                && login.checkPasswordComplexity()
                && login.checkCellPhoneNumber()) {

            System.out.println();
            System.out.println(login.registerUser());

            System.out.println();
            System.out.print("Enter your username to login: ");
            login.loginUsername = input.nextLine();

            System.out.print("Enter your password to login: ");
            login.loginPassword = input.nextLine();

            login.loginStatus = login.loginUser();

            System.out.println();
            System.out.println(login.returnLoginStatus());
        }

        input.close();
    }
}