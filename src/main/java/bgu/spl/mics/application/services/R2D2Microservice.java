package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;

import bgu.spl.mics.application.messages.BroadcastImpl;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {

    public R2D2Microservice(long duration) {
        super("R2D2");
        this.duration=duration;

    }
    private long duration;
    @Override
    protected void initialize() {

        MessageBusImpl.getInstance()
                .register(this);
        MessageBusImpl.getInstance()
                .subscribeEvent(DeactivationEvent.class, this);
        MessageBusImpl.getInstance()
                .subscribeBroadcast(Broadcast.class,this);
    }

    protected void initialize(MessageBusImpl messageBus) {
        messageBus.register(this);
        messageBus.subscribeEvent(DeactivationEvent.class, this);
        messageBus.subscribeBroadcast(Broadcast.class,this);
        //register
        //subscribe event to deactivate
        //subscribe broadcast
        //run ->if nothing wait for messages
    }
    public long getDuration (){

        return duration;
    }


    public void InitiateDeactivation()
    {


        Diary.getInstance().setLittleDiary(getName()+"Deactivate",System.currentTimeMillis());
        MessageBusImpl.getInstance().sendBroadcast(new BroadcastImpl(getName(),System.currentTimeMillis()));

    }
}
