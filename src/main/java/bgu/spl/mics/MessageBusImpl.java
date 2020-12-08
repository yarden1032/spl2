package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestryerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import sun.jvm.hotspot.utilities.MessageQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	//list of queue
	private static class SingletonHolder{
	private static MessageBusImpl instance = new MessageBusImpl();
	}

	private  List<Queue<Message>> allMessages;
	private  int indexOfRoundRobin=0;

	 private List<MicroService> registeredMicroservices=null;
	private List<List<Boolean>> MessageQueuebool=null;


private List<Future> futureList;
private List<Event> eventOfFutures;




	private final int Broadcastlocation=0;
	private final int Attacklocation=1;
	private final int Bomblocation=2;
	private final int Deactivatelocation=3;



	public  List<Queue<Message>> getAllMessages()
		{
			return  allMessages;
		}

	public MessageBusImpl()
	{
		allMessages=new LinkedList<Queue<Message>>();
		registeredMicroservices=new LinkedList<MicroService>();
		MessageQueuebool=new LinkedList<List<Boolean>>();
		futureList =new LinkedList<Future>();
		eventOfFutures=new LinkedList<Event>();

	}
	public synchronized static MessageBusImpl getInstance() {

		return SingletonHolder.instance;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {

		if (registeredMicroservices!=null&&registeredMicroservices.contains(m)) {
			int i=registeredMicroservices.indexOf(m);
			//need to check if it registered at all
			if (type == AttackEvent.class) {
				MessageQueuebool.get(i).set(Attacklocation, true);
				CallbackAttack c=new CallbackAttack ();
				m.subscribeEvent(type,c);
			}
			if (type == BombDestryerEvent.class) {
				MessageQueuebool.get(i).set(Bomblocation, true);
				CallbackBombDestroyer c=new CallbackBombDestroyer ();
				m.subscribeEvent(type,c);
			}
			if (type == DeactivationEvent.class) {
				MessageQueuebool.get(i).set(Deactivatelocation, true);
				CallbackDeactivation c=new CallbackDeactivation ();
				m.subscribeEvent(type,c);
			}

		}



	}


	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if (registeredMicroservices != null && registeredMicroservices.contains(m)) {
			int i = registeredMicroservices.indexOf(m);
			if (type == Broadcast.class) {
				MessageQueuebool.get(i).set(Broadcastlocation, true);
			}

		}



	}
	@Override
	public <T> void complete(Event<T> e, T result) {
		int i=eventOfFutures.indexOf(e);
		futureList.get(i).resolve(result);

		futureList.remove(i);
		eventOfFutures.remove(i);

		allMessages.forEach((temp) ->{

			temp.remove(e);

		});



		/*
		for the list of micro
		check if the event type is like e
		if so, check if if e is there
		 we need to delete the message

		 */
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		MessageQueuebool.forEach((temp) ->{
			if(temp.get(Broadcastlocation))
			{
				int index=MessageQueuebool.indexOf(temp);
				allMessages.get(index).add(b);
				registeredMicroservices.get(index).notify();
			}

				}

				);


		}


	
	@Override
	 public synchronized <T> Future<T> sendEvent(Event<T> e) {
		Future<T> f= new Future();
		allMessages.get(indexOfRoundRobin).add(e);
		int currentsize= registeredMicroservices.size();
		registeredMicroservices.get(indexOfRoundRobin).notify();

		while (true)
		{
			if(indexOfRoundRobin<currentsize)
			indexOfRoundRobin++;
			else
				indexOfRoundRobin=0;

			if(MessageQueuebool.get(indexOfRoundRobin).get(Attacklocation))
			{
				break;
			}
		}

		eventOfFutures.add(e);
		futureList.add(f);
        return f;
	}

	@Override
	public void  register(MicroService m) {
		if(registeredMicroservices==null)
		{
			registeredMicroservices=new LinkedList<MicroService>();
			MessageQueuebool=new LinkedList<List<Boolean>>();
		}
		registeredMicroservices.add(m);
		List<Boolean> listOfboolean=new LinkedList<Boolean>();
		MessageQueuebool.add(listOfboolean);
		allMessages.add(new LinkedList<Message>() );
	}

	@Override
	public void unregister(MicroService m) {
		if (registeredMicroservices != null) {
			int i = registeredMicroservices.indexOf(m);
			registeredMicroservices.remove(m);
			MessageQueuebool.remove(i);
			allMessages.remove(i);
		}
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		if(!registeredMicroservices.contains(m))
			throw new IllegalStateException();



		int i=registeredMicroservices.indexOf(m);
		Queue<Message> incommingMessages=allMessages.get(i);
		if (incommingMessages.isEmpty())
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}


			Message  thisMessage = incommingMessages.poll();

///TODO: think about other option
			if (thisMessage.getClass() == AttackEvent.class) {
				AttackEvent Aevent = (AttackEvent) thisMessage;
				//       Attack currentAttack=new Attack(attackEvent.getSerialNumbers(),attackEvent.getDuration()); //
				Aevent.getCallback().call(Aevent.getDuration());
			}
			if (thisMessage.getClass() == BombDestryerEvent.class) {
				BombDestryerEvent bombevent = (BombDestryerEvent) thisMessage;
				//       Attack currentAttack=new Attack(attackEvent.getSerialNumbers(),attackEvent.getDuration()); //
				bombevent.getCallback().call(m);
			}
			if (thisMessage.getClass() == DeactivationEvent.class) {
				DeactivationEvent deactivateevent = (DeactivationEvent) thisMessage;
				//       Attack currentAttack=new Attack(attackEvent.getSerialNumbers(),attackEvent.getDuration()); //
				deactivateevent.getCallback().call(m);
			}
			return thisMessage;





	}




	}

