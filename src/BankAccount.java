/**
 * A Class that holds bank account transactions
 *
 * @author  Anna Parejo
 * @version 1.0
 * @since   2022-11-20
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankAccount {
    static Utils utils = new Utils();
    User user = new User();

    FileInputOutput fileInputOutput = new FileInputOutput();

    /**
     * Creating Account for user
     * @param userKey
     * @throws FileNotFoundException
     */
    public void createAccount(int userKey) throws FileNotFoundException {
        List<List> accountResults = fileInputOutput.readFile();

        Scanner input = new Scanner(System.in);

        System.out.println("Enter the type of account you want to open:");
        System.out.println("[1] Savings Account");
        System.out.println("[2] Chequing Account");

        String optionSelected = input.nextLine();

        StringBuilder accountDetails = new StringBuilder();
        int accountNumber = utils.generateRandomDigits(10);

        for (int key = 0; key < accountResults.size(); key++) {
            String user = accountResults.get(key).toString();
            String userDetails = user.replace("[", "").replace("]", "");
            if (key > 0) {
                accountDetails.append("\n");
            }
            if (key == userKey) {
                for (int i = 0; i < accountResults.get(userKey).size(); i++) {
                    accountDetails.append(userDetails);
                }
                accountDetails.append("/" + optionSelected);
                accountDetails.append(";" + accountNumber);
                accountDetails.append(";00.00");
            } else {
                accountDetails.append(userDetails);
            }
        }

        System.out.println("Account successfully created:");
        System.out.println("============================================");
        System.out.println("Account type: " + accountTypeName(String.valueOf(optionSelected)));
        System.out.println("Account number: " + accountNumber);
        System.out.println("Account balance: " + "00.00");
        System.out.println("============================================");

        fileInputOutput.writeToFile(accountDetails);

    }

    /**
     * MOdifying user account details
     * @param userKey
     * @throws FileNotFoundException
     */
    public static void modifyAccount(int userKey) throws FileNotFoundException {
        FileInputOutput fileInputOutput = new FileInputOutput();
        List<List> accountResults = fileInputOutput.readFile();

        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the new information:");

        System.out.println("Name:");
        String name = input.nextLine();

        StringBuilder accountDetails = new StringBuilder();

        for (int key = 0; key < accountResults.size(); key++) {
            String user = accountResults.get(key).toString();
            String userDetails = user.replace("[", "").replace("]", "");
            String[] result = userDetails.split("/");
            if (key > 0) {
                accountDetails.append("\n");
            }
            if (key == userKey) {
                for (int i = 0; i < result.length; i++) {
                    if (i == 2) {
                        accountDetails.append(name);
                    } else {
                        accountDetails.append(result[i]);
                    }
                    if (i < result.length - 1) {
                        accountDetails.append("/");
                    }
                }
            } else {
                accountDetails.append(userDetails);
            }
        }
        fileInputOutput.writeToFile(accountDetails);
        System.out.println("");
        System.out.println("Account information is sucessfully modified!");
    }

    /**
     * Displaying current balance for user
     * @param userKey
     * @throws FileNotFoundException
     */
    public static void displayCurrentBalances(int userKey) throws FileNotFoundException {
        FileInputOutput fileInputOutput = new FileInputOutput();
        List<List> accountResults = fileInputOutput.readFile();

        String user = accountResults.get(userKey).toString();
        String userDetails = user.replace("[", "").replace("]", "");
        String[] result = userDetails.split("/");

        System.out.println("\nACCOUNT DETAILS:");
        System.out.println("Name: " + result[2]);
        System.out.println("Email: " + result[0]);
        System.out.println("\nLIST OF ACCOUNTS:");

        String[] accountLabel = {"Account Type", "Account Number", "Balance"};

        for (int key = 3;key < result.length; key++) {
            String[] userAccount = result[key].split(";");

            System.out.println("==============================");
            for (int i = 0; i < userAccount.length; i++) {
                if (i == 0) {
                    System.out.println(accountLabel[i] + ": " + accountTypeName(userAccount[i]));
                }  else if (i == 2) {
                    userAccount[i]=userAccount[i].replaceAll(",", "");
                    System.out.println(accountLabel[i] + ": " + utils.formatCurrency(Double.parseDouble(userAccount[i])));
                } else {
                    System.out.println(accountLabel[i] + ": " + userAccount[i]);
                }
            }
        }
    }

    /**
     * Deposit or withdraw money from account
     * @param userKey
     * @param action
     * @throws FileNotFoundException
     */
    public static void depositWithdrawMoney(int userKey, String action) throws FileNotFoundException {
        FileInputOutput fileInputOutput = new FileInputOutput();
        List<List> accountResults = fileInputOutput.readFile();

        String user = accountResults.get(userKey).toString();
        String userDetails =  utils.formatString(user);
        String[] result = userDetails.split("/");
        if (result.length == 3) {
            System.out.println("You dont have yet an existing account.");
            System.out.println("Go back to the main menu and create an account.");
        }
        else {
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter the type of account:");

            ArrayList<Integer> validOptions = new ArrayList<>();
            for (int key = 3;key < result.length; key++) {
                String[] userAccount = result[key].split(";");
                System.out.println("[" + userAccount[0] + "] " + accountTypeName(String.valueOf(userAccount[0])));
                validOptions.add(Integer.valueOf(userAccount[0]));
            }

            String optionSelected = input.nextLine();
            boolean validUserOptions = false;
            for (int opt = 0; opt < validOptions.size(); opt++) {
                if (Integer.parseInt(optionSelected) == Integer.parseInt(String.valueOf(validOptions.get(opt)))) {
                    validUserOptions = true;
                }
            }
            if(validUserOptions == false) {
                System.out.println("Invalid Option. Please try again:");
                System.out.println("Make sure you selected a valid option");
                System.out.println("Exit");
            } else {
                boolean isValidTransaction = true;
                String amount = "";
                if (action == "deposit") {
                    System.out.println("Please enter the amount you want to deposit:");
                    amount = input.nextLine();
                } else {
                    if (action == "pay") {
                        System.out.println("Please enter the amount you want to pay:");
                    } else {
                        System.out.println("Please enter the amount you want to withdraw:");
                    }
                    amount = input.nextLine();
                    for (int key = 3;key < result.length; key++) {
                        String[] userAccount = result[key].split(";");
                        if (Integer.parseInt(optionSelected) == Integer.parseInt(userAccount[0])) {
                            if (Double.parseDouble(amount) > Double.parseDouble(userAccount[2])) {
                                isValidTransaction = false;
                                System.out.println("Withdrawn amount is greater than the money in your bank");
                                System.out.println("Please try again and go back to main menu.");
                            }

                        }
                    }
                }

                if (isValidTransaction) {
                    StringBuilder accountDetails = new StringBuilder();
                    for (int rkey = 0; rkey < accountResults.size(); rkey++) {
                        String user1 = accountResults.get(rkey).toString();
                        String userDetails1 = user1.replace("[", "").replace("]", "");
                        String[] result1 = userDetails1.split("/");
                        if (rkey > 0) {
                            accountDetails.append("\n");
                        }
                        if (rkey == userKey) {
                            accountDetails.append(result1[0] + "/");
                            accountDetails.append(result1[1] + "/");
                            accountDetails.append(result1[2]);
                            for (int key = 3;key < result1.length; key++) {
                                accountDetails.append("/");
                                String[] userAccount1 = result1[key].split(";");
                                String newAmount =  userAccount1[2];
                                for (int i = 0; i < userAccount1.length; i++) {
                                    if (i == 0 && Integer.parseInt(optionSelected) == Integer.parseInt(userAccount1[i])) {
                                        userAccount1[2] = userAccount1[2].replaceAll(",", "");
                                        if (action == "deposit") {
                                            newAmount = String.valueOf(Double.parseDouble(userAccount1[2]) + Double.parseDouble(amount));
                                        } else {
                                            newAmount = String.valueOf(Double.parseDouble(userAccount1[2]) - Double.parseDouble(amount));
                                        }
                                    }
                                }
                                accountDetails.append(userAccount1[0] + ";");
                                accountDetails.append(userAccount1[1]+ ";");
                                accountDetails.append(utils.formatNumeric(Double.parseDouble(newAmount)));
                            }
                        } else {
                            accountDetails.append(userDetails1);
                        }
                    }
                    fileInputOutput.writeToFile(accountDetails);
                    if (action == "deposit") {
                        System.out.println("You successfully deposited to your account.");
                    } else if (action == "pay") {
                        System.out.println("You successfully pay from your account.");
                    } else {
                        System.out.println("You successfully withdraw to your account.");
                    }

                    System.out.println("Here are the new account balance.");
                    System.out.println("===========================================");
                    displayCurrentBalances(userKey);
                }
            }
        }
    }

    public static String accountTypeName(String input) {
        switch (input) {
            case "1":
                return "Savings Account";
            default:
                return "Chequing Account";
        }
    }
}
