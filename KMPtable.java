import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

class KMPtable {
    // usage: java KMPtable <string to search for>
    // e.g. : java KMPtable xyxyz
    public static void main(String[] args) {
        
        String searchString = args[0]; // getting string to search for

        ArrayList<String> uniqueChars = getUniqueChars(searchString); // Getting all unique characters in pattern

        int[][] output = new int[uniqueChars.size()][searchString.length()]; // setting up array for output
        String currChar; // current character in pattern
        String compChar; // character in text to compare to
        int rowPointer = 0; // point to row (per text character)
        // Loop through unique characters
        for (int j=0; j<uniqueChars.size(); j++) {
            compChar = uniqueChars.get(j); // get latest unique char

            // Loop through pattern
            for (int i=0; i<searchString.length(); i++) {
                currChar = searchString.substring(i, i+1); // get current character

                if (compChar.equals(currChar)) { // if we're on track for a full match
                    output[rowPointer][i] = 0; // No shift necessary
                } else {
                    output[rowPointer][i] = getShift(compChar, searchString, i); // measure shift necessary
                }
            }
            rowPointer++; // increment row pointer
        }

        // Output KMP table to standard output
        try {
            // writer object
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            writer.write("  "); // write spacer characters for top row header
            for (int i=0; i<searchString.length(); i++) { // loop through pattern
                writer.write(searchString.substring(i, i+1)+" "); // writer each character spaced
            }
            writer.newLine(); // go to new line
            writer.flush(); // flush to output

            // loop through each unique characters shift on pattern
            for (int i=0; i<output.length; i++) {
                writer.write(uniqueChars.get(i)+" "); // get unique character for column "header"                
                // Loop through each shift value for each character
                for (int j=0; j<output[i].length; j++) {
                    writer.write(output[i][j]+" "); // output shift value spaced
                }
                writer.newLine(); // go to new line
                writer.flush(); // flush to output
            }
        } catch (IOException e) { // in case there is somehow an error
            
        }
    }

    // Gets all unique characters from pattern
    public static ArrayList<String> getUniqueChars(String source) {
        ArrayList<String> chars = new ArrayList<String>(); // arraylist to store unique characters

        // Loop through pattern
        for (char val : source.toCharArray()) {
            // if character is not in array yet 
            if (!chars.contains(String.valueOf(val))) {
                chars.add(String.valueOf(val)); // Add to array
            }
        }

        chars.add("*"); // add special character to mark all that does not appear in pattern

        return chars; // return array
    }

    // get the shift value for a character vs pattern
    public static int getShift(String compChar, String pattern, int pIndex) {
        String compString=""; // string of current compared values (equal to pattern excluding current position)
        for (int i=0; i<pIndex;i++) { // loop up to current index
            compString += pattern.substring(i, i+1); // recreate pattern up to that point
        }

        // index pointer, starts at index minus 1
        int pIndexPoint = pIndex-1;
        int shifts = 1; // at least 1 shift has to be made
        String pChar; // character from pattern

        try { // In case there are range errors
            while (true) { // loop until otherwise told to stop
                // get character from pattern
                pChar = pattern.substring(pIndexPoint, pIndexPoint+1);
                if (compChar.equals(pChar)) { // check if text character is equal
                    if (pIndex==1) { // check if it's the patter character at index 1
                        break; // end loop
                    } else if ((pIndex-pIndexPoint) > 1) { // not second character
                        
                        // get pattern character to the left of last one
                        pChar = pattern.substring(pIndexPoint-1, pIndexPoint);
                        String c = compString.substring(pIndex-1, pIndex); // get text character to the left of the original
                        if (c.equals(pChar)) { // check equality
                            break; // if equal then string match up to that point
                        }
                        // otherwise keep shifting
                    }
                }
                pIndexPoint--; // go backwards through pattern
                shifts++; // increment number of shifts
            }
        } catch (Exception e) {
        }

        // return final number of shifts
        return shifts;
    }
}