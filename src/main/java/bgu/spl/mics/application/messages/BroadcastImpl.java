package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Callback;
import bgu.spl.mics.CallbackBroadcast;


public class BroadcastImpl implements Broadcast {
    private CallbackBroadcast callbackBroadcast;
    private String whoSendIt;
    private long whenSentIt;

    public BroadcastImpl(String whoSendIt, long whenSentIt)
    {

    }

    public Callback getCallback() {
        return callbackBroadcast;
    }


public String getWhoSendIt()
{
    return whoSendIt;
}

}