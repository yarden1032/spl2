package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestryerEvent;
import bgu.spl.mics.application.messages.BroadcastImpl;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.services.*;

/**
 * The MicroService is an abstract class that any micro-service in the system
 * must extend. The abstract MicroService class is responsible to get and
 * manipulate the singleton {@link MessageBus} instance.
 * <p>
 * Derived classes of MicroService should never directly touch the message-bus.
 * Instead, they have a set of internal protected wrapping methods (e.g.,
 * {@link #sendBroadcast(bgu.spl.mics.Broadcast)}, {@link #sendBroadcast(bgu.spl.mics.Broadcast)},
 * etc.) they can use. When subscribing to message-types,
 * the derived class also supplies a {@link Callback} that should be called when
 * a message of the subscribed type was taken from the micro-service
 * message-queue (see {@link MessageBus#register(bgu.spl.mics.MicroService)}
 * method). The abstract MicroService stores this callback together with the
 * type of the message is related to.
 * 
 * Only private fields and methods may be added to this class.
 * <p>
 */
public abstract class MicroService implements Runnable { 

    private String name;
   /* private int index=MessageBusImpl.getInstance().getAllMessages().indexOf(this);
    private Queue<Message> incommingMessages=MessageBusImpl.getInstance().getAllMessages().get(index);*/
    /**
     * @param name the micro-service name (used mainly for debugging purposes -
     *             does not have to be unique)
     */
    public MicroService(String name) {
    	this.name=name;


    }

    /**
     * Subscribes to events of type {@code type} with the callback
     * {@code callback}. This means two things:
     * 1. Subscribe to events in the singleton event-bus using the supplied
     * {@code type}
     * 2. Store the {@code callback} so that when events of type {@code type}
     * are received it will be called.
     * <p>
     * For a received message {@code m} of type {@code type = m.getClass()}
     * calling the callback {@code callback} means running the method
     * {@link Callback#call(java.lang.Object)} by calling
     * {@code callback.call(m)}.
     * <p>
     * @param <E>      The type of event to subscribe to.
     * @param <T>      The type of result expected for the subscribed event.
     * @param type     The {@link Class} representing the type of event to
     *                 subscribe to.
     * @param callback The callback that should be called when messages of type
     *                 {@code type} are taken from this micro-service message
     *                 queue.
     */
    protected final <T, E extends Event<T>> void subscribeEvent(Class<E> type, Callback<E> callback) {

        MessageBusImpl.getInstance().subscribeEvent(type,this);

        /*when we subscribe we also save the callback here in a list
        when we get a message we also get a callback and then just run the callback
        */

    }

    /**E==EVENTATTACK
     * Subscribes to broadcast message of type {@code type} with the callback
     * {@code callback}. This means two things:
     * 1. Subscribe to broadcast messages in the singleton event-bus using the
     * supplied {@code type}
     * 2. Store the {@code callback} so that when broadcast messages of type
     * {@code type} received it will be called.
     * <p>
     * For a received message {@code m} of type {@code type = m.getClass()}
     * calling the callback {@code callback} means running the method
     * {@link Callback#call(java.lang.Object)} by calling
     * {@code callback.call(m)}.
     * <p>
     * @param <B>      The type of broadcast message to subscribe to
     * @param type     The {@link Class} representing the type of broadcast
     *                 message to subscribe to.
     * @param callback The callback that should be called when messages of type
     *                 {@code type} are taken from this micro-service message
     *                 queue.
     */
    protected final <B extends Broadcast> void subscribeBroadcast(Class<B> type, Callback<B> callback) {
    	
    }

    /**
     * Sends the event {@code e} using the message-bus and receive a {@link Future<T>}
     * object that may be resolved to hold a result. This method must be Non-Blocking since
     * there may be events which do not require any response and resolving.
     * <p>
     * @param <T>       The type of the expected result of the request
     *                  {@code e}
     * @param e         The event to send
     * @return  		{@link Future<T>} object that may be resolved later by a different
     *         			micro-service processing this event.
     * 	       			null in case no micro-service has subscribed to {@code e.getClass()}.
     */
    protected final <T> Future<T> sendEvent(Event<T> e) {

        Future<T> f=  MessageBusImpl.getInstance().sendEvent(e);


        return f;
    }

    /**
     * A Micro-Service calls this method in order to send the broadcast message {@code b} using the message-bus
     * to all the services subscribed to it.
     * <p>
     * @param b The broadcast message to send
     */
    protected final void sendBroadcast(Broadcast b) {
        MessageBusImpl.getInstance().sendBroadcast(b);

    }

    /**
     * Completes the received request {@code e} with the result {@code result}
     * using the message-bus.
     * <p>
     * @param <T>    The type of the expected result of the processed event
     *               {@code e}.
     * @param e      The event to complete.
     * @param result The result to resolve the relevant Future object.
     *               {@code e}.
     */
    protected final <T> void complete(Event<T> e, T result) {
    	MessageBusImpl.getInstance().complete(e,result);
    }

    /**
     * this method is called once when the event loop starts.
     */
    protected abstract void initialize();

    /**
     * Signals the event loop that it must terminate after handling the current
     * message.
     */
    protected final void terminate() {
        Diary.getInstance().setLittleDiary(name+"Terminate",System.currentTimeMillis());
        Thread.currentThread().interrupt();

    }

    /**
     * @return the name of the service - the service name is given to it in the
     *         construction time and is used mainly for debugging purposes.
     */
    public final String getName() {
        return name;
    }

    /**
     * The entry point of the micro-service. TODO: you must complete this code
     * otherwise you will end up in an infinite loop.
     */
    @Override
    public final void run() {
        initialize();
        while (true) {
            try {

                Message current = MessageBusImpl.getInstance().awaitMessage(this);

                if (current!=null) {

                    if ("Han".equals(getName()) && current.getClass() == AttackEvent.class) {
                        ((HanSoloMicroservice) this).InitiateAttack(current);
                    }

                    if (("C3PO".equals(getName()) && current.getClass() == AttackEvent.class)) {
                        ((C3POMicroservice) this).InitiateAttack(current);

                    }
                    if (("R2D2".equals(getName()) && current.getClass() == DeactivationEvent.class)) {
                        ((R2D2Microservice) this).InitiateDeactivation();
                    }
                    if (("Lando".equals(getName()) && current.getClass() == BombDestryerEvent.class)) {
                        ((LandoMicroservice) this).InitiateBombardment();
                    }
                    if (current.getClass() == BroadcastImpl.class) {
                        if (this.getName().equals("Leia")) {
                            ((LeiaMicroservice) this).handleBoradcast(current);
                        }
                        BroadcastImpl b = (BroadcastImpl) current;
                        if (b.getWhoSendIt().equals("Leia")) {
                            terminate();

                        }


                    }
                }
                else {
                  /*  Object obj=new Object();
                    synchronized (obj){*/



                  //  MessageBusImpl.getInstance().wait();

                }


            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();


            }



        }
    }

}
