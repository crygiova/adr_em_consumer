package fi.aalto.itia.consumer;

import fi.aalto.itia.adr_em_common.ADR_EM_Common;
import fi.aalto.itia.adr_em_common.SimulationElement;
import fi.aalto.itia.adr_em_common.SimulationMessageFactory;

public class ADRConsumer extends SimulationElement {

	public ADRConsumer(String inputQueueName) {
		super(inputQueueName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4328592954437775437L;

	@Override
	public void run() {
		this.startConsumingMq();
		//TODO init procedure ofthe consumer
		int i = 0;
		//TODO Registration message (it could be that in the content of the registration message there is already the first update)
		System.out.println("MyQueue: " + inputQueueName);
		//TODO think how to monitor the frequency (it could be done centrally by he main consumer every second and there could be a method to call)
		
		//TODO decide also how often the updates are sent, and with which policy (frequency & every status change?)
		while (i++ < 2) {
			try {
				this.sendMessage(SimulationMessageFactory.getRegisterMessage(
						this.inputQueueName, ADR_EM_Common.AGG_INPUT_QUEUE));
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void scheduleTasks() {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeTasks() {
		// TODO Auto-generated method stub

	}

	@Override
	public void elaborateIncomingMessages() {
		// TODO Auto-generated method stub

	}

}
