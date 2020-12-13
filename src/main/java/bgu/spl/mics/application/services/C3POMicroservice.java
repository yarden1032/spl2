package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BroadcastImpl;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;


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
    public void InitiateAttack(Message current) {

       /* CallbackAttack callbackAttack=new CallbackAttack();
        callbackAttack.call(current);
*/

        MessageBusImpl.getInstance().sendBroadcast(new BroadcastImpl(getName(),System.currentTimeMillis()));
        Diary.getInstance().setLittleDiary(getName()+"Finish",System.currentTimeMillis());


        /*
        AttackEvent currentAttack = (AttackEvent) current;
        for (int i = 0; i < currentAttack.getSerialNumbers().size(); i++){

            if (Ewoks.getInstance().getEwokList().get(i).isAvailable()) {
                Ewoks.getInstance().getEwokList().get(i).acquire();
                ///TODO TO CHECK ISSUE OF AQUIRED AND NOT USE BECAUSE THE WAIT
            } else {
                try {
                    i--;
                    wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }



        try {
            wait(currentAttack.getDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        (currentAttack.getSerialNumbers()).forEach((temp) ->{
            Ewoks.getInstance().getEwokList().get(temp).release();
            Ewoks.getInstance().getEwokList().get(temp).notify();
        });
        MessageBusImpl.getInstance().sendBroadcast(new BroadcastImpl(getName(),System.currentTimeMillis()));
        Diary.getInstance().setLittleDiary(getName()+"Finish",System.currentTimeMillis());
*/
    }


}

