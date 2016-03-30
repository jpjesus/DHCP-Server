/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidordhcp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus
 */
public class Servidor implements Runnable {
  DatagramSocket socket;
  InetAddress ipMac;
  String ipEsta;
  Calendar fechaf ;
  Calendar fechai ;
  Historial nuevo;
public static void main(String[] args)
    {
    
    Thread discoveryThread = new Thread(Servidor.getInstance());
    discoveryThread.start();
    }
  @Override
   public void run() {
        try {
            Direcciones conf= Archivos.LeerConfServidor();
                   int puerto = conf.getPuerto();
      //Mantengo el socket abierto para escuchar todo el trafico udp destinado para este puerto
      socket = new DatagramSocket(puerto, InetAddress.getByName("0.0.0.0"));
      socket.setBroadcast(true);
      while (true) {
            System.out.println(getClass().getName() + ">>>Recibiendo paquetes!");
 
            
              //Recibo el paquete
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
              // System.out.println(getClass().getName() + ">>>Paquete recibido; data:     " + new String(packet.getData()));

        //Veo si el paquete tiene el comando correcto
                 String message = new String(packet.getData()).trim();
                 if (message.equals("DISCOVER"))
                 {
                     
                 System.out.println(getClass().getName() + ">>>Peticion Discovery");
                 byte[] sendData = "OFFER".getBytes();
                 
                 long ipi=host2long(conf.rangoInicial);
                 long ipf=host2long(conf.rangoFinal);
                 HistList hist =new HistList();
                hist = Archivos.LeerHistorial(hist);
            
                 if(hist.size()!=0)
                 {
                 int listsize=hist.size();
               //  Historial nuevo;
                 int cont =0;
                 int ultimo=0;
                 for (long i=ipi; i<=ipf; i++) 
                 {                   
                   ipEsta=long2dotted(i);
                    if(cont==listsize)
                    {
                        break;
                    }
                    for(int j=0;j < listsize;j++)
                    {
                        
                        if(hist.get(j).getIp().equals(ipEsta))
                        {
                            if(hist.get(j).getIp().equals(conf.getRangoFinal()))
                            
                            {
                            
                                 ultimo ++;
                            
                            }
                            cont++;
                            break;
                        }
                        
                    }
                 }
                 
                         
                     
                   
                 
                 
               if(cont==0 || ultimo !=0)
               {
                   
               ipEsta="No hay direcciones ip disponibles";
               
               } 
                 }
          else
                 {
                 
                 
                 ipEsta=conf.rangoInicial;
                 
                 }
          //Mandar respuesta
          DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
          socket.send(sendPacket);
         
          byte[] sendData2=ipEsta.getBytes();
           DatagramPacket sendPacket2= new DatagramPacket(sendData2,sendData2.length,packet.getAddress(),packet.getPort());
           socket.send(sendPacket2);
          System.out.println(getClass().getName() + ">>>Direccion IP asignada para el cliente: ---->   " +ipEsta );
          System.out.println(getClass().getName() + ">>>Mascara: -------->" + conf.getMascara() );
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           ObjectOutputStream oos = new ObjectOutputStream(baos);
           oos.writeObject(conf);
           byte[]confdata= baos.toByteArray();
           DatagramPacket sendPacket3= new DatagramPacket(confdata,confdata.length,packet.getAddress(),packet.getPort());
           socket.send(sendPacket3);
           System.out.println(getClass().getName() + ">>> Oferta mandada el cliente ");
        }
        
        if (message.equals("REQUEST"))
                {
                //AQUI VA LA PARTE DE  INSERTAR EN XML O TXT LA DIRECCION IP DEL CLIENTE CON SU MAC Y LEASE TIME
                    System.out.println(getClass().getName() + ">>> Paquete Request recibido ");
                    fechaf = Calendar.getInstance();
                    fechaf.add(Calendar.SECOND,conf.tiempo);
                    fechai = Calendar.getInstance();
                    String addressMac="08:00:27:c7:59:43";
                    nuevo = new Historial(ipEsta,fechai,fechaf,addressMac);
                    Archivos.addHistorial(nuevo);
                    byte[] sendData = "ACKNOWLEDGE".getBytes();
                    DatagramPacket sendPacket2= new DatagramPacket(sendData,sendData.length,packet.getAddress(),packet.getPort());
                    socket.send(sendPacket2); 
                 System.out.println(getClass().getName() + ">>> Asignacion al cliente completada");
              
                }
      }
    } 
        catch (IOException ex) {
      Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
          Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
      }
  }

  public static Servidor getInstance() {
    return DiscoveryThreadHolder.INSTANCE;
  }
public static String getMask() throws SocketException, UnknownHostException{
String mascara ="";
InetAddress localHost = Inet4Address.getLocalHost();
NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
short numMask =networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();
String nMask=Short.toString(numMask);
  if ("24".equals(nMask))
  {
   mascara= "255.255.255.0";
  }
   if ("16".equals(nMask))
  {
   mascara= "255.255.0.0";
  }
    if ("8".equals(nMask))
  {
   mascara= "255.0.0.0";
  }
  return mascara;

}

    void liberarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  private static class DiscoveryThreadHolder {

    private static final Servidor INSTANCE = new Servidor();
  }  
 public  String getMacAddress () throws SocketException, UnknownHostException
 {
        ipMac=InetAddress.getLocalHost();
        
        NetworkInterface network = NetworkInterface.getByInetAddress(ipMac);
        byte[] mac = network.getHardwareAddress();
       StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
        }
        
        String macAdress = sb.toString();
        
     return macAdress;
 
 }
//Obtener rango de IP dado la inicial y el ip final de este rango
   public static long host2long(String host) {
        long ip=0;
        if (!Character.isDigit(host.charAt(0))) return -1;
        int[] addr = ip2intarray(host);
        if (addr == null) return -1;
        for (int i=0;i<addr.length;++i) {
            ip += ((long)(addr[i]>=0 ? addr[i] : 0)) << 8*(3-i);
        }
        return ip;
    }
 public static int[] ip2intarray(String host) {
        int[] address = {-1,-1,-1,-1};
        int i=0;
        StringTokenizer tokens = new StringTokenizer(host,".");
        if (tokens.countTokens() > 4) return null;
        while (tokens.hasMoreTokens()) {
            try {
                address[i++] = Integer.parseInt(tokens.nextToken()) & 0xFF;
            } catch(NumberFormatException nfe) {
                return null;
            }
        }
        return address;
    }
    public static String long2dotted(long address) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0, shift = 24; i < 4; i++, shift -= 8) {
            long value = (address >> shift) & 0xff;
            sb.append(value);
            if (i != 3) {
                sb.append('.');
            }
        }
        return sb.toString();
    }
public static long ip2long(InetAddress ip) {
        long l=0;
        byte[] addr = ip.getAddress();
        if (addr.length == 4) { //IPV4
            for (int i=0;i<4;++i) {
                l += (((long)addr[i] &0xFF) << 8*(3-i));
            }
        } else { //IPV6
            return 0;  // I dont know how to deal with these
        }
        return l;
    }

}
