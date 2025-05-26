/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samet
 */
public class Server extends Thread {
    int clientId;
    ServerSocket ssocket;
    ArrayList<SClient> clients;

    public Server(int port) throws IOException {
        this.clientId=0;
        this.ssocket = new ServerSocket(port);
        this.clients = new ArrayList<>();
    }

    public void StartAcceptance() throws IOException {
        //Socket csocket = this.ssocket.accept();//blocking
        this.start();

    }
    
    public void SendConnectedClientIdsToAll() throws IOException
    {
        String data="";
        for (SClient client : clients) {
            data+=client.id+",";
        }
        
        String msg= Message.GenerateMsg(Message.Type.CLIENTIDS, data);
        this.SendBroadcastMsg(msg.getBytes());
    }
    
    public void SendMessageToClinet(int id, String msg) throws IOException
    {
        for (SClient client : clients) {
            if (client.id==id) {
                String rmsg=Message.GenerateMsg(Message.Type.MSGFROMCLIENT, msg);
                client.SendMessage(rmsg.getBytes());
                break;
            }
        }
    }

    public void SendBroadcastMsg(byte [] bmsg) throws IOException {
        for (SClient client : clients) {
            client.SendMessage(bmsg);
        }
    }

    @Override
    public void run() {

        try {
            while (!this.ssocket.isClosed()) {
                Socket csocket = this.ssocket.accept();//blocking
                SClient newClient = new SClient(csocket, this);
                newClient.StartListening();
                this.clients.add(newClient);
                this.SendConnectedClientIdsToAll();
                
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
