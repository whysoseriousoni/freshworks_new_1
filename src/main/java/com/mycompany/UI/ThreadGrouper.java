/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.UI;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WhysoseriousONI
 */
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

    public ThreadGrouper(String belongsTO, String fileName, String location) {
        group = new ThreadGroup(belongsTO);
        this.fileName = fileName;
        this.location = location;
        this.clientID = belongsTO;
    }

    static public void addThreadToGroup(String id) {
        System.out.println("Thread added " + id);

        new Thread(ThreadGrouper.group, new supportClass(ThreadGrouper.clientID, ThreadGrouper.fileName, ThreadGrouper.location, id), id).start();
        group.list();
        System.out.println("thread count from class " + group.activeCount());
        System.out.println("thread group count from class " + group.activeGroupCount());
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
