package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BroadcastImpl;

import bgu.spl.mics.application.passiveObjects.Diary;

import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.HanSoloMicroservice;

import org.junit.jupiter.api.Test;


import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageBusTest {

    private MessageBusImpl messageBus;
    private MicroService microService1;
    private MicroService microService2;
    private MicroService microService3;
    private HanSoloMicroservice hanSoloMicroservice;

    public void setUp() {
        messageBus = new MessageBusImpl();
  //      microService1 = new MicroService("Test");
        hanSoloMicroservice=new HanSoloMicroservice(MessageBusImpl.getInstance());


        microService2 = new MicroService("Test2") {
            @Override
            protected void initialize() {

            }
        };
        microService3 = new MicroService("Test3") {
            @Override
            protected void initialize() {

            }
        };

    }

    @Test

    public void testRegister() {


        hanSoloMicroservice = new HanSoloMicroservice(MessageBusImpl.getInstance());
        try {
            Thread test = new Thread(hanSoloMicroservice);
            test.start();
            Thread.sleep(2000);

            Integer[] a = new Integer[1];
            a[0]=1;
            List<Ewok> ewoks=new LinkedList<>();
            for (int i=1;i<20;i++)
            {
                ewoks.add(new Ewok(i));
            }
            Ewoks ewoks1=new Ewoks();
            Ewoks.getInstance().add(ewoks);
            Event eventest = new AttackEvent(a, 100);


            MessageBusImpl.getInstance().subscribeEvent(AttackEvent.class, microService1);


            MessageBusImpl.getInstance().sendEvent(eventest);


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            assertTrue(test.getState() == Thread.State.WAITING);
            MessageBusImpl.getInstance().sendBroadcast(new BroadcastImpl("Lando", System.currentTimeMillis()));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            Diary.getInstance().destructorForTest();
            MessageBusImpl.getInstance().unregister(hanSoloMicroservice);
        } catch (NullPointerException | InterruptedException e) {

        }


        //נוצר פה תור

    }

    @Test
    public void testRegister_fail() {

            List<Ewok> ewoks=new LinkedList<>();
            for (int i=1;i<20;i++)
            {
                ewoks.add(new Ewok(i));
            }
            Ewoks ewoks1=new Ewoks();
            Ewoks.getInstance().add(ewoks);


        hanSoloMicroservice=new HanSoloMicroservice(MessageBusImpl.getInstance());
        Thread test = new Thread(hanSoloMicroservice);
        test.start();
        MessageBusImpl.getInstance().unregister(hanSoloMicroservice);
        try {
            Thread.sleep(2000); //נוצר פה תור
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        Integer[] a = new Integer[1];
        a[0]=2;
        Event eventest = new AttackEvent(a, 100);
        MessageBusImpl.getInstance().sendEvent(eventest);

        test.interrupt();
        //String st = (String) (((Object[]) Diary.getInstance().getLittleDiary().get(0))[0]);

        assertTrue(Diary.getInstance().getLittleDiary().size()<=1);

        MessageBusImpl.getInstance().sendBroadcast(new BroadcastImpl("Lando", System.currentTimeMillis()));
        Diary.getInstance().destructorForTest();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MessageBusImpl.getInstance().unregister(hanSoloMicroservice);
        Ewoks.getInstance().release();
        Diary.getInstance().destructorForTest();

    }




}