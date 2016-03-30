/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidordhcp;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Jesus
 */
public class IpComparator implements Serializable,Comparator{
   @Override
    public int compare(Object firstObject, Object secondObject)
    {
        int result = ((Historial)firstObject).getIp().compareTo(((Historial)secondObject).getIp());

        if (result == 0)
            return 0;
        
        if (result < 0)
            return -1;
        
        return 1;
    }  
}
