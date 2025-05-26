/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;



/**
 *
 * @author samet
 */
public class Message {
    public enum Type
    {
        NONE,
        CLIENTIDS,
        MSGFROMCLIENT,
        MSGFROMSERVER,
        TOCLIENT
    }
    
    public static String GenerateMsg(Message.Type type, String data)
    {
        //mtype#d1,d2,d3
        String gmsg= " "+type+"#"+data;
        return gmsg;
    }
    
  
            
    
}
