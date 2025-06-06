package com.sfs.global.qa.utils;


import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CsvUtility {

    static String userName = null;

    private CsvUtility() {
    }

    public static void writeCsvFille(String csvFilePath,String[][] filedata) {
        try {
            FileWriter writer = new FileWriter(csvFilePath);

            // Create a CSVWriter with a custom CSVWriterBuilder
            CSVWriter csvWriter = (CSVWriter) new CSVWriterBuilder(writer)
                    .withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER) // Set quotechar to NO_QUOTE_CHARACTER
                    .build();

            csvWriter.writeAll(Arrays.asList(filedata));
            csvWriter.close();
        } catch (IOException e) {
            throw new NullPointerException("Write CSV: Method had Exceptions >>" + e.getMessage());
        }
    }

    public static String writeData(String filepath) {
        String firstName = RandomGenerator.getrandomString(4);
        String lastName = RandomGenerator.getrandomString(4);
        userName = firstName + " " + lastName;
        String[][] feeddata = {
                {"First Name*", "Middle Name", "Last Name*", "Email*", "Phone Number (Optional)",
                        "Driver",
                        "Manager"},
                {firstName,"", lastName, "automation_user" +  RandomGenerator.randomIntByFigures(4)
                        + "@email.com", String.valueOf(RandomGenerator.randomIntByFigures(10)),"st1","st2"}
        };
        CsvUtility.writeCsvFille(filepath,feeddata);
        return filepath;
    }

    public static String duplicateData(String filepath,String fileName, String username, String emailId) {
        String[] nameParts = username.split(" ");
        if (nameParts.length < 2) {
            throw new IllegalArgumentException("Username must contain both first name and "
                    + "last name separated by a space.");
        }
        String firstName = nameParts[0];
        String lastName = nameParts[1];
        String[][] feeddata = {
                {"First Name*", "Middle Name", "Last Name*", "Email*", "Phone Number (Optional)", "Driver", "Manager"},
                {firstName, "", lastName, emailId, String.valueOf(RandomGenerator.randomIntByFigures(10)), "st1", "st2"}
        };
        CsvUtility.writeCsvFille(filepath, feeddata);
        return filepath;
    }


    public static String getUserName() {
        return userName;
    }
}
