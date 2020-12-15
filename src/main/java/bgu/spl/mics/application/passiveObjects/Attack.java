package bgu.spl.mics.application.passiveObjects;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;



/**
 * Passive data-object representing an attack object.
 * You must not alter any of the given public methods of this class.
 * <p>
 * YDo not add any additional members/method to this class (except for getters).
 */
public class Attack {
    final Integer [] serials; //List<Integer>
    final int duration;

    /**
     * Constructor.
     */

    public Attack(Integer [] serialNumbers, int duration) {

        serials=serialNumbers;

        this.duration = duration;
    }
    public Integer [] getSerials()
    {
        return serials;
    }
    public int getDuration()
    {
        return duration;
    }
    public Attack(ArrayList attacksInput , Object n)
    {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.toJsonTree(n).getAsJsonObject();
                duration=(jsonObject.get("duration")).getAsInt();
                JsonArray jsSerials=jsonObject.get("serials").getAsJsonArray();
                serials=new Integer[jsSerials.size()];
        for (int i=0; i < jsSerials.size(); i++) {
           serials[i] =jsSerials.get(i).getAsInt();
        }



    }

}

