package bgu.spl.mics;

import bgu.spl.mics.application.InOutputJsonClass;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class Ewoktest {

    @Test
    public void testEwok() {
        int n = 50;


        for (int i = 1; i <= 50; i++) {
            List<Ewok> ewokList = new LinkedList<>();
            ewokList.add(new Ewok(i));
            Ewoks.getInstance().add(ewokList);
            assertTrue(Ewoks.getInstance().isAvailable(i));
            Ewoks.getInstance().getEwokList().get(i).acquire();
            assertFalse(Ewoks.getInstance().isAvailable(i));
            Ewoks.getInstance().getEwokList().get(i).release();
            assertTrue(Ewoks.getInstance().isAvailable(i));
        }
        assertEquals(Ewoks.getInstance().getEwokList().size(), 50);


    }
}
