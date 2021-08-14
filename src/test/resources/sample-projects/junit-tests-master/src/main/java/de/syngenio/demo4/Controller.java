// using factories
package de.syngenio.demo4;

public class Controller {
	
	private Sensor _sensor;
	private Actor _actor;
	
	public Controller(InfrastructureFactory infrastructureFactory) {
		_sensor = infrastructureFactory.obtainSensor();
		_actor = infrastructureFactory.obtainActor();
		
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
