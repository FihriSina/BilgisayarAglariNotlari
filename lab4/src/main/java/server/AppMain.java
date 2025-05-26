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
            Server s1 = new Server(6000);
            s1.StartAcceptance();

            //Server s2 = new Server(7000);
            //s2.StartAcceptance();
            while (true) {
                Scanner scnr = new Scanner(System.in);
                String msg = " "+ scnr.next();
                s1.SendBroadcastMsg(msg.getBytes());
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
