package bgu.spl.mics;

import bgu.spl.mics.application.InOutputJsonClass;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals(a, ewok.getSerialNumber());
        assertTrue(ewok.isAvailable());
        ewok.acquire();
        assertFalse(ewok.isAvailable());
        ewok.release();
        assertTrue( ewok.isAvailable());

    /*
        try {
            assertTrue(!ewoks[0].equals(ewoks[1]));
        }
        catch {

        }*/
    }
  /*  @Test
    public void testMain(){

    //Input
    long R2D2=0;
    long Lando=0;
    int Ewoksnumbers=0;
    Attack[] attacks=null;
		try {
        Gson g= InOutputJsonClass.Read("input.json");
        attacks=InOutputJsonClass.readAttack(g);
        R2D2=(long)InOutputJsonClass.readOther(g,"R2D2");
        Lando=(long)InOutputJsonClass.readOther(g,"Lando");
        Ewoksnumbers=(int)InOutputJsonClass.readOther(g,"Ewoks");

    } catch (
    IOException e) {
        e.printStackTrace();
    }

    Ewoks ewoks=new Ewoks();
    List<Ewok> ewokList=new LinkedList<>();
		for (int i=0;i<=Ewoksnumbers;i++)
    {
        Ewok e=new Ewok(i);
    }
		ewoks.add(ewokList);

    //initialize all services
    MessageBusImpl messageBus=new MessageBusImpl();
    bgu.spl.mics.application.services.C3POMicroservice C3POMicroservice= new C3POMicroservice(messageBus);
		C3POMicroservice.run();

    bgu.spl.mics.application.services.HanSoloMicroservice HanSoloMicroservice= new HanSoloMicroservice(messageBus);
		HanSoloMicroservice.run();
    bgu.spl.mics.application.services.LandoMicroservice LandoMicroservice = new LandoMicroservice(Lando); //TODO get from input //destroy
		LandoMicroservice.run();
    bgu.spl.mics.application.services.LeiaMicroservice LeiaMicroservice=new LeiaMicroservice(attacks); //TODO it make attackevent from input
		LeiaMicroservice.run();
    bgu.spl.mics.application.services.R2D2Microservice R2D2Microservice =new R2D2Microservice(R2D2); //TODO get from input //deactivate
		R2D2Microservice.run();


    //do stuff
		LeiaMicroservice.startAttack();

    //to stop everything



    //
		InOutputJsonClass.output();
}

*/
}
