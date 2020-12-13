package bgu.spl.mics;

import bgu.spl.mics.application.services.LandoMicroservice;
import bgu.spl.mics.application.services.R2D2Microservice;

public class CallbackBombDestroyer  implements Callback {


    @Override
    public void call(Object c) {
        LandoMicroservice m= (LandoMicroservice) c;
        long duration= m.getDuration();

        try {
            Thread.sleep(duration);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}