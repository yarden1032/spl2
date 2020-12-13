package bgu.spl.mics;


import bgu.spl.mics.application.services.R2D2Microservice;

public class CallbackDeactivation  implements Callback {



    @Override
    public void call(Object c) {
        R2D2Microservice m= (R2D2Microservice) c;
        long duration= m.getDuration();

        try {
            Thread.sleep(duration);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
