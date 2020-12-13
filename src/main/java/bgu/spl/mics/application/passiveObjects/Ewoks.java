package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.MessageBusImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {
    private static class SingletonHolder {


        private static Ewoks instance = new Ewoks();
    }
   private List<Ewok>  ewokList=null;


    public Ewoks() {
        ewokList=new LinkedList<>();

    }


    public synchronized static Ewoks getInstance() {

        return SingletonHolder.instance;
    }
    public void add(List<Ewok>  l)
    {
for (int i=0;i<l.size();i++)
       this.ewokList.add(l.get(i));
    }
    public List<Ewok> getEwokList()
    {
        return ewokList;
    }

    public boolean isAvailable(int serial)
    {
        return ewokList.get(serial-1).isAvailable();
    }



}
