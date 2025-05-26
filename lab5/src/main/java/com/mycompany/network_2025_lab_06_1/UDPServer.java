/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.network_2025_lab_06_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samet
 */
public class UDPServer extends Thread {

    DatagramSocket socket;
    HashMap<String, SClient> clientList;

    public UDPServer(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        clientList = new HashMap<>();

    }

    public void Send(String msg) throws IOException {
        for (Map.Entry<String, SClient> entry : this.clientList.entrySet()) {
            SClient cl = entry.getValue();
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), cl.ip, cl.port);
            this.socket.send(packet);//blocking
        }
    }

    public void Listen() throws IOException {
        this.start();
    }

    @Override
    public void run() {
        try {
            while (true) {

                byte buffer[] = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, 1024);
                this.socket.receive(packet);//blocking

                clientList.put(packet.getAddress() + ":" + packet.getPort(),
                        new SClient(packet.getAddress(), packet.getPort()));
                System.out.println(packet.getAddress() + ":" + packet.getPort());
                System.out.println(new String(packet.getData()));

            }
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            UDPServer s1 = new UDPServer(52927);
            s1.Listen();
            Scanner scnr = new Scanner(System.in);
            while (true) {
                System.out.println("msg to all:");
                s1.Send(scnr.next());
            }

        } catch (SocketException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
