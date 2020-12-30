/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.UI;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.freshworks.GlobalDecalarations;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author WhysoseriousONI
 */
class Key_time {

    String key;
    long time;
    String id;

    public Key_time(String key, long time, String id) {
        this.key = key;
        this.time = time;
        this.id = id;
    }

    public Key_time(String superKey) {
        String arr[] = superKey.split("---");
        this.key = arr[0];
        this.time = Long.parseLong(arr[1]);
        this.id = superKey;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;

        }
        if (!(obj instanceof Key_time)) {
            return false;
        }

        Key_time o = (Key_time) obj;
        return o.key.equals(this.key);
    }

    @Override
    public int hashCode() {
        return new String(key).hashCode() * 31;
    }

    @Override
    public String toString() {
        return this.key + "---" + this.time + "\n";
    }
}

class supportClass implements Runnable {

    public static String clientID, fileName, location, id;
//    public static ThreadGrouper tg;

    public supportClass(String clientID, String fileName, String location, String id) {
        this.clientID = clientID;
        this.fileName = fileName;
        this.location = location;
        this.id = id;

    }

    @Override
    public void run() {

        CommonDataStoreAccess cdsa = new CommonDataStoreAccess();
        cdsa.jLabel6.setText(id);
        cdsa.jLabel8.setText(clientID);
        cdsa.jLabel10.setText(fileName);
        cdsa.jLabel11.setText(location);
        cdsa.setVisible(true);
    }

}

public class ThreadGrouper {

    public static ThreadGroup group;
    public static String fileName, location;
    public static String clientID, th;
    public static ThreadGrouper tg = null;//(jLabel3.getText(), file, data.get("location").getAsString())
    public static Gson gson = new Gson();
    public static Calendar cal = Calendar.getInstance();
    public static Scanner ss = new Scanner(System.in);
    public static List<String> keys = null;
    public static JsonObject main = null;
//    public static ConcurrentHashMap<Key_time, JsonObject> cmap = null;

    public ThreadGrouper(String belongsTO, String fileName, String location) {
        group = new ThreadGroup(belongsTO);
        ThreadGrouper.fileName = fileName;
        ThreadGrouper.location = location;
        ThreadGrouper.clientID = belongsTO;
        setFileAndVariables();
//        cmap=new ConcurrentHashMap<>();
    }

    static void setFileAndVariables() {
        parse();
        keys = new ArrayList<>(main.keySet());
        keys = Collections.synchronizedList(new ArrayList<>(main.keySet()));
    }
//        setKeyData();

//    public static synchronized void setKeyData() {
//        for (int i = 0; i < keys.size(); i++) {
//            String k = keys.get(i);
//            String arr[] = k.split("---");
////            cmap.put(new Key_time(arr[0], Long.parseLong(arr[1]), k), (JsonObject) main.get(keys.get(i)));
//
//        }
//    }

    public synchronized static JsonElement read(String key) {
        key = "key1";
        System.out.println("keys " + keys);
        Key_time tempKey = findKey(key, cal.getTimeInMillis());
//        System.out.println("data from threadgrouper => " + gson.toJson(main.get(tempKey.id)));
        return main.get(tempKey.id);
    }

    public synchronized static void writeToFile() {
        String key = "needskey---0";
        JsonObject enterdata = new JsonObject();
        enterdata.addProperty("tempadd", "tempo value");
        main.add(key, enterdata);
//        cmap.put(new Key_time(key), enterdata);
        keys.add(key);
        try ( FileWriter fw = new FileWriter(ThreadGrouper.location)) {
            gson.toJson(main, fw);
            System.out.println("write successful");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Key_time findKey(String key, long time) {
        Key_time ret = null;
        System.out.println(keys);
//        System.out.println("");
        for (int i = 0; i < keys.size(); i++) {
            Key_time temp = new Key_time(keys.get(i).split("---")[0], Long.parseLong(keys.get(i).split("---")[1]), keys.get(i));
                System.out.println(temp.toString()+" "+key + " " + temp.key);
            if (temp.key.equals(key)) {
                if (temp.time >= time || temp.time == 0) {
                    ret = new Key_time(key, temp.time, temp.id);
                    return ret;
                } else if (temp.time < time) {
                    System.out.println("KEY EXPIRED");
                    ret = new Key_time(key, temp.time, temp.id);
                    System.out.println(ret);
                    System.out.println("delete called");
                    deleteKeyValue(ret);
                    System.out.println("null called due to time expiry");
                    return null;
                }
            }
        }
        System.out.println("null called due to no element");
        return null;
    }

    public synchronized static void deleteInFile(String key) {
        key = "needskey";
        Key_time tempKey = findKey(key, 0);
        System.out.println(keys);
        System.out.println(tempKey.toString());
        
        if (tempKey != null) {
            main.remove(tempKey.id);
//            cmap.remove(tempKey);
            keys.remove(tempKey.id);
            try ( FileWriter fw = new FileWriter(ThreadGrouper.location)) {
                gson.toJson(main, fw);
                System.out.println("delete successful");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public synchronized static void deleteKeyValue(Key_time tempKey) {

        main.remove(tempKey.id);
//        cmap.remove(tempKey);
        keys.remove(tempKey.id);
        try ( FileWriter fw = new FileWriter(ThreadGrouper.location)) {
            gson.toJson(main, fw);
            System.out.println("Delete successful");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static public void parse() {
        try ( FileReader reader = new FileReader(ThreadGrouper.location)) {
            JsonParser json = new JsonParser();
            main = (JsonObject) json.parse(reader);
        } catch (Exception e) {
            System.out.println("null called due to cant parse the file");
            System.out.println(e);
        }
    }

    static public void addThreadToGroup(String id) {
//        System.out.println("Thread added " + id);
        new Thread(ThreadGrouper.group, new supportClass(ThreadGrouper.clientID, ThreadGrouper.fileName, ThreadGrouper.location, id), id + Integer.toString(group.activeCount())).start();
        group.list();
//        System.out.println("thread count from class " + group.activeCount());
//        System.out.println("thread group count from class " + group.activeGroupCount());
    }

    public static ThreadGrouper getInstance(String gname, String fName, String loc) {
        if (tg == null) {
            tg = new ThreadGrouper(gname, fName, loc);
        }
        return tg;
    }

    public static void main(String[] args) throws InterruptedException {

//        ThreadGrouper tg = new ThreadGrouper("asd", "temp2.json", "C:\\Users\\WhysoseriousONI\\Documents\\NetBeansProjects\\Freshworks\\src\\main\\java\\FileSystem\\temp2.json");
//        ThreadGrouper tg = new ThreadGrouper("file1", "Name", "1");//("asd", "temp2.json", "C:\\Users\\WhysoseriousONI\\Documents\\NetBeansProjects\\Freshworks\\src\\main\\java\\FileSystem\\temp2.json");
//        tg.addThreadToGroup("1");
//        tg.addThreadToGroup("2");
//
//        System.out.println(tg.group.getName());
//        tg.group.list();
    }

}
