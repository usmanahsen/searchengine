/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Project {

    public static Hashtable<String, ArrayList> Hash = new Hashtable<>();

    public static void main(String[] args) {

        try {
            File inputFile = new File("C:\\Users\\Touseef Ahmad\\Desktop\\SimpleWiki\\yes.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Enumeration e = Hash.elements();

        while (e.hasMoreElements()) {
            System.out.println(e.nextElement());
        }

        // Store the Forward index variable
        try {
            FileOutputStream fileOut = new FileOutputStream("Reverse.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Hash);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void add(String[] title, int page,boolean t) {
        for (int x = 0; x < title.length; x++) {
            if (!Hash.containsKey(title[x])) {
                Hash.put(title[x], new ArrayList<Integer>());
                Hash.get(title[x]).add(0,page);
            } else {
                if (!Hash.get(title[x]).contains(page)) {
                    Hash.get(title[x]).add(0,page);
                }
            }

        }
    }
    
    
    public static void add(String[] title, int page) {
        for (int x = 0; x < title.length; x++) {
            if (!Hash.containsKey(title[x])) {
                Hash.put(title[x], new ArrayList<Integer>());
                Hash.get(title[x]).add(page);
            } else {
                if (!Hash.get(title[x]).contains(page)) {
                    Hash.get(title[x]).add(page);
                }
            }

        }
    }
}

class UserHandler extends DefaultHandler {

    int id = 0;
    boolean ID = false;
    int PageId = 0;
    String Text = "";
    boolean Title = false;

    String title = "";

    boolean text = false;

    @Override
    public void startElement(
            String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("title")) {
            Title = true;
        } else if (qName.equalsIgnoreCase("text")) {
            text = true;
        } else if (qName.equalsIgnoreCase("id")) {
            ID = true;
            id++;
        }
    }

    public void print(String[] t) {
        for (int x = 0; x < t.length; x++) {
            System.out.println(t[x]);
        }
    }

    @Override
    public void endElement(String uri,
            String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("page")) {
            System.out.println(PageId);
            Text = Text.replaceAll("(?s)<ref(.+?)</ref>", " "); //remove references
            Text = Text.replaceAll("(?s)\\{\\{(.+?)\\}\\}", " "); //remove links underneath headings
            Text = Text.replaceAll("(?s)==See also==.+", " "); //remove everything after see also
            Text = Text.replaceAll("\\|", " "); //Separate multiple links
            Text = Text.replaceAll("\\n", " "); //remove new lines
            Text = Text.replaceAll("-", " ");
            Text = Text.replaceAll("[^a-zA-Z0-9- \\s]", " "); //remove all non alphanumeric except dashes and spaces
            Text = Text.replaceAll("\\s+", " ");
            title = title.replaceAll("(?s)<ref(.+?)</ref>", " "); //remove references
            title = title.replaceAll("(?s)\\{\\{(.+?)\\}\\}", " "); //remove links underneath headings
            title = title.replaceAll("(?s)==See also==.+", " "); //remove everything after see also
            title = title.replaceAll("\\|", " "); //Separate multiple links
            title = title.replaceAll("\\n", " "); //remove new lines
            title = title.replaceAll("-", " ");
            title = title.replaceAll("[^a-zA-Z0-9- \\s]", " "); //remove all non alphanumeric except dashes and spaces
            title = title.replaceAll("\\s+", " ");
            String[] SplittedText = Text.split("\\s");
            String[] Splittedtitle = title.split("\\s");
            Project.add(SplittedText, PageId);
            Project.add(Splittedtitle, PageId,true);
            Text = "";
        } else if (qName.equalsIgnoreCase("text")) {
            text = false;
            id = 0;

        }

    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (Title) {
            title = new String(ch, start, length);
            Title = false;
        } else if (text) {
            Text += new String(ch, start, length);
        } else if (id == 1 && ID) {
            ID = false;
            PageId = (int) Double.parseDouble(new String(ch, start, length));
        }
    }
}
