package bgu.spl.mics.application.messages;
import bgu.spl.mics.Callback;
import bgu.spl.mics.CallbackAttack;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;

import java.util.Arrays;
import java.util.List;

public class AttackEvent implements Event<Boolean> {
    private final List<Integer> serialnumbers;
    private final int duration;
    private CallbackAttack callbackAttack;
    public AttackEvent (Integer[] serialnumber, int duration)
    {
        this.serialnumbers= Arrays.asList(serialnumber);
        this.duration=duration;
        callbackAttack=new CallbackAttack();
    }
    public List <Integer> getSerialNumbers()
    {
        return serialnumbers;
    }
    public int getDuration()
    {
        return duration;
    }


    @Override
    public Callback getCallback() {
        return callbackAttack;
    }
}
