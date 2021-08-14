// constructor mocking
package de.syngenio.demo6;

public class Controller {
	
	private Sensor _sensor;
	private Actor  _actor;
	
	
	public Controller() {
		_sensor = new Sensor();
		_actor = new Actor();
	}
	
	public void singleDecision() {
		if (_sensor.isMotorBlocked()) {
			_actor.stopMotor();
		} else {
			int desiredTemperature;
			if (_sensor.getBrightness() < 10) {
				desiredTemperature = 15;
			} else {
				desiredTemperature = 20;
			}
			_actor.moveMotor(desiredTemperature - _sensor.getTemperature());
		}
	}
}
