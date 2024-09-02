package com.creativelabs.scriptscreator.scriptshandle;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SVMExtract {

    public static void main(String[] args) {
        String text =
                "Smalltalk115  = \"SVM_7_Smalltalk115\"  ;//...trzeba coś z tym zrobić...\n" +
                        "Smalltalk116  = \"SVM_7_Smalltalk116\"  ;//...nie ma co narzekać...\n" +
                        "Smalltalk117  = \"SVM_7_Smalltalk117\"  ;//...lepiej nie wygaduj takich rzeczy...\n" +
                        "Smalltalk118  = \"SVM_7_Smalltalk118\"  ;//...wątpię, żeby to była prawda...\n" +
                        "Smalltalk119  = \"SVM_7_Smalltalk119\"  ;//...na to już nic nie poradzisz...\n" +
                        "Smalltalk120  = \"SVM_7_Smalltalk120\"  ;//...nie musisz mi o tym mówić...\n" +
                        "Smalltalk121  = \"SVM_7_Smalltalk121\"  ;//...skończ powtarzać głupie plotki...\n" +
                        "Smalltalk122  = \"SVM_7_Smalltalk122\"  ;//...jakby mało nam było problemów...\n" +
                        "Smalltalk123  = \"SVM_7_Smalltalk123\"  ;//...może i masz trochę racji...\n" +
                        "Smalltalk124  = \"SVM_7_Smalltalk124\"  ;//...kto by pomyślał...\n" +
                        "Smalltalk125  = \"SVM_7_Smalltalk125\"  ;//...posłuchaj dalej, nie przerywaj...\n" +
                        "Smalltalk126  = \"SVM_7_Smalltalk126\"  ;//...a to ci dopiero historia...";

        Scanner scanner = new Scanner(text);
        String convertedNames = "";
        String convertedTexts = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.contains("\"")) {
                String name = line.substring(line.indexOf(";//")+3) + "\n";
                convertedNames = convertedNames + name;
                String extractedText = line.substring(line.indexOf("\"")+1);
                String extractedText2 = extractedText.substring(0, extractedText.indexOf("\"")) + "\n";
                convertedTexts = convertedTexts + extractedText2;
            } else {
                convertedNames = convertedNames + "\n";
                if (line.contains("-----------")) line = line.replace("-", "");
                convertedTexts = convertedTexts + line + "\n";
            }
        }
        scanner.close();

        try {
            FileWriter writeDialogue = new FileWriter("E:\\Names.txt");
            writeDialogue.write(convertedTexts);
            writeDialogue.close();

            FileWriter writeDialogue2 = new FileWriter("E:\\Texts.txt");
            writeDialogue2.write(convertedNames);
            writeDialogue2.close();
            System.out.println("Dialogue successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}