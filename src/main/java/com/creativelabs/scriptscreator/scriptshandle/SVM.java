package com.creativelabs.scriptscreator.scriptshandle;

import java.util.Scanner;

public class SVM {
    public static void main(String[] args) {
        String input =
                "    LUMBERJACKGreetings_01            =       \"SVM_7_LUMBERJACKGreetings_01\"               ;//Chwała Silvanowi!\n" +
                        "    LUMBERJACKGreetings_02            =       \"SVM_7_LUMBERJACKGreetings_02\"               ;//Oby Alrik żył jak najdłużej!\n" +
                        "    //hunter\n" +
                        "    HUNTERGreetings_01            =       \"SVM_7_HUNTERGreetings_01\"               ;//Dobry srebrzak to martwy srebrzak.\n" +
                        "    HUNTERGreetings_02            =       \"SVM_7_HUNTERGreetings_02\"               ;//Oby zwierzyny nie zabrakło!\n" +
                        "    HUNTERGreetings_03            =       \"SVM_7_HUNTERGreetings_03\"               ;//Dobra broń to podstawa.\n" +
                        "    //city\n" +
                        "    CITYGreetings_01            =       \"SVM_7_CITYGreetings_01\"               ;//Niech żyje Samoa!\n" +
                        "    CITYGreetings_02            =       \"SVM_7_CITYGreetings_02\"               ;//Niech żyje król Pyrron!\n" +
                        "    CITYGreetings_03            =       \"SVM_7_CITYGreetings_03\"               ;//W końcu do portu zawitał jakiś statek!\n" +
                        "\n" +
                        "\n" +
                        "    Weather_Good_01                        =       \"SVM_7_Weather_Good_01\"                                ;//Ależ piękny mamy dzień!\n" +
                        "    Weather_Good_02                        =       \"SVM_7_Weather_Good_02\"                                ;//Taką pogodę to ja lubię.\n" +
                        "\n" +
                        "    Weather_Bad_01                        =       \"SVM_7_Weather_Bad_01\"                                ;//Brzydka pogoda.\n" +
                        "    Weather_Bad_02                        =       \"SVM_7_Weather_Bad_02\"                                ;//Porządnie leje.";

        Scanner scanner = new Scanner(input);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            int equalIndex = line.indexOf('=');
            if (equalIndex != -1) {
                String extractedText = line.substring(0, line.indexOf("=")).trim();
                System.out.println("var string " + extractedText + ";");
            }
        }
        scanner.close();
    }
}
