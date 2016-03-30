/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidordhcp;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jesus
 */
public class Archivos {
    
    // Tags configuracion
    public static final String CONF_TAG                 = "ser";
    public static final String CONF_PUERTO_TAG            = "puerto";
    public static final String CONF_PROTOCOLO_TAG            = "protocolo";
    public static final String CONF_IP_TAG        = "ip";
    public static final String CONF_RED_TAG  = "red";
    public static final String CONF_MASCARA_TAG       = "mascara";
    public static final String CONF_RANGOI_TAG            = "rangoi";
    public static final String CONF_RANGOF_TAG        = "rangof";
    public static final String CONF_ENROUTADOR_TAG              = "enroutador";
    public static final String CONF_DNS_TAG            = "dns";
    public static final String CONF_TIEMPO_TAG          = "tiempo"; 
    public static final String CONF_XML_PATH= "src/servidordhcp/servidorConf.xml";
    
   //Tags Historial
  
    public static final String HIST_XML_PATH= "src/servidordhcp/historialIp.xml";
    public static final String HIST_TAG                 = "hist";
    public static final String HIST_IP_TAG            = "ip";
    public static final String HIST_MAC_TAG        = "mac";
    public static final String HIST_FECHAINI_TAG            = "fecha_inicio";
    public static final String HIST_FECHAFIN_TAG            = "fecha_fin";
    
    
    
    
    
    //Errors leyendo xml
    public static final String ERROR_XML_EMPTY_FILE                 = "Error loading XML file - The file is empty";
    public static final String ERROR_XML_PROCESSING_FILE            = "Error loading XML file - It's not possible processing the file"; 
   
   public static Direcciones LeerConfServidor()
    {
        Document        doc;
        Element         root,child;
        List <Element>  rootChildrens;
        String          ip, mascara, status, protocolo,tiempo, dns, enroutador, red, puerto,rangoi,rangof;
        boolean         found = false;
        int             pos = 0;
        Direcciones     configuracion = null;
        SAXBuilder      builder = new SAXBuilder();

        try
        {
            doc = builder.build(CONF_XML_PATH);

            root = doc.getRootElement();

            rootChildrens = root.getChildren();

            while (!found && pos < rootChildrens.size())
            {
                child = rootChildrens.get(pos);

                puerto              = child.getAttributeValue( CONF_PUERTO_TAG );
                protocolo       =child.getAttributeValue(CONF_PROTOCOLO_TAG);
                ip          = child.getAttributeValue(CONF_IP_TAG);
              
                red   = child.getAttributeValue(CONF_RED_TAG );
                mascara         = child.getAttributeValue(CONF_MASCARA_TAG);
                rangoi        = child.getAttributeValue(CONF_RANGOI_TAG );
                rangof            = child.getAttributeValue(CONF_RANGOF_TAG );
                enroutador         = child.getAttributeValue(CONF_ENROUTADOR_TAG);
                dns                = child.getAttributeValue(CONF_DNS_TAG );
                tiempo            = child.getAttributeValue(CONF_TIEMPO_TAG);
             
                
                
                if(ip             != null   && protocolo !=null &&  
                   puerto         != null   &&   
                   enroutador            != null   &&
                   rangoi       != null   && 
                   rangof              != null   && 
                   mascara             != null   && 
                   tiempo        != null   &&   
                   dns        != null   )
                  
                {
                    found = true;
                    int puertof= Integer.parseInt(puerto);
                    int tiempof =Integer.parseInt(tiempo);
                    configuracion = new Direcciones(ip, mascara, puertof , red, protocolo, dns,tiempof,enroutador, rangoi,rangof);
                    return configuracion;
                    
                }
                
                pos++;
            }
        }
        catch(JDOMParseException e)
        {
            System.out.println(ERROR_XML_EMPTY_FILE);
            e.printStackTrace();
        }
        catch(JDOMException e)
        {
            System.out.println(ERROR_XML_PROCESSING_FILE);
            e.printStackTrace();
        }
        catch(IOException e)
        {
            System.out.println(ERROR_XML_PROCESSING_FILE);
            e.printStackTrace();
        }

        return configuracion;
    }
    
     public static boolean addHistorial (Historial hist)
     {
        Document    doc;
        Element     root, newChild;
        boolean res = false;
        SimpleDateFormat df= new SimpleDateFormat("dd/MM/yy HH:mm");
        String fi= df.format(hist.getFechaInicio().getTime());
        String ff = df.format(hist.getFechaFin().getTime());
        SAXBuilder  builder = new SAXBuilder();

        try
        {
            doc = builder.build(HIST_XML_PATH);

            root = doc.getRootElement();

            newChild = new Element(HIST_TAG );

            newChild.setAttribute(HIST_IP_TAG ,hist.getIp());
            newChild.setAttribute(HIST_MAC_TAG , hist.getMac());
            newChild.setAttribute(HIST_FECHAINI_TAG , fi);
            newChild.setAttribute(HIST_FECHAFIN_TAG , ff);
            root.addContent(newChild);
            
            res=true;

            try
            {
                Format format = Format.getPrettyFormat();
             
                XMLOutputter out = new XMLOutputter(format);

                FileOutputStream file = new FileOutputStream(HIST_XML_PATH);

                out.output(doc,file);

                file.flush();
                file.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch(JDOMParseException e)
        {
            System.out.println(ERROR_XML_EMPTY_FILE);
            e.printStackTrace();
        }
        catch(JDOMException e)
        {
            System.out.println(ERROR_XML_PROCESSING_FILE);
            e.printStackTrace();
        }
        catch(IOException e)
        {
            System.out.println(ERROR_XML_PROCESSING_FILE);
            e.printStackTrace();
        }

        return res;
     }
    
 
    public static HistList LeerHistorial(HistList lista) throws ParseException
    {
      
        Historial           list= null;
        Document        doc;
        Element         root,child;
        List <Element>  rootChildrens;
        String          ip, mac, fechai, fechaf;
        
        int             pos = 0;
      DateFormat df= new SimpleDateFormat("dd/MM/yy HH:mm");
      Calendar fei = Calendar.getInstance();
      Calendar fef = Calendar.getInstance();
    
        SAXBuilder      builder = new SAXBuilder();

        try
        {
            doc = builder.build(HIST_XML_PATH);
            root = doc.getRootElement();

            rootChildrens = root.getChildren();

            while (pos < rootChildrens.size())
            {
                child = rootChildrens.get(pos);

                ip       = child.getAttributeValue(HIST_IP_TAG);
                mac      = child.getAttributeValue(HIST_MAC_TAG);
                fechai       = child.getAttributeValue(HIST_FECHAINI_TAG);
                fechaf      = child.getAttributeValue(HIST_FECHAFIN_TAG);
                 Date fi = df.parse(fechai);
                 Date ff = df.parse(fechai);
                 fei.setTime(fi);
                 fef.setTime(ff);
                list= new Historial(ip,fei,fef,mac);
                 lista.addDirecciones(list);
               
                
                pos++;
            }
          
        }
        catch(JDOMParseException e)
        {
            System.out.println(ERROR_XML_EMPTY_FILE);
            e.printStackTrace();
        }
        catch(JDOMException e)
        {
            System.out.println(ERROR_XML_PROCESSING_FILE);
            e.printStackTrace();
        }
        catch(IOException e)
        {
            System.out.println(ERROR_XML_PROCESSING_FILE);
            e.printStackTrace();
        }


        return lista;
    }
    
    


} 