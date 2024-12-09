import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.System.out;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.JFileChooser;
import java.util.Scanner;

public class DataSaver
{
    public static void main(String[] args)
    {
        /**
         * this Scanner takes all user input
         */
        Scanner in = new Scanner(System.in);
        /**
         * this ArrayList holds a list of all completed records input by the user
         */
        ArrayList<String> recs = new ArrayList<>();
        /**
         * this String holds the first names input by the user
         */
        String firstName = "";
        /**
         * this String holds the last names input by the user
         */
        String lastName = "";
        /**
         * this String holds the int version of the ID input by the user (so that getRangedInt can be used)
         */
        int userNumber = 0;
        /**
         * this String holds the String version of the ID from userNumber with the addition of a variable number of zeroes on the front
         */
        String numberID = "";
        /**
         * this String holds the email addresses input by the user
         */
        String email = "";
        /**
         * this int holds the int version of the year of birth entered by the user (so that getRangedInt can be used)
         */
        int yobInt = 0;
        /**
         * this String holds the String version of the year of birth from yobInt
         */
        String yob = "";
        /**
         * this String holds the concatenation of firstName, lastName, numberID, email, and yob
         */
        String recEntry = "";
        /**
         * this boolean tracks the true/false value of whether the user would like to continue entering records
         */
        boolean continueYN = false;
        /**
         * this String holds the filename (selected by the user) of the records file the user has created
         */
        String fileName = "";

        /**
         * this algorithm allows the user to create custom records to put into a text file, select a filename, and create a text file in the src directory
         */
        try {
            /**
             * this algorithm allows the user to enter a first name, last name, ID number, email, and year of birth an unlimited number of times to create records
             */
            do {
                    firstName = SafeInput.getNonZeroLenString(in, "Please enter a first name");
                    lastName = SafeInput.getNonZeroLenString(in, "Please enter a last name");

                    userNumber = SafeInput.getRangedInt(in, "Please enter an ID number", 1, 999999);

                    if(userNumber < 10) {
                        numberID = "00000" + userNumber;
                    }else if(userNumber < 100) {
                        numberID = "0000" + userNumber;
                    }else if(userNumber < 1000) {
                        numberID = "000" + userNumber;
                    }else if(userNumber < 10000) {
                        numberID = "00" + userNumber;
                    }else if(userNumber < 100000) {
                        numberID = "0" + userNumber;
                    }else {
                        numberID = Integer.toString(userNumber);
                    }

                    email = SafeInput.getRegExString(in, "Please enter an email address", "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+[\\.][a-zA-Z]+$");

                    yobInt = SafeInput.getRangedInt(in, "Please enter a year of birth", 1000, 9999);

                    yob = Integer.toString(yobInt);

                    recEntry = firstName + ", " + lastName + ", " + numberID + ", " + email + ", " + yob;

                    recs.add(recEntry);

                    continueYN = SafeInput.getYNConfirm(in, "Would you like to continue entering records?");
            }while (continueYN);

            fileName = SafeInput.getRegExString(in, "Enter the name of your file", "[a-zA-Z0-9_]+");

            /**
             * this File holds the current directory
             */
            File workingDirectory = new File(System.getProperty("user.dir"));
            /**
             * this Path holds the current directory concatenated with the location of the text file that will be created
             */
            Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName + ".csv");

            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            /**
             * this algorithm writes the data from the recs array to a text file
             */
            for(String rec : recs)
            {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("\nThe data file has been written.");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("\nThe file couldn't be found.");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("\nAn exception occurred.");
            e.printStackTrace();
        }
    }
}
