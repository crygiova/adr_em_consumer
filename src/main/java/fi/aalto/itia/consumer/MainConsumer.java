package fi.aalto.itia.consumer;

import java.util.ArrayList;
import java.util.Properties;

import fi.aalto.itia.adr_em_common.SimulationElement;
import fi.aalto.itia.util.Utility;

public class MainConsumer {

	private static final String FILE_NAME_PROPERTIES = "config.properties";
	private static final String NUMBER_OF_CONSUMERS = "N_CONSUMERS";
	private static Properties properties;

	private static Integer numberOfConsumers = 0;
	/**
	 * ArrayList which will contain all the Simulation elements
	 */
	public static ArrayList<SimulationElement> simulationElements = new ArrayList<SimulationElement>();
	/**
	 * ArrayList of Threads Objects for each simulation element
	 */
	public static ArrayList<Thread> threads = new ArrayList<Thread>();

	static {
		properties = Utility.getProperties(FILE_NAME_PROPERTIES);
		numberOfConsumers = Integer.parseInt(properties
				.getProperty(NUMBER_OF_CONSUMERS));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Consumer App START");
		initConsumers();
		startThreads();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		endOfSimulation();
		System.out.println("Consumer App STOP");
	}
	
	public static void initConsumers() {
		// add Consumers
		simulationElements = new ArrayList<SimulationElement>();
		// Add as much as clients you want theoretically
		for (int i = 0; i < numberOfConsumers; i++) {
			simulationElements.add(i, new ADRConsumer("DR_C_" + i));
		}
	}
	
	public static void startThreads() {
		for (SimulationElement r : simulationElements) {
			threads.add(new Thread(r));
		}
		for (Thread thread : threads) {
			thread.start();
		}
	}
	
	/**
	 * Procedure which will end the simulation of all the SimulationElements
	 */
	public synchronized static void endOfSimulation() {
		for (SimulationElement simulationElement : simulationElements) {
			simulationElement.setKeepGoing(false);
			simulationElement.closeConnection();
		}
	}
}
