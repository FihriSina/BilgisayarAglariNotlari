/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samet
 */
public class SClient extends Thread {

    int id;
    Socket csocket;
    OutputStream coutput;
    InputStream cinput;
    Server ownerServer;

    public SClient(Socket connectedSocket, Server server) throws IOException {
        this.csocket = connectedSocket;
        this.coutput = this.csocket.getOutputStream();
        this.cinput = this.csocket.getInputStream();
        this.ownerServer = server;
        this.id = server.clientId;
        server.clientId++;
    }

    public void MsgParser(String msg) throws IOException {
        String tokens[] = msg.split("#");
        Message.Type mt = Message.Type.valueOf(tokens[0]);
        switch (mt.ordinal()) {
            case 1:

                break;
            case 4:
                String datas[]= tokens[1].split(",");
                int id= Integer.parseInt(datas[0]);
                this.ownerServer.SendMessageToClinet(id, datas[1]);
                break;

            default:
                throw new AssertionError();
        }

    }

    public void StartListening() {
        this.start();
    }

    public void run() {
        try {
            while (!this.csocket.isClosed()) {
                System.out.println("Log: Server listening client:"+this.csocket.getInetAddress()+":"+this.csocket.getPort());    
                int bsize = this.cinput.read();
                byte buffer[] = new byte[bsize];
                this.cinput.read(buffer);
                String rsMsg = new String(buffer);
                this.MsgParser(rsMsg);
            }
        } catch (IOException ex) {
            this.ownerServer.clients.remove(this);
            try {
                this.ownerServer.SendConnectedClientIdsToAll();
                //Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex1) {
                Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }

    public void SendMessage(byte[] msg) throws IOException {

        this.coutput.write(msg);
    }

}
