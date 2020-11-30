package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Ewok;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ewoktest {

    @Test
    public void testEwok(){
      /*  int n=50;
        ArrayList<Ewok> ewoks = new ArrayList<Ewok>(n);
        Random random = new Random();
        Iterator<String> Iterator = countries.iterator();
        while(Iterator.hasNext())
        {
         int a=random.nextInt(1000);
            ewoks.add(new Ewok(a));
            assertTrue( ewoks.getSerialNumber()==a);
            assertTrue(ewoks[i].isAvailable());
            ewoks[i].acquire();
            assertTrue(! ewoks[i].isAvailable());
            ewoks[i].release();
            assertTrue( ewoks[i].isAvailable());
        }

*/
        int a=1;
        Ewok ewok= new Ewok(a);

        assertTrue( ewok.getSerialNumber()==a);
        assertTrue(ewok.isAvailable());
        ewok.acquire();
        assertTrue(! ewok.isAvailable());
        ewok.release();
        assertTrue( ewok.isAvailable());

    /*
        try {
            assertTrue(!ewoks[0].equals(ewoks[1]));
        }
        catch {

        }*/
    }
}
