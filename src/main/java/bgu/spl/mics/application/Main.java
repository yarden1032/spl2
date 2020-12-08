package bgu.spl.mics.application;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.services.*;


import java.util.Collections;
import java.util.List;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		//Input

		//initialize all services
		MessageBusImpl messageBus=new MessageBusImpl();
		C3POMicroservice C3POMicroservice= new C3POMicroservice(messageBus);
		C3POMicroservice.run();

		HanSoloMicroservice HanSoloMicroservice= new HanSoloMicroservice(messageBus);
		HanSoloMicroservice.run();
		LandoMicroservice LandoMicroservice = new LandoMicroservice(1000); //TODO get from input //destroy
		LandoMicroservice.run();
		LeiaMicroservice LeiaMicroservice=null ;//=new LeiaMicroservice(); //TODO it make attackevent from input
		LeiaMicroservice.run();
		R2D2Microservice R2D2Microservice =new R2D2Microservice(1000); //TODO get from input //deactivate
		R2D2Microservice.run();
		//


	}
}
