package bgu.spl.mics;

public class CallbackAttack implements Callback {
    @Override
    public void call(Object c) {
        int n=(int) c;
        try {
            wait(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
