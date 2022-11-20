/**
 * A Class that holds the reading and writing of file
 *
 * @author  Anna Parejo
 * @version 1.0
 * @since   2022-11-20
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileInputOutput {
    /**
     * Reading the file
     * @return
     * @throws FileNotFoundException
     */
    public List<List> readFile() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("accountDetails.txt"));

        List<List> arraylist = new ArrayList<List>();

        while (scan.hasNextLine()) {
            List accountList=new ArrayList();
            accountList.add(scan.nextLine());
            arraylist.add(accountList);
        }

        scan.close();
        return arraylist;
    }

    /**
     * Writing the processed employee details to a file
     * @exception FileNotFoundException On input error.
     * @return nothing
     */
    public void writeToFile(StringBuilder fileToWrite) throws FileNotFoundException {
        try {
            FileWriter myWriter = new FileWriter("accountDetails.txt");
            myWriter.write(String.valueOf(fileToWrite));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}