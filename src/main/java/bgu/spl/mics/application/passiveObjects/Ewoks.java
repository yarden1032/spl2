package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.MessageBusImpl;

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

    List<Ewok>  ewokList;

    public Ewoks() {
        ewokList=null;
    }

    private static class SingletonHolder{
        private static Ewoks instance = new Ewoks();
    }
    public synchronized static Ewoks getInstance() {

        return Ewoks.SingletonHolder.instance;
    }
    public void add(List<Ewok>  ewokList)
    {
       this.ewokList.addAll(ewokList);
    }
    public List<Ewok> getEwokList()
    {
        return ewokList;
    }




}
