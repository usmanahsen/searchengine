/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Bilal Khalid
 */
public class JavaApplication1 {

      /**
     * @param args the command line arguments
     */
    public static void SearchWord(Hashtable<String, ArrayList> v, String word) {
        if (v.containsKey(word)) {
            int n = v.get(word).size();
            for (int x = 0; x < n ; x++) {
                System.out.println("KeyWord Found on page " + v.get(word).get(x));
            }
        } else {
            System.out.println("KeyWord not Found ");
        }
    }

    public static void multiWord(Hashtable<String, ArrayList> v, String Text) {

        Text = Text.replaceAll("(?s)<ref(.+?)</ref>", " "); //remove references
        Text = Text.replaceAll("(?s)\\{\\{(.+?)\\}\\}", " "); //remove links underneath headings
        Text = Text.replaceAll("(?s)==See also==.+", " "); //remove everything after see also
        Text = Text.replaceAll("\\|", " "); //Separate multiple links
        Text = Text.replaceAll("\\n", " "); //remove new lines
        Text = Text.replaceAll("-", " ");
        Text = Text.replaceAll("[^a-zA-Z0-9- \\s]", " "); //remove all non alphanumeric except dashes and spaces
        Text = Text.replaceAll("\\s+", " ");
        String[] Splitted = Text.split("\\s");
        List<ArrayList<Integer>> a = new ArrayList<>();
        for(int i = 0 ; i < Splitted.length ; i++)
            a.add(v.get(i));
        for(int i = 1 ; i < a.size() ; i++)
            a.get(0).retainAll(a.get(i));
        System.out.println("i was here");
        int n = a.get(0).size();
        for(int i = 0 ; i < n ; i++)
            System.out.println(a.get(0).get(i));
    }

    public static void main(String[] args) {
        Hashtable<String, ArrayList> Hash = null;
        System.out.println("Please wait while the data is loaded......");
        try {

            FileInputStream fileIn = new FileInputStream("C:\\Users\\Bilal Khalid\\Desktop\\Reverse.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Hash = (Hashtable<String, ArrayList>) objectIn.readObject();

            objectIn.close();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        System.out.println("Enter a Word");
        Scanner input = new Scanner(System.in);
        String word = "";
        
        while (true) {
            word = input.nextLine();
            SearchWord(Hash, word);
            System.out.println("Enter a Word");
        }

    }

}
