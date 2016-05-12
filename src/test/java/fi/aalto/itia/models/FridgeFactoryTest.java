package fi.aalto.itia.models;

import java.util.ArrayList;

import org.junit.Test;

public class FridgeFactoryTest {

	@Test
	public void testGetFridge() {
		FridgeModel fridgeModel = FridgeFactory.getFridge();
		System.out.println(fridgeModel.toString());
	}

	@Test
	public void testGetArrayFridge() {
		ArrayList<FridgeModel> a = FridgeFactory.getNFridges(1);
		for (FridgeModel fridgeModel : a) {
			System.out.println(fridgeModel.toString());
		}
		int i = 0;
		int prevI = 0;
		boolean prev = false;
		while (i++ < 150000) {
			for (FridgeModel fridgeModel : a) {
				fridgeModel.updateTemperature();
				prev = fridgeModel.isCurrentOn();
				// T > Tmax and notOn
				if (fridgeModel.getCurrentTemperature() > (fridgeModel
						.getTemperatureSP() + fridgeModel.getThermoBandDT())
						&& !fridgeModel.isCurrentOn())
					fridgeModel.switchOn();
				// T < Tmin and On
				if (fridgeModel.getCurrentTemperature() < (fridgeModel
						.getTemperatureSP() - fridgeModel.getThermoBandDT())
						&& fridgeModel.isCurrentOn())
					fridgeModel.switchOff();

				if (prev != fridgeModel.isCurrentOn()) {
					System.out.println(i - prevI);
					prevI = i;
					System.out.println(fridgeModel.getCurrentTemperature()
							+ " O:" + fridgeModel.isCurrentOn());
				}
			}
		}
	}

}
