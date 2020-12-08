package bgu.spl.mics.application.messages;

import bgu.spl.mics.Callback;
import bgu.spl.mics.CallbackAttack;
import bgu.spl.mics.Event;

public class BombDestryerEvent implements Event<Boolean> {
    private CallbackAttack callbackAttack;

    public BombDestryerEvent()
    {

    }
    @Override
    public Callback getCallback() {
        return null;
    }
}
