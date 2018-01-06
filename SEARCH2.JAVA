/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmaker;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Project {
    
   
    public static void main(String[] args) {
        Reverse Reverse = new Reverse();
        title title = new title();
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        Hashtable<Integer,Integer> Hash  = (Hashtable<Integer,Integer>) Reverse.Hash.get(s);
        ArrayList<Integer> titleList = (ArrayList<Integer>) title.Hash.get(s);
        System.out.println("Keyword found in title");
        for(int i = 0 ; i < titleList.size();i++){
            System.out.println("in title of page "+ titleList.get(i));
        }
        for(int key : Hash.keySet()){
            System.out.println("Keywords found on page "+Hash.get(key));
            
        }

    }
}
class title{
     DB db;
   
     ConcurrentMap Hash ;
    
    title(){
     db =  DBMaker.fileDB("title.db").make();
     Hash = db.hashMap("map").createOrOpen();
    }
    public void close(){
    db.close();
    }
}
class Reverse{
  DB db;
   
    ConcurrentMap Hash ;
    
    Reverse(){
     db =  DBMaker.fileDB("Reverse.db").make();
     Hash = db.hashMap("map").createOrOpen();
    }
    public void close(){
    db.close();
    }

}
