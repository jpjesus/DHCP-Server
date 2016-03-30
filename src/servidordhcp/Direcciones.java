/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidordhcp;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jesus
 */
public class Direcciones implements Serializable {

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public String getEnroutador() {
        return enroutador;
    }

    public void setEnroutador(String enroutador) {
        this.enroutador = enroutador;
    }

    public String getRangoInicial() {
        return rangoInicial;
    }

    public void setRangoInicial(String rangoInicial) {
        this.rangoInicial = rangoInicial;
    }

    public String getRangoFinal() {
        return rangoFinal;
    }

    public void setRangoFinal(String rangoFinal) {
        this.rangoFinal = rangoFinal;
    }
    
   String ip;
   String mascara;
   int puerto;
   String red;
   String protocolo;
   String dns;
   int tiempo;
   String enroutador;
   String rangoInicial;
   String rangoFinal;

       public Direcciones (String ip,String mac,int puerto ,String red,String protocolo)    
       {
       
       this.ip=ip;
       this.mascara=mac;
       this.puerto=puerto;
       this.red=red;
       this.protocolo=protocolo;
       
       
       }
       
       public Direcciones (String ip,String mac,int puerto ,String red,String protocolo,String dns,int tiempo,String enroutador,String rangoi,String rangof)    
       {
       
       this.ip=ip;
       this.mascara=mac;
       this.puerto=puerto;
       this.red=red;
       this.protocolo=protocolo;
       this.dns=dns;
       this.tiempo=tiempo;
       this.enroutador=enroutador;
       this.rangoInicial=rangoi;
       this.rangoFinal=rangof;
       
       }
       
       public Direcciones()
       {
       
       this.ip="";
       this.mascara="";
       this.puerto=0;
       this.red="";
       this.protocolo="";
       this.dns="";
       this.tiempo=0;
       this.enroutador="";
       this.rangoInicial= "";
       this.rangoFinal="";
       
       
       }
       
       
   

}
