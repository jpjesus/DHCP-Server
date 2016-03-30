/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidordhcp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Jesus
 */
public class Historial implements Serializable{
   String ip;
   Calendar fechaInicio;
   Calendar fechaFin;
   String mac;
   String fi;
   String ff;

    public Historial(String ip, String mac, String fi, String ff) {
        this.ip = ip;
        this.mac = mac;
        this.fi = fi;
        this.ff = ff;
    }

    public String getFi() {
        return fi;
    }

    public void setFi(String fi) {
        this.fi = fi;
    }

    public String getFf() {
        return ff;
    }

    public void setFf(String ff) {
        this.ff = ff;
    }
    

    public HistList getLista() {
        return lista;
    }

    public void setLista(HistList lista) {
        this.lista = lista;
    }
   HistList lista;
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
   

    public Historial(String ip, Calendar fechaInicio, Calendar fechaFin) {
        this.ip = ip;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
         this.lista        = new HistList();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Calendar getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Calendar getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }
    
  
    public Historial(String ip, Calendar fechaInicio, Calendar fechaFin, String mac) {
        this.ip = ip;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.mac = mac;
         this.lista        = new HistList();
        
    }

    private static class fi {

        public fi() {
        }
    }
   
}
