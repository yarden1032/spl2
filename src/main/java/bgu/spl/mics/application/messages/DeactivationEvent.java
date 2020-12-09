package bgu.spl.mics.application.messages;

import bgu.spl.mics.Callback;
import bgu.spl.mics.CallbackAttack;
import bgu.spl.mics.Event;

public class DeactivationEvent implements Event<Boolean> {
    private CallbackAttack callbackAttack;


    @Override
    public Callback getCallback() {
        return callbackAttack;
    }
}
