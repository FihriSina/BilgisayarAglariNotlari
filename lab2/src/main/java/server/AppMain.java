/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samet
 */
public class AppMain {
    public static void main(String[] args) {
        try {
            Server s1= new Server(5000);
            s1.Start();
            Scanner scnr= new Scanner(System.in);
            while(true)
            {
                String msg= scnr.next();
                s1.SentMsgAll(msg.getBytes());
            }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
