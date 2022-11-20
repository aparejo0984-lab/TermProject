/**
 * A Program simulation of a primitive banking system that the following:
 * Allows the user to create bank accounts for clients,
 * Allow them to do bank transactions.
 *
 * @author  Anna Parejo
 * @version 1.0
 * @since   2022-11-20
 */

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static Utils utils = new Utils();

    /**
     * Main method
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        welcomeMessage();
        firstMenu();
        User user = new User();

        String optionSelected = selectMainMenu();

        if (utils.isInputValid(optionSelected, 3)) {
            if (Integer.parseInt(optionSelected) == 1) {
                user.createUser();
            } else if (Integer.parseInt(optionSelected) == 0) {
                System.out.println("Thank you for using our banking system.");
                System.out.println("Exit....");
            }else {
                int userKey = user.checkForValidUser();
                if (userKey == 9999) {
                    System.out.println("Invalid credentials. Please try again..");
                    System.out.println("Exit....");
                } else {
                    mainMenu();
                    String selected = selectMainMenu();

                    if (utils.isInputValid(selected, 7)) {
                        execute(Integer.parseInt(selected),userKey);
                        System.out.println("");
                        System.out.println("===========================");
                        System.out.println("Do you want to continue.");
                        System.out.println("[1] Continue");
                        System.out.println("[0] Exit");
                        System.out.println("===========================");

                        Scanner option = new Scanner(System.in);
                        System.out.println("Please select an option:");
                        String continueSelected = option.nextLine();

                        if (Integer.parseInt(continueSelected) == 1) {
                            mainMenu();
                            String continueOption = selectMainMenu();
                            execute(Integer.parseInt(continueOption),userKey);
                        } else {
                            System.out.println("Thank you for using our banking system.");
                            System.out.println("Exit....");
                        }
                    } else {
                        System.out.println("Invalid entry. Please try again....");
                        System.out.println("Exit....");
                    }
                }
            }
        } else {
            System.out.println("Invalid entry. Please try again....");
            System.out.println("Exit....");
        }
    }

    /**
     * Selection of main menu
     * @return
     */
    public static String selectMainMenu() {

        Scanner option = new Scanner(System.in);
        System.out.println("Please select an option:");
        String optionSelected = option.nextLine();
        return optionSelected;
    }

    /**
     * Displaying of welcome message
     */
    public static void welcomeMessage() {
        System.out.println("============================================================");
        System.out.println("*                                                          *");
        System.out.println("*                                                          *");
        System.out.println("*           WELCOME TO TORONTO BANKING SYSTEM              *");
        System.out.println("*                                                          *");
        System.out.println("*                                                          *");
        System.out.println("============================================================\n");
    }

    /**
     * Displaying of first menu
     */
    public static void firstMenu() {
        System.out.println("Are you an existing or new user:");
        System.out.println("[1] New User");
        System.out.println("[2] Existing user");
        System.out.println("[0] Exit");
    }

    /**
     * Displaying of main menu
     */
    public static void mainMenu() {
        System.out.println("==============================================================================");
        System.out.println("*                 Select available options for your account:                 *");
        System.out.println("*                           [1] Create an account                            *");
        System.out.println("*                           [2] Modify an existing account                   *");
        System.out.println("*                           [3] Display account details and balances         *");
        System.out.println("*                           [4] Deposit money                                *");
        System.out.println("*                           [5] Draw money                                   *");
        System.out.println("*                           [6] Pay Utility Bills                            *");
        System.out.println("*                           [0] Sign Off                                     *");
        System.out.println("==============================================================================");
    }

    /**
     * Displaying of Bills payment menu
     */
    public static void billsPaymentMenu() {
        System.out.println("==============================================================================");
        System.out.println("*                          Bills Payment Options:                            *");
        System.out.println("*                           [1] Toronto Hydro                                *");
        System.out.println("*                           [2] Ontario Energy Board                         *");
        System.out.println("*                           [3] Fido                                         *");
        System.out.println("*                           [5] Rogers                                       *");
        System.out.println("*                           [6] Bell                                         *");
        System.out.println("==============================================================================");
    }

    /**
     * Main execute method based from user selection
     * @param option
     * @param userKey
     * @throws FileNotFoundException
     */
    public static void execute(int option, int userKey) throws FileNotFoundException {
        BankAccount bankAccount = new BankAccount();
        switch (option) {
            case 1:
                bankAccount.createAccount(userKey);
                break;
            case 2:
                bankAccount.modifyAccount(userKey);
                break;
            case 3:
                bankAccount.displayCurrentBalances(userKey);
                break;
            case 4:
                bankAccount.depositWithdrawMoney(userKey, "deposit");
                break;
            case 5:
                bankAccount.depositWithdrawMoney(userKey, "withdraw");
                break;
            case 6:
                billsPaymentMenu();
                Scanner input = new Scanner(System.in);
                System.out.println("Please bills payment options:");
                String billPay = input.nextLine();

                bankAccount.depositWithdrawMoney(userKey, "pay");
                break;
            case 0:
                System.out.println("THANK YOU FOR USING OUR BANKING SYSTEM");
                System.out.println("Exit....");
                break;
        }
    }

    /**
     * Convert Account type ineger to account type name
     * @param input
     * @return
     */
    public static String accountTypeName(String input) {
        switch (input) {
            case "1":
                return "Savings Account";
            default:
                return "Chequing Account";
        }
    }
}