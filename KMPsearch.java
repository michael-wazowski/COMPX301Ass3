import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class KMPsearch {
    public static void main(String[] args){
        try{  
            int tracker =0; 
            File f = new File(args[0]);
            //  File f = new File("table.txt");
            Scanner kmpScanner =  new Scanner(f);
            ArrayList<String> pattern =  new ArrayList<String>(Arrays.asList(kmpScanner.nextLine().split(" ")));
            pattern.removeAll(Arrays.asList("", null));


            // arraylist of arraylist<int> (or string), contains shift values
            ArrayList <String []> rowList = new ArrayList<>();
            // arraylist<string>, contains found chars. Used to index prev array list to get shift
            ArrayList <String> foundChars = new ArrayList<>();
            String [] row = new String [pattern.size()+1];

            while(kmpScanner.hasNextLine()) {
                row = kmpScanner.nextLine().split(" ");
                foundChars.add(row[0]);
                rowList.add(row);
            }

            kmpScanner.close();
            //  Scanner sc = new Scanner(new File("rubbish.txt"));
            Scanner sc = new Scanner(new File(args[1]));
     
            int foundCharIndex =0;
            boolean matched = false;
            char [] inputStr;
            while(sc.hasNextLine() && !matched){ //read each line in the source file 
                tracker++;
                int pointer=0; //Points to where we are in the pattern
                inputStr = sc.nextLine().toCharArray(); //turn line into a char Array
                // System.out.println(Arrays.toString(inputStr));
                for (int i = 0; i < inputStr.length; i++) { //loop into the characters of the line
                    //check that pattern are source are equal 
                    if(pattern.get(pointer).equals(String.valueOf(inputStr[i]))){ //check if the character matches to the pattern
                        pointer++;
                        if(pointer ==1){
                            foundCharIndex = i+1;
                        }
                    }else{
                        int foundVal;
                        //need to know if the character is in found chars 
                        if(foundChars.contains(String.valueOf(inputStr[i]))){
                            foundVal = foundChars.indexOf(String.valueOf(inputStr[i]));
                        }else{
                            foundVal = foundChars.indexOf("*");
                        }
                        String[] valString = rowList.get(foundVal);
                        int shiftVal = Integer.valueOf(valString[pointer+1]);
                        i += shiftVal;
                    }
                    if(pointer == pattern.size()){
                        System.out.println(tracker + " "+foundCharIndex);
                        matched = true;
                        break;
                    }
                } 
            }
             sc.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
