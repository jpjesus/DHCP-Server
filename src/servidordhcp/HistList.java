/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidordhcp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Jesus
 */
public class HistList implements Serializable  {
        private SortedSet <Historial> histlista;
         private Comparator ipcomparator;
    public HistList(SortedSet<Historial> directlist) {
        this.histlista = directlist;
    }

    public HistList() {
       this.ipcomparator = (Comparator) new IpComparator();
       this.histlista= new TreeSet(ipcomparator);
    }
        
        
        
         public boolean addDirecciones(Historial user)
    {
        return this.histlista.add(user);
    }
    
    public boolean removeDireccion(Historial user)
    {
        return this.histlista.remove(user);
    }
    
    public void printUserList()
    {
        Iterator iterator;
        
 
        
        iterator = this.histlista.iterator();
        
        while (iterator.hasNext())
            System.out.println(iterator.next());
    }
    
    public int size()
    {
        return this.histlista.size();
    }
    
    public Historial get(int pos)
    {
        ArrayList <Historial> list = new ArrayList(this.histlista);
        
        return list.get(pos);
    }

}
