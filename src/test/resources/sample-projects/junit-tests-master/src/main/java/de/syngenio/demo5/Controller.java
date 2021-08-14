// exceptions and testing
package de.syngenio.demo5;

public class Controller {
	
	private Sensor _sensor;
	private Actor _actor;
	
	public Controller(InfrastructureFactory infrastructureFactory) {
		_sensor = infrastructureFactory.obtainSensor();
		_actor = infrastructureFactory.obtainActor();
		
	}
	
	public void singleDecision() {
		try {
			if (_sensor.isMotorBlocked()) {
				_actor.stopMotor();
				throw new RuntimeException("Motor blocked, stopping system!");
			} else {
				int desiredTemperature;
				if (_sensor.getBrightness() < 10) {
					desiredTemperature = 15;
				} else {
					desiredTemperature = 20;
				}
				_actor.moveMotor(desiredTemperature - _sensor.getTemperature());
			}
		} finally {
			_actor.waitUntilLastActionFinished();
		}
	}
}
