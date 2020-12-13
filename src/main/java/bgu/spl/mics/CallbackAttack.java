package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BroadcastImpl;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.List;

public class CallbackAttack implements Callback {
    @Override
    public synchronized void call(Object c) {
            AttackEvent currentAttack = (AttackEvent) c;
            for (int i = 0; i <currentAttack.getSerialNumbers().size() ; i++){
              Integer ewokToAquire = currentAttack.getSerialNumbers().get(i);
                if ((Ewoks.getInstance()).getEwokList().get(ewokToAquire-1).isAvailable()) {
                    (Ewoks.getInstance()).getEwokList().get(ewokToAquire-1).acquire();
                    ///TODO TO CHECK ISSUE OF AQUIRED AND NOT USE BECAUSE THE WAIT
                } else {
                    try {
                        i--;
                       Thread.currentThread().wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }



            try {
                Thread.sleep(currentAttack.getDuration());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            for (int i=0;i<currentAttack.getSerialNumbers().size();i++)
            {
                Ewoks.getInstance().getEwokList().get(i).release();

                //Thread.currentThread().notifyAll();
            }
        MessageBusImpl.getInstance().notifyAll();



            /*
            (currentAttack.getSerialNumbers()).forEach((temp) ->{
                Ewoks.getInstance().getEwokList().get(temp).release();
                notifyAll();
            });*/

            //todo - make sure we do it right here

        }
    }

