package bgu.spl.mics.application;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;


import java.io.File;
import java.io.IOException;
import java.util.*;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		//Input

		long R2D2=0;
		long Lando=0;
		int Ewoksnumbers=0;
		ArrayList attacksInput=null;
		File f = new File("test");
		System.out.println(f.getAbsolutePath());



		String test="C:/Users/Milky/IdeaProjects/spl2/input.json";


			Map map= InOutputJsonClass.Read(args[0]);
		attacksInput=InOutputJsonClass.readAttack(map);
		R2D2=(long) InOutputJsonClass.readOther(map,"R2D2");
		Lando=(long)InOutputJsonClass.readOther(map,"Lando");
		Ewoksnumbers=(int)InOutputJsonClass.readOther(map,"Ewoks");

		Ewoks ewoks=new Ewoks();
		List<Ewok> ewokList=new LinkedList<>();
		for (int i=1;i<=Ewoksnumbers;i++)
		{
			Ewok e=new Ewok(i);
			ewokList.add(e);

		}
		Ewoks.getInstance().add(ewokList);


	/*	attacksInput.forEach((n) -> {
			System.out.println(n.toString());
		});*/

	//	Object attackss= attacksInput.toArray();
		//Attack object = new Gson().fromJson(new Gson().toJson(((LinkedTreeMap<String, Object>) theLinkedTreeMapObject)), Attack .class);
		Attack [] attacks=new Attack[attacksInput.size()];
		final int[] i = {0};
		ArrayList finalAttacksInput = attacksInput;
		attacksInput.forEach((n) -> {
		Attack at=new Attack(finalAttacksInput,n);
		attacks[i[0]]=at;
		i[0]++;
		});


		//initialize all services
		MessageBusImpl messageBus=new MessageBusImpl();
		C3POMicroservice C3POMicroservice= new C3POMicroservice(messageBus);
		Thread C3POMicroserviceThread= new Thread(C3POMicroservice);
		C3POMicroserviceThread.start();
		//C3POMicroserviceThread.run();

		/*try {
			Thread.sleep(100);
		} catch (InterruptedException e) { }


		C3POMicroserviceThread.interrupt();*/

		HanSoloMicroservice HanSoloMicroservice= new HanSoloMicroservice(messageBus);
		Thread HanSoloMicroserviceThread= new Thread(HanSoloMicroservice);
		HanSoloMicroserviceThread.start();
		//HanSoloMicroserviceThread.run();



		LandoMicroservice LandoMicroservice = new LandoMicroservice(Lando); //TODO get from input //destroy
		Thread LandoMicroserviceThread = new Thread(LandoMicroservice);
		LandoMicroserviceThread.start();
		LeiaMicroservice LeiaMicroservice=new LeiaMicroservice(attacks); //TODO it make attackevent from input
		Thread LeiaMicroserviceThread= new Thread(LeiaMicroservice);
		LeiaMicroserviceThread.start();
		R2D2Microservice R2D2Microservice =new R2D2Microservice(R2D2); //TODO get from input //deactivate
		Thread R2D2MicroserviceThread=new Thread(R2D2Microservice);
		R2D2MicroserviceThread.start();

		while (R2D2MicroserviceThread.getState()!= Thread.State.WAITING || LeiaMicroserviceThread.getState()!= Thread.State.WAITING ||C3POMicroserviceThread.getState()!=Thread.State.WAITING
		     || HanSoloMicroserviceThread.getState()!=Thread.State.WAITING || LandoMicroserviceThread.getState()!=Thread.State.WAITING)
		//do stuff
		{

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		LeiaMicroservice.startAttack();

		//to stop everything



		//
		while (R2D2MicroserviceThread.getState()== Thread.State.TERMINATED && LeiaMicroserviceThread.getState()== Thread.State.TERMINATED &&C3POMicroserviceThread.getState()==Thread.State.TERMINATED
				&& HanSoloMicroserviceThread.getState()==Thread.State.TERMINATED && LandoMicroserviceThread.getState()==Thread.State.TERMINATED)
		//do stuff
		{

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		InOutputJsonClass.output();
	}
}
