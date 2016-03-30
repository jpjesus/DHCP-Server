/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidordhcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesus
 */
public class ServidorDchp {
   BufferedReader in = null;   
String str = null;
byte[] buffer;
DatagramPacket packet;
InetAddress address;
int port;
DatagramSocket socket;

public ServidorDchp()  throws IOException {
System.out.println("Mandando mesanjes");
socket = new DatagramSocket();
transmit();
}

public void transmit() {

    try{
    
    in= new BufferedReader(new InputStreamReader(System.in));
    while (true)
    {
    System.out.println("RECIBIENDO MENSAJE");
    str =in.readLine();
    buffer=str.getBytes();
    address=InetAddress.getByName("255.255.255.255");
    packet = new DatagramPacket(buffer,buffer.length,address,1502);
    socket.send(packet);
    
    }
    }
     catch (Exception e){
   
   System.out.println("Error");
    
    }



}

}
