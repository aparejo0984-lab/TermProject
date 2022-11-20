/**
 * A Class that holds general methods for user input
 *
 * @author  Anna Parejo
 * @version 1.0
 * @since   2022-11-20
 */


import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class Utils {

    /**
     * Check if input is numeric
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }

    /**
     * CHeck if email is valid
     * @param email
     * @return
     */
    public boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }

    /**
     * Generate Random digits
     * @param n
     * @return
     */
    public static int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }

    /**
     * Format Numeric
     * @param currencyAmount
     * @return
     */
    public static String formatNumeric(double currencyAmount) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(currencyAmount);
    }

    /**
     * Format and cleans string
     * @param str
     * @return
     */
    public static String formatString(String str) {
        return str.replace("[", "").replace("]", "");
    }

    /**
     * Format currency
     * @param currencyAmount
     * @return
     */
    public static String formatCurrency(double currencyAmount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
        return currencyFormat.format(currencyAmount);
    }

    /**
     * Check if input is valid
     * @param option
     * @param optionNum
     * @return
     */
    public static boolean isInputValid(String option, int optionNum)
    {
        if (isNumeric(option)) {
            int selectedOption = Integer.parseInt(option);
            if (selectedOption < 0 || selectedOption > optionNum ) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}