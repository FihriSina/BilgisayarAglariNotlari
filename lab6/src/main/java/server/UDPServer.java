/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
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
    int messageId = 0;

    public void Send(String data) throws IOException {

        int totalChunk = (int) Math.ceil((double) data.length() / 5); // Toplam paket sayısı
        for (int chunkNo = 0; chunkNo < totalChunk; chunkNo++) {
            // Paket sıra numarası ve toplam paket sayısını header olarak ekle
            String header = messageId + ":" + chunkNo + ":" + totalChunk + ":";
            String chunk = data.substring(chunkNo * 5, Math.min((chunkNo + 1) * 5, data.length()));
            String packetData = header + chunk;

            byte[] buffer = packetData.getBytes();
            //send chunk to all client
            for (Map.Entry<String, SClient> entry : this.clientList.entrySet()) {
                SClient cl = entry.getValue();
                
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, cl.ip, cl.port);
                this.socket.send(packet);//blocking
            }

        }
        messageId++;
    }

    public void Listen() throws IOException {
        this.start();
    }

    @Override
    public void run() {
        try {

            int totalChunks = -1; // Toplam paket sayısı
            int messageId = -1; // Mesaj numarası
            TreeMap<Integer, String> receivedPackets = new TreeMap<>(); // Paketleri sıralı tutmak için
            while (true) {
                byte buffer[] = new byte[1024];
                
                DatagramPacket receivePacket = new DatagramPacket(buffer, 1024);
                this.socket.receive(receivePacket);
                
                
                clientList.put(receivePacket.getAddress() + ":" + receivePacket.getPort(),
                        new SClient(receivePacket.getAddress(), receivePacket.getPort()));
                
                String packetData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Alınan paket: " + packetData);
                // Header'ı ayır
                String[] parts = packetData.split(":", 4); // Header 3 parçadan oluşur
                int receivedMessageId = Integer.parseInt(parts[0]);
                int chunkNumber = Integer.parseInt(parts[1]);
                totalChunks = Integer.parseInt(parts[2]);
                String chunk = parts[3];

                // Mesaj numarasını kontrol et
                if (messageId == -1) {
                    messageId = receivedMessageId; // İlk mesaj numarasını al
                } else if (messageId != receivedMessageId) {
                    System.out.println("Farklı bir mesaj numarası alındı. Yoksayılıyor.");
                    messageId = receivedMessageId;
                    receivedPackets.clear();

                }

                // Paketi kaydet
                receivedPackets.put(chunkNumber, chunk);

                // Tüm paketler alındıysa döngüyü kır
                if (receivedPackets.size() == totalChunks) {
                    // Mesajı birleştir
                    String fullMessage = "";
                    for (String rchunk : receivedPackets.values()) {
                        fullMessage += rchunk;
                    }
                    System.out.println("Tam mesaj: " + fullMessage);
                    this.Send("server->"+fullMessage);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            UDPServer s1 = new UDPServer(5000);
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
