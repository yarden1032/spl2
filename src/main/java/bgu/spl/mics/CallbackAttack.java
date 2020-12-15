package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;


import bgu.spl.mics.application.passiveObjects.Ewoks;





public class CallbackAttack implements Callback {



    @Override
    public synchronized void call(Object c) {
            AttackEvent currentAttack = (AttackEvent) c;
          //   synchronized (key) {
                 for (int i = 0; i < currentAttack.getSerialNumbers().size(); i++) {
                     Integer ewokToAquire = currentAttack.getSerialNumbers().get(i);

                     if (((Ewoks.getInstance()).getEwokList()).get(ewokToAquire - 1).isAvailable()) {
                         ((Ewoks.getInstance()).getEwokList()).get(ewokToAquire - 1).acquire();
                     }
                     ///TODO TO CHECK ISSUE OF AQUIRED AND NOT USE BECAUSE THE WAIT
                     else {
                         //     synchronized (key) {
                         try {
                             Thread.currentThread().wait();

                         } catch (InterruptedException e) {


                             e.printStackTrace();
                         }

                   //  }

                 }
             }



            try {
                Thread.sleep(currentAttack.getDuration());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            for (int i=0;i<currentAttack.getSerialNumbers().size();i++)
            {
                Integer ewokTorelease = currentAttack.getSerialNumbers().get(i);
                Ewoks.getInstance().getEwokList().get(ewokTorelease-1).release();


            }
        MessageBusImpl.getInstance().notifyAll();







        }
    }

