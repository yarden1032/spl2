package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageBusTest {

    private MessageBusImpl messageBus;
    private MicroService microService1;
    private MicroService microService2;
    private MicroService microService3;

    public void setUp() {
        messageBus = new MessageBusImpl();
        microService1 = new MicroService("Test") {
            @Override
            protected void initialize() {

            }
        };
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

        messageBus.register(microService1);

        //נוצר פה תור

        Integer[] a = new Integer[5];
        Event eventest = new AttackEvent(a, 100);


        messageBus.subscribeEvent(AttackEvent.class, microService1);

        try {
            messageBus.sendEvent(eventest);
            messageBus.awaitMessage(microService1);

        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Test Fail - no message received");
        }

    }


    @Test
    public void testRegister_fail() {

        messageBus.register(microService1); //נוצר פה תור


        Thread test = new Thread(microService1);
        test.start();
        Integer[] a = new Integer[5];
        Event eventest = new AttackEvent(a, 100);
        messageBus.sendEvent(eventest);

        test.interrupt();
        String st = (String) (((Object[]) Diary.getInstance().getLittleDiary().get(0))[0]);

        assertNotEquals(Diary.getInstance().getLittleDiary().size(), 0);


    }


    @Test

    public void testUnRegister() {

        messageBus.register(microService1); //נוצר פה תור


        Thread test = new Thread(microService1);
        test.start();
        Integer[] a = new Integer[5];
        Event eventest = new AttackEvent(a, 100);
        MessageBusImpl.getInstance().unregister(microService1);
        messageBus.sendEvent(eventest);

        test.interrupt();
        String st = (String) (((Object[]) Diary.getInstance().getLittleDiary().get(0))[0]);

        assertNotEquals(Diary.getInstance().getLittleDiary().size(), 0);


    }


}