package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestryerEvent;
import bgu.spl.mics.application.messages.BroadcastImpl;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.passiveObjects.Attack;

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
	private  List<Queue<Callback>> allCallbacks;
	private  List<Queue<Message>> allMessages;
	private  int indexOfRoundRobin=0;
	private List<Thread> threads;
	 private List<MicroService> registeredMicroservices=null;
	private List<List<Boolean>> MessageQueuebool=null;


private List<Future> futureList;
private List<Event> eventOfFutures;

private int attackCounter=0;
private final int ammountOfEvents=4;

	private final int Broadcastlocation=0;
	private final int Attacklocation=1;
	private final int Bomblocation=2;
	private final int Deactivatelocation=3;



	public  List<Queue<Message>> getAllMessages()
		{
			return  allMessages;
		}

	public List<Queue<Callback>> getAllCallbacks() {
		return allCallbacks;
	}

	public MessageBusImpl()
	{
		allMessages=new LinkedList<Queue<Message>>();
		registeredMicroservices=new LinkedList<MicroService>();
		final int c=4;
		MessageQueuebool=new LinkedList<List<Boolean>>();
		futureList =new LinkedList<Future>();
		eventOfFutures=new LinkedList<Event>();
		threads=new LinkedList<Thread>();

	}
	public synchronized static MessageBusImpl getInstance() {

		return SingletonHolder.instance;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {

		if (registeredMicroservices!=null&&registeredMicroservices.contains(m)) {
			int i=registeredMicroservices.indexOf(m);
			//need to check if it registered at all
			//System.out.println(type.getName());
			//System.out.println(AttackEvent.class.getName());

			if (type.getName().equals(AttackEvent.class.getName())) {
				MessageQueuebool.get(i).set(Attacklocation, true);
				CallbackAttack c=new CallbackAttack ();
				//m.subscribeEvent(type,c);
			}

			if (type.getName().equals(BombDestryerEvent.class.getName())) {
				MessageQueuebool.get(i).set(Bomblocation, true);
				CallbackBombDestroyer c=new CallbackBombDestroyer ();
				//m.subscribeEvent(type,c);
			}

			if (type.getName().equals(DeactivationEvent.class.getName())) {
				MessageQueuebool.get(i).set(Deactivatelocation, true);
				CallbackDeactivation c=new CallbackDeactivation ();
			//	m.subscribeEvent(type,c);
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

	public int getAttackCounter() {
		return attackCounter;
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
		if(e.getClass().getName().equals(AttackEvent.class.getName()))
		{
			attackCounter++;
		}

		/*
		for the list of micro
		check if the event type is like e
		if so, check if if e is there
		 we need to delete the message

		 */
	}

	@Override
	public synchronized void sendBroadcast(Broadcast b) {
		for (int i=0;i<MessageQueuebool.size();i++)
		{
			if(MessageQueuebool.get(i).get(Broadcastlocation)) {
				//int index = MessageQueuebool.indexOf(i);
				allMessages.get(i).add(b);
			//	Thread.currentThread().notifyAll();


			}
		/*
		MessageQueuebool.forEach((temp) ->{
			if(temp.get(Broadcastlocation))
			{
				int index=MessageQueuebool.indexOf(temp);
				allMessages.get(index).add(b);
				notifyAll();
			}

				}

				);*/


		}
		MessageBusImpl.getInstance().notifyAll();
	}


	
	@Override
	 public synchronized <T> Future<T> sendEvent(Event<T> e) {
		Future<T> f= new Future();
		if (e.getClass()==AttackEvent.class) {
			allMessages.get(indexOfRoundRobin).add(e);
			int currentsize = registeredMicroservices.size();
			//notifyAll();
			threads.get(indexOfRoundRobin).interrupt();
			//registeredMicroservices.get(indexOfRoundRobin)

			while (true) {
				if (indexOfRoundRobin < currentsize)
					indexOfRoundRobin++;
				else
					indexOfRoundRobin = 0;

				if (indexOfRoundRobin < currentsize)
					if (MessageQueuebool.get(indexOfRoundRobin).get(Attacklocation)) {
						break;
					}
			}




		}
        else{
        	if (e.getClass()==DeactivationEvent.class)
			{
				for (int i=0;i<registeredMicroservices.size();i++)
				{
					if(MessageQueuebool.get(i).get(Deactivatelocation))
					{
						allMessages.get(i).add(e);
						threads.get(i).interrupt();
					}
				}
			}
        	else {
				for (int i=0;i<registeredMicroservices.size();i++)
				{
					if(MessageQueuebool.get(i).get(Bomblocation))
					{
						allMessages.get(i).add(e);
						threads.get(i).interrupt();
					}
				}
			}
		}

		eventOfFutures.add(e);
		futureList.add(f);
        return f;
	}

	@Override
	public synchronized void  register(MicroService m) {
		/*if(registeredMicroservices.size()==0)
		{
			registeredMicroservices=new LinkedList<MicroService>();

		}*/
		threads.add(Thread.currentThread());
		registeredMicroservices.add(m);
		MessageQueuebool.add(new LinkedList<Boolean>());
		for (int i=0;i<ammountOfEvents;i++)
			MessageQueuebool.get(MessageQueuebool.size()-1).add(false);
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
	public synchronized Message  awaitMessage(MicroService m) throws InterruptedException {
		 while (!Thread.currentThread().isInterrupted()) {
			 if (!registeredMicroservices.contains(m))
				 throw new IllegalStateException();


			 int i = registeredMicroservices.indexOf(m);
			 Queue<Message> incommingMessages = allMessages.get(i);
			 boolean b = incommingMessages.isEmpty();
			 Object key = new Object();
			 Message thisMessage=null;
					 while(thisMessage==null)
			 {
			 synchronized (key) {
				 if (b) {
					 try {
						 //		Thread.sleep(10);

						 wait();

					 } catch (InterruptedException e) {
						 //notify();



					 }

				 }
			 }
				  thisMessage = incommingMessages.poll();
		 }



///TODO: think about other option
				if (thisMessage.getClass() == AttackEvent.class) {
					AttackEvent Aevent = (AttackEvent) thisMessage;
					//       Attack currentAttack=new Attack(attackEvent.getSerialNumbers(),attackEvent.getDuration()); //
					Aevent.getCallback().call(Aevent);
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
				if (thisMessage.getClass() == BroadcastImpl.class) {
					BroadcastImpl BroadcastImpl = (BroadcastImpl) thisMessage;
					//       Attack currentAttack=new Attack(attackEvent.getSerialNumbers(),attackEvent.getDuration()); //
					BroadcastImpl.getCallback().call(m);
				}

				return thisMessage;



	}
		 wait();
		return null;


	}
}

