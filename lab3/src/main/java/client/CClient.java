/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.SClient;

/**
 *
 * @author samet
 */
public class CClient extends Thread {

    Socket csocket;
    OutputStream coutput;
    InputStream cinput;

    public CClient(String ip, int port) throws IOException {
        this.csocket = new Socket(ip, port);
        this.coutput = csocket.getOutputStream();
        this.cinput = csocket.getInputStream();
    }

    public void MsgParser(String msg) {
        try {

            String tokens[] = msg.split("#");

            Message.Type mt = Message.Type.valueOf(tokens[0]);
            switch (mt) {
                case Message.Type.CLIENTIDS:
                    String datas[] = tokens[1].split(",");
                    MainFrm.lst_clientIds_model.removeAllElements();
                    for (String data : datas) {
                        MainFrm.lst_clientIds_model.addElement(data);
                    }

                    break;

                case Message.Type.MSGFROMCLIENT:
                    MainFrm.lst_msgs_model.addElement(tokens[1]);

                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void SendMessage(String msg) throws IOException {

        this.coutput.write(msg.getBytes());
    }

    public void Listen() throws IOException {
        this.start();
    }

    public void run() {
        try {
            while (!this.csocket.isClosed()) {
                int bsize = this.cinput.read();
                byte buffer[] = new byte[bsize];
                this.cinput.read(buffer);
                String rsMsg = new String(buffer);
                this.MsgParser(rsMsg);
                //System.out.println(rsMsg);
                //MainFrm.lst_msgs_model.addElement(rsMsg);

            }
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
