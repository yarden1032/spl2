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
        Ewoks.getInstance().release();

        for (int i = 1; i <= 50; i++) {
            List<Ewok> ewokList = new LinkedList<>();
            ewokList.add(new Ewok(i));
            Ewoks.getInstance().add(ewokList);
            assertTrue(Ewoks.getInstance().isAvailable(i-1));
            Ewoks.getInstance().getEwokList().get(i-1).acquire();
            assertFalse(Ewoks.getInstance().isAvailable(i-1));
            Ewoks.getInstance().getEwokList().get(i-1).release();
            assertTrue(Ewoks.getInstance().isAvailable(i-1));
        }
        assertEquals(Ewoks.getInstance().getEwokList().size(), 50);

Ewoks.getInstance().release();
    }
}
