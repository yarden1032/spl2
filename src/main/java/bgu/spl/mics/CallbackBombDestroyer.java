package bgu.spl.mics;

import bgu.spl.mics.application.services.LandoMicroservice;
import bgu.spl.mics.application.services.R2D2Microservice;

public class CallbackBombDestroyer  implements Callback {


    @Override
    public void call(Object c) {
        LandoMicroservice tempobject=(LandoMicroservice) c ;
        long n= tempobject.getDuration();
        try {
            wait(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
