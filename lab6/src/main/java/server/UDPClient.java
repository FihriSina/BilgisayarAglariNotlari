/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author samet
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samet
 */

public class UDPClient {

    DatagramSocket socket;

    public UDPClient() throws SocketException {
        socket = new DatagramSocket();
    }
    int messageId = 1; // Mesaj numarası

    public void Send(String data) throws IOException {

        int totalChunk = (int) Math.ceil((double) data.length() / 5); // Toplam paket sayısı
        for (int chunkNo = 0; chunkNo < totalChunk; chunkNo++) {
            // Paket sıra numarası ve toplam paket sayısını header olarak ekle
            String header = messageId + ":" + chunkNo+ ":" + totalChunk + ":";
            String dataChunk = data.substring(chunkNo * 5, Math.min((chunkNo + 1) * 5, data.length()));
            String packetData = header + dataChunk;

            byte[] buffer = packetData.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("ec2-13-60-93-179.eu-north-1.compute.amazonaws.com"), 5000);
            this.socket.send(packet);
            
        }
        messageId++;
        //System.out.println(new String(packet.getData()));
    }

    public void Listen() throws IOException {
        int totalChunks = -1; // Toplam paket sayısı
        int messageId = -1; // Mesaj numarası
        TreeMap<Integer, String> receivedChunks = new TreeMap<>(); // Paketleri sıralı tutmak için
        //chunkNo, ChunkData
        while (true) {
            byte buffer[] = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(buffer, 1024);
            this.socket.receive(receivePacket);//blocking 
            
            
            
            String packetData = new String(receivePacket.getData(), 0, receivePacket.getLength());
            //header+chunkData
            System.out.println("Received paket: " + packetData);
            // Header'ı ayır
            String[] parts = packetData.split(":", 4); // Header 3 parçadan oluşur
            int receivedMessageId = Integer.parseInt(parts[0]);
            int chunkNumber = Integer.parseInt(parts[1]);
            totalChunks = Integer.parseInt(parts[2]);
            String chunkData = parts[3];

            // Mesaj numarasını kontrol et
            if (messageId == -1) {
                messageId = receivedMessageId; // İlk mesaj numarasını al
            } else if (messageId != receivedMessageId) {
                System.out.println("Farklı bir mesaj numarası alındı. Yoksayılıyor.");
                messageId=receivedMessageId;
                receivedChunks.clear();
                
            }

            // Paketi kaydet
            receivedChunks.put(chunkNumber, chunkData);

            // Tüm paketler alındıysa döngüyü kır
            if (receivedChunks.size() == totalChunks) {
                // Mesajı birleştir
                String fullMessage ="";
                for (String rchunk : receivedChunks.values()) {
                    fullMessage+=rchunk;
                }
                System.out.println("Message: " + fullMessage);
            }
        }

    }

    public static void main(String[] args) {
        UDPClient c1;
        try {
            c1 = new UDPClient();
            c1.Send("merhaba");
            c1.Listen();
            //c1.Listen();
        } catch (SocketException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
