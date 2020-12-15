package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Message;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestryerEvent;
import bgu.spl.mics.application.messages.BroadcastImpl;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;


/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
	private Attack[] attacks;
	private int counterAttackerfinish=0; //TODO: can be atomic
	
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
    }

    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        MessageBusImpl.getInstance().subscribeBroadcast(Broadcast.class,this);
        //register
        //subscribe event to attack
        //subscribe broadcast
        //run ->if nothing wait for messages
    }
    protected void initialize(MessageBusImpl messageBus){

        messageBus.register(this);
        messageBus.subscribeBroadcast(Broadcast.class,this);
        //register
        //subscribe event to attack
        //subscribe broadcast
        //run ->if nothing wait for messages

    }
    public void startAttack () {
        Object ob = new Object();
        synchronized (ob) {
            for (int i = 0; i < attacks.length; i++) {
                AttackEvent attackEvent = new AttackEvent(attacks[i].getSerials(), attacks[i].getDuration());

                        MessageBusImpl.getInstance().sendEvent(attackEvent);


            }
        }
    }

    public void handleBoradcast (Message current)
    {
        BroadcastImpl b= (BroadcastImpl) current;
        if (b.getWhoSendIt().equals("Han") ||b.getWhoSendIt().equals("C3PO"))
        {

            counterAttackerfinish++;

            if (counterAttackerfinish==attacks.length)///todo here
            {
                DeactivationEvent deactivationEvent= new DeactivationEvent();
                MessageBusImpl.getInstance().sendEvent(deactivationEvent);
                Diary.getInstance().setLittleDiary("TotalAttacks",counterAttackerfinish);
            }


        }
        if (b.getWhoSendIt().equals("R2D2"))
        {
            BombDestryerEvent bombDestryerEvent=new BombDestryerEvent();
            MessageBusImpl.getInstance().sendEvent(bombDestryerEvent);
        }

    }
}
