package bgu.spl.mics;


import org.junit.jupiter.api.BeforeEach;


import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FutureTest {

    private Future<String> future;
    private Future<String> future2;
    private Future<String> future3;
    @BeforeEach
    public void setUp(){
        future = new Future<>();
        future2 = new Future<>();
        future3 = new Future<>();
    }

    @Test
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        assertEquals(future.get(), str);
    }


    @Test
    public void testisDone() {
        assertFalse(future3.isDone());
        String str = "someResult";
        future3.resolve(str);
        assertTrue(future3.isDone());

    }


    @Test
    public void testfuture(){

        assertTrue(future2.isDone());
        TimeUnit time = TimeUnit.SECONDS;
        LocalTime date1= LocalTime.now();
        future2.get(10,time);
        LocalTime date2= LocalTime.now();
        LocalTime date3=date2.minus(10, ChronoUnit.SECONDS);
       if(!date3.isBefore(date1))
       {
           assertEquals(date3, date1);
       }

    }
}

