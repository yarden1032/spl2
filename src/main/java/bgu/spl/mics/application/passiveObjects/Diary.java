package bgu.spl.mics.application.passiveObjects;


import java.util.LinkedList;
import java.util.List;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    List<Object> littleDiary;

    //to Know: first will be the string and second will be timestemp

    private static class SingletonHolder{
        private static Diary instance = new Diary();
    }
    public synchronized static Diary getInstance() {

        return Diary.SingletonHolder.instance;
    }

    public Diary()
    {
        littleDiary= new LinkedList<Object>();
    }
    public void setLittleDiary (String message,long timestamp)
    {
        Object arr[]=new Object[2];
        arr[0]=message;
        arr[1]=timestamp;
        if (message.equals("TotalAttacks"))
        {
            littleDiary.add(0,arr);
        }
        littleDiary.add(arr);



    }
    public  List<Object> getLittleDiary()
    {
        return littleDiary;
    }





}
