/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.UI;

import java.io.File;

/**
 *
 * @author WhysoseriousONI
 */
public class ThreadGrouper implements Runnable {

    ThreadGroup group;

    public ThreadGrouper(String belongsTO) {
        group = new ThreadGroup(belongsTO);
    }

    @Override
    public void run() {
        CommonDataStoreAccess cdsa=new CommonDataStoreAccess();
    }
    
    public void addThreadToGroup(String threadName,String id, String fileName, String location,) {
        new Thread(group, () -> {
            System.out.println(threadName);
        }, threadName).start();

//        new Thread(group,,threadName);//middle add runnable 
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadGrouper tg = new ThreadGrouper("asd");
        tg.addThreadToGroup("hello");
        tg.addThreadToGroup("second");

        System.out.println(tg.group.getName());
        tg.group.list();
    }

}
