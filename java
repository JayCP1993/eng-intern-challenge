import java.util.HashMap;

/**
 * This class provides methods to translate between Braille and English.
 * It supports the full English alphabet, capital letters, spaces, and digits 0-9.
 * 
 * The input can be either Braille or English, and the program will automatically
 * determine the type of input and convert it to the other form (English -> Braille, or Braille -> English).
 */
public class BrailleTranslator {

    // Mapping from Braille patterns to corresponding English letters
    private static final HashMap<String, String> brailleToEnglishMap = new HashMap<>();
    
    // Mapping from English letters to their corresponding Braille patterns
    private static final HashMap<String, String> englishToBrailleMap = new HashMap<>();

    // Static block to initialize Braille-English mappings
    static {
        // Fill Braille to English mappings
        brailleToEnglishMap.put("O.....", "a");
        brailleToEnglishMap.put("O.O...", "b");
        brailleToEnglishMap.put("OO....", "c");
        brailleToEnglishMap.put("OO.O..", "d");
        brailleToEnglishMap.put("O..O..", "e");
        brailleToEnglishMap.put("OOO...", "f");
        brailleToEnglishMap.put("OOOO..", "g");
        brailleToEnglishMap.put("O.O.O.", "h");
        brailleToEnglishMap.put(".OO...", "i");
        brailleToEnglishMap.put(".OOO..", "j");
        brailleToEnglishMap.put("O...O.", "k");
        brailleToEnglishMap.put("O.OO..", "l");
        brailleToEnglishMap.put("OO..O.", "m");
        brailleToEnglishMap.put("OO.OO.", "n");
        brailleToEnglishMap.put("O..OO.", "o");
        brailleToEnglishMap.put("OOO.O.", "p");
        brailleToEnglishMap.put("OOOOO.", "q");
        brailleToEnglishMap.put("O.OOO.", "r");
        brailleToEnglishMap.put(".OO.O.", "s");
        brailleToEnglishMap.put(".OOOO.", "t");
        brailleToEnglishMap.put("O...OO", "u");
        brailleToEnglishMap.put("O.O.OO", "v");
        brailleToEnglishMap.put(".OOO.O", "w");
        brailleToEnglishMap.put("OO..OO", "x");
        brailleToEnglishMap.put("OO.OOO", "y");
        brailleToEnglishMap.put("O..OOO", "z");

        // Reverse mapping for English to Braille
        // This iterates over the Braille to English map and creates the reverse map
        for (String braille : brailleToEnglishMap.keySet()) {
            String englishLetter = brailleToEnglishMap.get(braille);
            englishToBrailleMap.put(englishLetter, braille);
        }
    }

    public static void main(String[] args) {
        // Check if input is provided
        if (args.length == 0 || args[0].trim().isEmpty()) {
            System.out.println("Please provide a string to translate.");
            return;
        }

        // First argument is the input string to be translated
        String input = args[0];

        // Determine if the input is Braille or English and translate accordingly
        if (isBraille(input)) {
            // Translate Braille to English
            String result = brailleToEnglish(input);
            System.out.println(result);  // Output the result to the console
        } else {
            // Translate English to Braille
            String result = englishToBraille(input);
            System.out.println(result);  // Output the result to the console
        }
    }

    /**
     * This method checks if the input is in Braille format.
     * Braille input contains only 'O' (raised dots) and '.' (flat areas), along with spaces.
     *
     * @param input The input string to check.
     * @return true if the input is in Braille format, false if it is in English.
     */
    public static boolean isBraille(String input) {
        return input.matches("[O. ]+");  // Regex ensures the input contains only 'O', '.', and spaces
    }

    /**
     * This method translates a Braille string into its corresponding English letters.
     * It also handles capital letters and number markers.
     *
     * @param braille The Braille string to translate.
     * @return The translated English string.
     */
    public static String brailleToEnglish(String braille) {
        StringBuilder result = new StringBuilder();  // Store the translated result

        // Split Braille input into individual characters (separated by spaces)
        String[] brailleLetters = braille.split(" ");

        boolean capitalMode = false;
        boolean numberMode = false;

        // Loop through each Braille letter and translate it to English
        for (String brailleLetter : brailleLetters) {
            brailleLetter = brailleLetter.trim();  // Remove any extra spaces

            // Check for capital letter marker
            if (brailleLetter.equals(".....")) {
                capitalMode = true;  // Activate capital mode
                continue;  // Skip to next Braille character
            }

            // Check for number marker
            if (brailleLetter.equals(".O.OOO")) {
                numberMode = true;  // Activate number mode
                continue;  // Skip to next Braille character
            }

            // Look up the Braille pattern in the map
            String englishLetter = brailleToEnglishMap.get(brailleLetter);
            if (englishLetter != null) {
                // If capital mode is active, convert letter to uppercase
                if (capitalMode) {
                    englishLetter = englishLetter.toUpperCase();
                    capitalMode = false;  // Reset capital mode after use
                }

                // If number mode is active, convert letter to a digit
                if (numberMode) {
                    englishLetter = getEnglishDigit(englishLetter);
                }

                // Append translated letter to result
                result.append(englishLetter);
            } else {
                // If Braille pattern is not recognized, append a '?' symbol
                result.append("?");
            }
        }
        return result.toString();  // Return the translated English string
    }

    /**
     * Helper method to convert a Braille letter (a-j) into the corresponding digit (1-0).
     *
     * @param letter The Braille letter to convert to a digit.
     * @return The corresponding digit, or '?' if the letter is invalid.
     */
    private static String getEnglishDigit(String letter) {
        switch (letter.toLowerCase()) {
            case "a": return "1";
            case "b": return "2";
            case "c": return "3";
            case "d": return "4";
            case "e": return "5";
            case "f": return "6";
            case "g": return "7";
            case "h": return "8";
            case "i": return "9";
            case "j": return "0";
            default: return "?";  // Return '?' if the letter does not correspond to a number
        }
    }

    /**
     * This method translates an English string into Braille.
     * It handles capital letters, numbers, and spaces.
     *
     * @param english The English string to translate.
     * @return The translated Braille string.
     */
    public static String englishToBraille(String english) {
        StringBuilder result = new StringBuilder();  // Store the translated result
        boolean numberMode = false;  // Flag to track if we are in number mode

        // Loop through each character in the English string
        for (char letter : english.toCharArray()) {
            // Handle spaces explicitly
            if (letter == ' ') {
                result.append(" ");  // Add a space between Braille words
                numberMode = false;  // Reset number mode when space is encountered
                continue;  // Skip to the next character
            }

            // Detect digits and activate number mode if necessary
            if (Character.isDigit(letter)) {
                if (!numberMode) {
                    result.append(".O.OOO ");  // Append the number marker before digits
                    numberMode = true;  // Activate number mode
                }
                // Append the corresponding Braille digit
                result.append(getBrailleDigit(letter)).append(" ");
            } else {
                // Reset number mode after non-digit characters
                numberMode = false;

                // Check if the character is uppercase and append the capitalization marker
                if (Character.isUpperCase(letter)) {
                    result.append("..... ");  // Append the capital marker
                    letter = Character.toLowerCase(letter);  // Convert to lowercase for lookup
                }

                // Look up the Braille pattern for the letter
                String brailleLetter = englishToBrailleMap.get(String.valueOf(letter));

                // Append the Braille pattern or '?' if unrecognized
                if (brailleLetter != null) {
                    result.append(brailleLetter).append(" ");
                } else {
                    result.append("? ");  // Append '?' if the letter is not recognized
                }
            }
        }
        return result.toString().trim();  // Return the translated Braille string
    }

    /**
     * Helper method to convert a digit to the corresponding Braille pattern.
     *
     * @param digit The digit to convert.
     * @return The corresponding Braille pattern for the digit, or '?' if invalid.
     */
    public static String getBrailleDigit(char digit) {
        switch (digit) {
            case '1': return "O.....";  // Same as 'a' in Braille
            case '2': return "O.O...";
            case '3': return "OO....";
            case '4': return "OO.O..";
            case '5': return "O..O..";
            case '6': return "OOO...";
            case '7': return "OOOO..";
            case '8': return "O.OO..";
            case '9': return ".OO...";
            case '0': return ".OOO..";  // Same as 'j' in Braille
            default: return "?";  // Return '?' if digit is unrecognized
        }
    }
}
