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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samet
 */
public class Server extends Thread {
  //public class Server implements Runnable {  

    ServerSocket server;
    ArrayList<SClient> clients;
    public Server(int port) throws IOException
    {
        this.server = new ServerSocket(port);
        this.clients= new ArrayList<>();
    }
    public void Start() {
        
        this.start();
    }
    
    public void SentMsgAll(byte[] msg) throws IOException
    {
        for (SClient client : clients) {
            client.SendMsg(msg);
        }
    }
    

    @Override
    public void run()
    {
        try {
            while(!this.server.isClosed())
            {
                Socket acceptedSoket= this.server.accept();
                SClient nclient= new SClient(acceptedSoket,this);
                this.clients.add(nclient);
                nclient.Listen();
                
                nclient.SendMsg(" welcome".getBytes());
                
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
 
}
