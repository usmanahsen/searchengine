/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
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

/**
 *
 * @author Bilal Khalid
 */
public class JavaApplication1 {

      /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
           try {
            File inputFile = new File("C:\\Users\\Bilal Khalid\\Desktop\\SimpleWiki\\SIMPLEWIKI.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
 

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
class DBClass{
     DB db;
   
     ConcurrentMap Hash ;
    
    DBClass(){
     db =  DBMaker.fileDB("Forward.db").make();
     Hash = db.hashMap("map").createOrOpen();
    }
    public void close(){
    db.close();
    }
}

class UserHandler extends DefaultHandler {
    DBClass DBClass = new DBClass();
    Reverse Reverse = new Reverse();
    title title = new title();
    int id = 0;
    boolean ID = false;
    int PageId = 0;
    @Override
    public void startElement(
            String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("id")) {
            ID = true;
            id++;
        }
    }

public int frequency = 0;
    @Override
    public void endElement(String uri,
            String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("page")) {
            System.out.println(PageId);
            if(PageId>50000)
            {
               DBClass.close();
               Reverse.close();
               title.close();
               System.exit(0);
            }
            
           Hashtable<String, ArrayList> Hash = (Hashtable<String, ArrayList>) DBClass.Hash.get(PageId);
            
            for (String key : Hash.keySet()) {
                frequency = Hash.get(key).size();
                if(!Reverse.Hash.containsKey(key)){
                    Hashtable<Integer, Integer> fmap= new Hashtable<>();
                    fmap.put( PageId,frequency);
                    Reverse.Hash.put(key, fmap);
                
                }else{
                    Hashtable<Integer, Integer> fmap =   (Hashtable<Integer, Integer>) Reverse.Hash.get(key);
                    fmap.put(PageId,frequency);
                    Reverse.Hash.put(key, fmap);
                }
            
            }
            PageId*=(-1);
            String[] Splitted = ((String)DBClass.Hash.get(PageId)).split("\\s");
            PageId*=(-1);
            for(int i = 0 ; i < Splitted.length; i++){
                if(!title.Hash.containsKey(Splitted[i])){
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(PageId);
                    title.Hash.put(Splitted[i], temp);
                }else{
                 ArrayList<Integer> temp = (ArrayList<Integer>) title.Hash.get(Splitted[i]);
                 if(!temp.contains(PageId)){   
                 temp.add(PageId);
                    title.Hash.put(Splitted[i], temp);
                 }
                }
            
            }
            
            
        } else if (qName.equalsIgnoreCase("text")) {
            
            id = 0;

        }
        
    }
    
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (id == 1 && ID) {
            ID = false;
            PageId = (int) Double.parseDouble(new String(ch, start, length));
        }
    }
    @Override
    public void endDocument(){
       DBClass.close();
       Reverse.close();
       title.close();
    }
}
