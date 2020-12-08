package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {
	
    public C3POMicroservice(MessageBusImpl messageBus) {
        super("C3PO");
    }

    @Override
    protected void initialize() {

    }
    protected void initialize(MessageBusImpl messageBus) {
        messageBus.register(this);
        messageBus.subscribeEvent(AttackEvent.class, this);
        messageBus.subscribeBroadcast(Broadcast.class,this);
        //register
        //subscribe event to attack
        //subscribe broadcast
        //run ->if nothing wait for messages
    }

}
