package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.util.LinkedList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class FlowTest {

    private static bgu.spl.mics.Test[] getTestsFromJson(String filePath) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, bgu.spl.mics.Test[].class);
        }
    }
@Test
public void FlowTest_helper() throws IOException {
    bgu.spl.mics.Test[] tests= getTestsFromJson("Tests.json");

    for (int i=0;i<tests.length;i++)
    {
        Diary d=Diary.getInstance();
        Ewoks e=Ewoks.getInstance();
        int finalI = i;
        assertDoesNotThrow(() -> {
            FlowTest(finalI,tests);


        },"test number:" + i + "is Failed");
    }

}

@Test
public void FlowTest(int i,  bgu.spl.mics.Test[] tests) throws  InterruptedException {



        List<Ewok> ewokList=new LinkedList<>();
        for (int k=1;k<=tests[i].Ewoks;k++)
        {

            Ewok e=new Ewok(k);
            ewokList.add(e);

        }
        Ewoks.getInstance().add(ewokList);


        Attack[] attacks=tests[i].getAttacks();
        //initialize all services
        MessageBusImpl messageBus=new MessageBusImpl();
        C3POMicroservice C3POMicroservice= new C3POMicroservice(messageBus);
        Thread C3POMicroserviceThread= new Thread(C3POMicroservice);
        C3POMicroserviceThread.start();
        HanSoloMicroservice HanSoloMicroservice= new HanSoloMicroservice(messageBus);
        Thread HanSoloMicroserviceThread= new Thread(HanSoloMicroservice);
        HanSoloMicroserviceThread.start();
        LandoMicroservice LandoMicroservice = new LandoMicroservice(tests[i].Lando); //TODO get from input //destroy
        Thread LandoMicroserviceThread = new Thread(LandoMicroservice);
        LandoMicroserviceThread.start();
        LeiaMicroservice LeiaMicroservice=new LeiaMicroservice(attacks); //TODO it make attackevent from input
        Thread LeiaMicroserviceThread= new Thread(LeiaMicroservice);
        LeiaMicroserviceThread.start();
        R2D2Microservice R2D2Microservice =new R2D2Microservice(tests[i].R2D2); //TODO get from input //deactivate
        Thread R2D2MicroserviceThread=new Thread(R2D2Microservice);
        R2D2MicroserviceThread.start();

        while (R2D2MicroserviceThread.getState()!= Thread.State.WAITING || LeiaMicroserviceThread.getState()!= Thread.State.WAITING ||C3POMicroserviceThread.getState()!=Thread.State.WAITING
                || HanSoloMicroserviceThread.getState()!=Thread.State.WAITING || LandoMicroserviceThread.getState()!=Thread.State.WAITING)
        //do stuff
        {


                Thread.sleep(100);


        }
    MessageBus m2=MessageBusImpl.getInstance();
        LeiaMicroservice.startAttack();
    MessageBus m3=MessageBusImpl.getInstance();




        //
        while (!(R2D2MicroserviceThread.getState()== Thread.State.TERMINATED && LeiaMicroserviceThread.getState()== Thread.State.TERMINATED &&C3POMicroserviceThread.getState()==Thread.State.TERMINATED
                && HanSoloMicroserviceThread.getState()==Thread.State.TERMINATED && LandoMicroserviceThread.getState()==Thread.State.TERMINATED))
        //do stuff
        {
            assertDoesNotThrow(() -> {
                Thread.sleep(100);

            },"test number:" + i + "is Failed");
        }
         /*   assertDoesNotThrow(() => { /* custom code block here*/
       /*     Assertions.assertDoesNotThrow(NumberFormatException.class, () -> {
                Thread.sleep(100);
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                assertDoesNotThrow(InterruptedException e);
            }
*/

        //to stop everything

        //start Assertion:
        Object [] firstDairy = (Object[]) (Diary.getInstance().getLittleDiary().get(0));
        assertEquals(firstDairy[0],"TotalAttacks","test number:"+i+"is Failed");
        long realAttack=tests[i].getNumberOfAttacks().intValue();
        assertEquals(firstDairy[1],realAttack);
        Object [] deactivattion = (Object[]) (Diary.getInstance().getLittleDiary().get(attacks.length+1));
        assertEquals(deactivattion[0],"R2D2Deactivate","test number:"+i+"is Failed");

        for (int j=attacks.length+2;j<Diary.getInstance().getLittleDiary().size();j++)
        //
        {
            Object [] diaryCheck = (Object[]) (Diary.getInstance().getLittleDiary().get(j));

            String st= (String) diaryCheck[0];
            assertTrue( st.contains("Terminate"),"test number:"+i+"is Failed");

        }

        MessageBusImpl.getInstance().unregister(C3POMicroservice);
    MessageBusImpl.getInstance().unregister(HanSoloMicroservice);
    MessageBusImpl.getInstance().unregister(LandoMicroservice);
    MessageBusImpl.getInstance().unregister(LeiaMicroservice);
    MessageBusImpl.getInstance().unregister(R2D2Microservice);
    Ewoks.getInstance().release();




        Diary.getInstance().destructorForTest();
    }



}

