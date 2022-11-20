/**
 * User class that holds account creation and modification
 *
 * @author  Anna Parejo
 * @version 1.0
 * @since   2022-11-20
 */
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class User {
    Main main = new Main();
    Utils utils = new Utils();
    FileInputOutput fileInputOutput = new FileInputOutput();

    /**
     * Creation of user
     * @throws FileNotFoundException
     */
    public void createUser() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the following information:");

        System.out.println("Email:");
        String email = input.nextLine();

        if (!this.utils.isEmailValid(email)) {
            System.out.println("Incorrect email. Please try again.");
            System.out.println("Exit");
        } else {
            System.out.println("Password:");
            String password = input.nextLine();

            System.out.println("Name:");
            String name = input.nextLine();

            FileInputOutput fileInputOutput = new FileInputOutput();
            List<List> accountResults = fileInputOutput.readFile();
            StringBuilder userDetails = new StringBuilder();

            for (int key = 0; key < accountResults.size(); key++) {
                if (key > 0) {
                    userDetails.append("\n");
                }
                for (int i = 0; i < accountResults.get(key).size(); i++) {
                    userDetails.append(accountResults.get(key).get(i));
                    if (i < accountResults.get(key).size() - 1) {
                        userDetails.append("/");
                    }
                }
            }
            userDetails.append("\n" + email);
            userDetails.append("/" + password);
            userDetails.append("/" + name);

            fileInputOutput.writeToFile(userDetails);
            System.out.println("");
            System.out.println("Congratulations. Account sucessfully created.");
            System.out.println("Welcome to Toronto Banking system.");

            main.mainMenu();
            String continueOption = main.selectMainMenu();
            main.execute(Integer.parseInt(continueOption),accountResults.size() + 1);
        }
    }

    /**
     * Check if user is a valid user from file
     * @return
     * @throws FileNotFoundException
     */
    public int checkForValidUser() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your account credentials:");

        System.out.println("Email:");
        String email = input.nextLine();

        if (!this.utils.isEmailValid(email)) {
            System.out.println("Incorrect email. Please try again.");
            System.out.println("Exit");
        }

        System.out.println("Password:");
        String password = input.nextLine();

        FileInputOutput fileInputOutput = new FileInputOutput();
        List<List> accountResults = fileInputOutput.readFile();

        for (int key = 0; key < accountResults.size(); key++) {
            String user = accountResults.get(key).toString();
            String userDetails = user.replace("[", "").replace("]", "");
            String[] result = userDetails.split("/");
            if (result[0].equals(email) && result[1].equals(password)) {
                return key;
            }
        }
        return 9999;
    }
}

