package bgu.spl.mics.application.messages;

import bgu.spl.mics.*;

public class BombDestryerEvent implements Event<Boolean>  {
    private CallbackBombDestroyer CallbackBombDestroyer;

    public BombDestryerEvent()
    {
        CallbackBombDestroyer=new CallbackBombDestroyer();
    }
    @Override
    public Callback getCallback() {
        return CallbackBombDestroyer;
    }
}


