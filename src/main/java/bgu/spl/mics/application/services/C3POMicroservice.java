package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BroadcastImpl;
import bgu.spl.mics.application.passiveObjects.Diary;



/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {

    private long finishattacking;
    public C3POMicroservice(MessageBusImpl messageBus) {
        super("C3PO");
    }

    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        MessageBusImpl.getInstance().subscribeEvent(AttackEvent.class, this);
        MessageBusImpl.getInstance().subscribeBroadcast(Broadcast.class,this);
        //register
        //subscribe event to attack
        //subscribe broadcast
        //run ->if nothing wait for messages
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

    public void InitiateAttack() {


        finishattacking=System.currentTimeMillis();
        MessageBusImpl.getInstance().sendBroadcast(new BroadcastImpl(getName(),finishattacking));
        Diary.getInstance().setLittleDiary(getName()+"Finish",finishattacking);



    }


}

