package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;

import bgu.spl.mics.application.messages.BombDestryerEvent;
import bgu.spl.mics.application.messages.BroadcastImpl;


/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {

    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration=duration;
    }
    private long duration;
    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        MessageBusImpl.getInstance().subscribeEvent(BombDestryerEvent.class, this);
        MessageBusImpl.getInstance().subscribeBroadcast(Broadcast.class,this);
        //register
        //subscribe event to bomb
        //subscribe broadcast
        //run ->if nothing wait for messages
    }
    protected void initialize(MessageBusImpl messageBus) {
        MessageBusImpl.getInstance().register(this);
        MessageBusImpl.getInstance().subscribeEvent(BombDestryerEvent.class, this);
        MessageBusImpl.getInstance().subscribeBroadcast(Broadcast.class,this);
        //register
        //subscribe event to bomb
        //subscribe broadcast
        //run ->if nothing wait for messages
    }

    public long getDuration (){

        return duration;
    }
    public void InitiateBombardment()
    {





        MessageBusImpl.getInstance().sendBroadcast(new BroadcastImpl(getName(),System.currentTimeMillis()));
    }

}


