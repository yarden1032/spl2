package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestryerEvent;

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
       
    }
    protected void initialize(MessageBusImpl messageBus) {
        messageBus.register(this);
        messageBus.subscribeEvent(BombDestryerEvent.class, this);
        messageBus.subscribeBroadcast(Broadcast.class,this);
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
        try {
            wait(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}


