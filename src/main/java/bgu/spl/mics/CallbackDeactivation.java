package bgu.spl.mics;


import bgu.spl.mics.application.services.R2D2Microservice;

public class CallbackDeactivation  implements Callback {



    @Override
    public void call(Object c) {
        R2D2Microservice tempobject=(R2D2Microservice) c ;
        int n= tempobject.getDuration();
        try {
            wait(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
