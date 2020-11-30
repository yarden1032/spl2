package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Ewok;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
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
        assertTrue(str.equals(future.get()));
    }


    @Test
    public void testisDone() {
        assertTrue(!future3.isDone());
        String str = "someResult";
        future3.resolve(str);
        assertTrue(future3.isDone());

    }


    @Test
    public void testfuture(){
        String str = "someResult";
        assertTrue(future2.isDone());
        TimeUnit time = TimeUnit.SECONDS;
        LocalTime date1= LocalTime.now();
        future2.get(10,time);
        LocalTime date2= LocalTime.now();
        LocalTime date3=date2.minus(10, ChronoUnit.SECONDS);
       if(!date3.isBefore(date1))
       {
           assertTrue(date1.equals(date3));
       }

    }
}

