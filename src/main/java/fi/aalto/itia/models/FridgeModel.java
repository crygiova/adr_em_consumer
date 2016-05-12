package fi.aalto.itia.models;

import java.util.ArrayList;

import org.apache.commons.math3.analysis.function.Exp;

public class FridgeModel {
	// COEFFICIENTS OF THE FRIDGE
	/** Coefficient of performance */
	private double coeffOfPerf;
	/** Rated Power (w) */
	private double ptcl;
	/** minimum thermal mass (kWh/celcius) */
	private double mcMin;
	/** maximum thermal mass (kWh/celcius) */
	private double mcMax;
	/** thermal conductance (W/C) */
	private double thermalConductanceA;
	/** time constant */
	private double tau;
	/** temperature set-point (celcius) */
	private double temperatureSP;
	/** thermostat bandwidth (celcius) */
	private double thermoBandDT;
	/** ambient temperature */
	private double tAmb;
	/** initial temperature */
	private double t0;
	/** initial on/off state */
	private double q0;

	// COEFFICIENTS FOR TEMPERATURE EVOLUTION OF THE FRIDGE
	private final Double A;
	private final Double B;
	private final Double E;

	// TODO do I need a long counter???

	// VARIABLES
	// TODO decide if this is a list of keep track only of the current
	// temperature
	/** temperature list */
	private ArrayList<Double> temperatureList;
	/** temperature at a given time t */
	private Double currentTemperature;
	/** power list */
	private ArrayList<Double> electricPowerList;
	/** electric power at a given time t */
	private Double currentElectricPower;
	/** temperature at a given time t */
	private ArrayList<Boolean> onOffList;
	/** temperature at a given time t */
	private boolean currentOnOff;

	/**
	 * @param coeffOfPerf
	 * @param ptcl
	 * @param mcMin
	 * @param mcMax
	 * @param thermalConductanceA
	 * @param tau
	 * @param temperatureSP
	 * @param thermoBandDT
	 * @param tAmb
	 * @param t0
	 * @param q0
	 */
	public FridgeModel(double coeffOfPerf, double ptcl, double mcMin,
			double mcMax, double thermalConductanceA, double tau,
			double temperatureSP, double thermoBandDT, double tAmb, double t0,
			double q0) {
		super();
		this.coeffOfPerf = coeffOfPerf;
		this.ptcl = ptcl;
		this.mcMin = mcMin;
		this.mcMax = mcMax;
		this.thermalConductanceA = thermalConductanceA;
		this.tau = tau;
		this.temperatureSP = temperatureSP;
		this.thermoBandDT = thermoBandDT;
		this.tAmb = tAmb;
		this.t0 = t0;
		this.q0 = q0;

		// TODO There is a problem here exp(-dt*tau) where dt is the disc time
		// step!!!!
		// HOw TODO this?? 1 if 1 sec time step!
		this.E = new Exp().value(-tau);
		this.A = 1d - this.E;
		this.B = coeffOfPerf / thermalConductanceA * ptcl;

		// Init variables
		this.currentTemperature = t0;
		this.currentElectricPower = q0;
		if (q0 > 0d) {
			// ON
			this.currentOnOff = true;
		} else {
			// OFF
			this.currentOnOff = false;
		}
	}

	public double getCoeffOfPerf() {
		return coeffOfPerf;
	}

	public double getPtcl() {
		return ptcl;
	}

	public double getMcMin() {
		return mcMin;
	}

	public double getMcMax() {
		return mcMax;
	}

	public double getThermalConductanceA() {
		return thermalConductanceA;
	}

	public double getTau() {
		return tau;
	}

	public double getTemperatureSP() {
		return temperatureSP;
	}

	public double getThermoBandDT() {
		return thermoBandDT;
	}

	public double gettAmb() {
		return tAmb;
	}

	public double getT0() {
		return t0;
	}

	public double getQ0() {
		return q0;
	}

	public Double getCurrentTemperature() {
		return currentTemperature;
	}

	public Double getCurrentElectricPower() {
		return currentElectricPower;
	}

	public boolean isCurrentOn() {
		return currentOnOff;
	}

	// Switch on the fridge
	public void switchOn() {
		this.currentOnOff = true;
		this.currentElectricPower = ptcl;
	}

	// Switch on the fridge
	public void switchOff() {
		this.currentOnOff = false;
		this.currentElectricPower = 0d;
	}

	// Temperature Update Function
	// TODO Olli suggests to add some randomness
	public void updateTemperature() {
		int bufferOnOff = 0;
		if (currentOnOff)
			bufferOnOff = 1;
		this.currentTemperature = this.E * this.currentTemperature + this.A
				* (this.tAmb - this.B * bufferOnOff);
	}

	@Override
	public String toString() {
		return "FridgeModel [coeffOfPerf=" + coeffOfPerf + ", ptcl=" + ptcl
				+ ", mcMin=" + mcMin + ", mcMax=" + mcMax
				+ ", thermalConductanceA=" + thermalConductanceA + ", tau="
				+ tau + ", temperatureSP=" + temperatureSP + ", thermoBandDT="
				+ thermoBandDT + ", tAmb=" + tAmb + ", t0=" + t0 + ", q0=" + q0
				+ ", A=" + A + ", B=" + B + ", E=" + E
				+ ", currentTemperature=" + currentTemperature
				+ ", currentElectricPower=" + currentElectricPower
				+ ", currentOnOff=" + currentOnOff + "]";
	}
}
