package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
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
        microService2= new MicroService("Test2") {
            @Override
            protected void initialize() {

            }
        };
        microService3= new MicroService("Test3") {
            @Override
            protected void initialize() {

            }
        };

    }
    @Test

    public void testRegister(){

        messageBus.register(microService1); //נוצר פה תור
        Event<String> eventest=new Event<String>() {

        };
        //messageBus.subscribeEvent( ,microService1);

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

        boolean b=false;

        try {
            messageBus.awaitMessage(microService1);

        } catch (InterruptedException e) {
            b=true;
        }
        assertTrue(b);

    }
    @Test
    public void testSubscribeEvent()
    {
        messageBus.register(microService1);
        messageBus.subscribeEvent(AttackEvent.class, microService1);
        AttackEvent attc=new AttackEvent();
        messageBus.sendEvent(attc);
        try {
            messageBus.awaitMessage(microService1);
        } catch (InterruptedException e) {
            fail("Test Fail - no message received");
            e.printStackTrace();

        }


    }
    @Test
    public void testBroadcast()
    {
        messageBus.register(microService1);
        messageBus.register(microService2);
        messageBus.subscribeBroadcast(Broadcast.class, microService1);
        messageBus.subscribeBroadcast(Broadcast.class, microService2);
        Broadcast broadcast =null;

        microService3.sendBroadcast(broadcast);
        try {
            messageBus.awaitMessage(microService2);
            messageBus.awaitMessage(microService1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Test Fail - no message received");
        }

    }
    @Test

    public void testUnRegister(){
        testRegister();
        messageBus.unregister(microService1);
        Event<String> eventest=new Event<String>() {
        };
        boolean b=false;

        try {
            messageBus.sendEvent(eventest);
            messageBus.awaitMessage(microService1);

        } catch (InterruptedException e) {

            b=true;
        }
        assertTrue(b,"Test Fail - the message received to unregistered service");

    }
    @Test

    public void testAwaitMessage() {
        testUnRegister();
        testBroadcast();
  /**/

    }
    @Test
    public void testFutureSendEvent() {

        messageBus.register(microService1);
        messageBus.register(microService2);
        messageBus.subscribeEvent(AttackEvent.class, microService1);
        messageBus.subscribeEvent(AttackEvent.class, microService2);
        AttackEvent attc=new AttackEvent();
        Future<Boolean> future= messageBus.sendEvent(attc);
       assertNotNull(future);
        boolean b=false;
        try {
            messageBus.awaitMessage(microService1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }
        try {
            messageBus.awaitMessage(microService2);
        } catch (InterruptedException e) {
            b=true;
        }
        assertTrue(b,"Test Fail - the message received to unregistered service");

    }
    @Test
    public void testFutureSendEvent_twoEvents() {

        messageBus.register(microService1);
        messageBus.register(microService2);
        messageBus.subscribeEvent(AttackEvent.class, microService1);
        messageBus.subscribeEvent(AttackEvent.class, microService2);
        AttackEvent attc=new AttackEvent();
        AttackEvent attc2=new AttackEvent();
        Future<Boolean> future= messageBus.sendEvent(attc);
        Future<Boolean> future2= messageBus.sendEvent(attc2);
        assertNotNull(future);
        assertNotNull(future2);
        try {
            messageBus.awaitMessage(microService1);
            messageBus.awaitMessage(microService2);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }


    }
    @Test
    public void testComplete() {
        messageBus.register(microService1);
        messageBus.subscribeEvent(AttackEvent.class, microService1);
        AttackEvent attc=new AttackEvent();
        Future<Boolean> future= messageBus.sendEvent(attc);
        microService1.complete(attc,true);
       assertTrue(future.isDone());
    }

}
