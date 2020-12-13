package bgu.spl.mics.application.messages;

import bgu.spl.mics.Callback;
import bgu.spl.mics.CallbackAttack;
import bgu.spl.mics.CallbackDeactivation;
import bgu.spl.mics.Event;

public class DeactivationEvent implements Event<Boolean> {
    private CallbackDeactivation CallbackDeactivation;


    public DeactivationEvent()
    {
        CallbackDeactivation=new CallbackDeactivation();
    }




    @Override
    public Callback getCallback() {
        return CallbackDeactivation;
    }
}
