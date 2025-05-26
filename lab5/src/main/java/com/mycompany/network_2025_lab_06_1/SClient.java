/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.network_2025_lab_06_1;

import java.net.InetAddress;

/**
 *
 * @author samet
 */
public class SClient {
    public static int clientId=0;
    InetAddress ip;
    int port;
    int id;
    public SClient(InetAddress ip, int port)
    {
        this.ip=ip;
        this.port= port;
        this.id=SClient.clientId;
        SClient.clientId++;
    }
}
