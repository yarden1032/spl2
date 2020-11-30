package bgu.spl.mics;

import org.junit.jupiter.api.Test;

public class MessageBusTest {

    private MessageBus messageBus;
    private MicroService microService;

    public void setUp() {
        messageBus = new MessageBus();
        microService= new MicroService("Test") {
            @Override
            protected void initialize() {

            }
        };

    }
    @Test

    public void testRegister(){
        messageBus.register(microService);
        Event<String> eventest=new Event<String>() {
        };

        messageBus.sendEvent(eventest);


    }
}
