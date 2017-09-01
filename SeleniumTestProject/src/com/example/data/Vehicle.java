/**
 * 
 */
package com.example.data;

/**
 * @author Gaurav Nigam
 *
 */
public class Vehicle {

	private String registrationNumber;
	private String make;
	private String colour;
	
	
	/**
	 * @param registrationNumber
	 * @param make
	 * @param colour
	 */
	public Vehicle(String registrationNumber, String make, String colour) {
		super();
		this.registrationNumber = registrationNumber;
		this.make = make;
		this.colour = colour;
	}
	/**
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}
	/**
	 * @return the colour
	 */
	public String getColour() {
		return colour;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vehicle [registrationNumber=" + registrationNumber + ", make=" + make + ", colour=" + colour + "]";
	}
	
	
}
