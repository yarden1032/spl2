package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Callback;
import bgu.spl.mics.CallbackBroadcast;


public class BroadcastImpl implements Broadcast {
    private final CallbackBroadcast callbackBroadcast;
    private final String whoSendIt;
    private final long whenSentIt;

    public BroadcastImpl(String whoSendIt, long whenSentIt) {
        this.whoSendIt = whoSendIt;
        this.whenSentIt = whenSentIt;
        callbackBroadcast=new CallbackBroadcast();
    }

      public Callback getCallback() {
          return callbackBroadcast;
      }


    public String getWhoSendIt() {
        return whoSendIt;
    }


    public long getWhenSentIt() {
        return whenSentIt;
    }
}