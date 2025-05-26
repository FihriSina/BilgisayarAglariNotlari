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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samet
 */
public class UDPClient {

    DatagramSocket socket;

    public UDPClient() throws SocketException {
        this.socket = new DatagramSocket();

    }

    public void Send(String msg) throws IOException {
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName("localhost"), 5000);
        this.socket.send(packet);//blocking
    }

    public void Listen() throws IOException {
        while (true) {
            byte buffer[] = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, 1024);
            this.socket.receive(packet);//blocking
            System.out.println(packet.getAddress() + ":" + packet.getPort());
            System.out.println(new String(packet.getData()));
        }
    }

    public static void main(String[] args) {
        try {
            UDPClient c1 = new UDPClient();
            c1.Send("merhaba");
            c1.Listen();

        } catch (SocketException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
