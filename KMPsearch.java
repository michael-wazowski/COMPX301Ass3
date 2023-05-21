import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class KMPsearch {
    public static void main(String[] args){
        try{  
            File f = new File(args[0]);
            //  File f = new File("table.txt");
            Scanner kmpScanner =  new Scanner(f);
            String patternString = kmpScanner.nextLine();
            ArrayList<String> pattern =  new ArrayList<String>(Arrays.asList(patternString.split(" ")));
            pattern.removeAll(Arrays.asList("", null));


            // arraylist of arraylist<int> (or string), contains shift values
            ArrayList <String []> rowList = new ArrayList<>();
            // arraylist<string>, contains found chars. Used to index prev array list to get shift
            ArrayList <String> foundChars = new ArrayList<>();
            String [] row = new String [pattern.size()+1];

            while(kmpScanner.hasNextLine()) {
                row = kmpScanner.nextLine().split(" ");
                foundChars.add(row[0]);
                rowList.add(Arrays.copyOfRange(row,1,row.length));
            }

            kmpScanner.close();

            Scanner sc = new Scanner(new File(args[1])); // scanner to read through target source
     
            int foundCharIndex = 0; // holds index of mismatched char
            boolean matched = false; // whether string search has matched
            String inputStr; // string to hold each line from target source
            String[] shiftVals; // array to get shift vals for when it's necessary
            int tracker = 0; // tracks lines read
            int pointer; // points to place in pattern

            while (sc.hasNextLine() && !matched) { // read each line in source file
                tracker++; // inc tracker
                pointer = 0;
                inputStr = sc.nextLine().toLowerCase(); // read line from target file 
                for (int i = 0; i<inputStr.length(); i++) { // loop through characters of the line
                    if (pattern.get(pointer).equals(String.valueOf(inputStr.charAt(i)))) { // check that the pattern and source are equal
                        pointer++; // increment pointer to compare rest of pattern
                    } else {
                        foundCharIndex = foundChars.indexOf(String.valueOf(inputStr.charAt(i))); // get index in array of chars
                        if (foundCharIndex == -1) { // if char is not in array
                            foundCharIndex = foundChars.indexOf("*"); // index all non-pattern characters  
                        }
                        shiftVals = rowList.get(foundCharIndex); // get array of shift values for corresponding char
                        pointer -= Integer.parseInt(shiftVals[pointer]); // shift pointer
                        if (pointer < 0) { // if pointer is less than 0
                            pointer = 0; // fix to 0
                        }
                    }
                    if (pointer == pattern.size()) { // if pointer is pattern size, we have a match
                        // output success
                        System.out.println(tracker + " " + ((i+2)-pattern.size())); // output line and column number
                        matched=true; // set matched to true
                        break; // break from for loop
                    }
                }
            }
            sc.close(); //close file scanner
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
