import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class KMPsearch {
    public static void main(String[] args){
        try{  
            int tracker =0; 
            // File f = new File(args[0]);
             File f = new File("table.txt");
            Scanner kmpScanner =  new Scanner(f);
            ArrayList<String> pattern =  new ArrayList<String>(Arrays.asList(kmpScanner.nextLine().split(" ")));
            pattern.removeAll(Arrays.asList("", null));
            System.out.println(pattern);


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
            rowList.forEach(s -> System.out.println(Arrays.toString(s)));
            kmpScanner.close();
             Scanner sc = new Scanner(new File("rubbish.txt"));
            // Scanner sc = new Scanner(new File(args[1]));
            int pointer = 0; //Points to where we are in the pattern
            while(sc.hasNextLine()){ //read each line in the source file 
                tracker++;
                char [] inputStr = sc.nextLine().toCharArray(); //turn line into a char Array
                System.out.println(inputStr);
                for (int i = 0; i < inputStr.length; i++) { //loop into the characters of the line
                    System.out.println(inputStr[i]);
                    //check that pattern are source are equal 
                    if(pattern.get(pointer).equals(String.valueOf(inputStr[i]))){ //check if the character matches to the pattern
                        System.out.println("/////////////////////////////////////////////");
                        pointer++;
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
                        System.out.println(tracker);
                        pointer=0;
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
