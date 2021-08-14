package de.syngenio.demo3;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestController {

	private Actor _actor;
	private Sensor _sensor;
	private Controller _controller;

	@BeforeEach
	public void setUp() throws Exception {
		_controller = new Controller();

		_sensor = spy(_controller._sensor);
		_actor  = spy(_controller._actor);
		_controller._sensor = _sensor;
		_controller._actor  = _actor;
	}

	@Test
	public void assureThatMotorIsStoppedWhenBlocked() {
		when(_sensor.isMotorBlocked()).thenReturn(true);

		_controller.singleDecision();

		verify(_actor).stopMotor();
		verify(_actor,times(0)).moveMotor(anyInt());
	}

	@Test
	public void assureThatMotorIsMovedCorrectly() {
		when(_sensor.isMotorBlocked()).thenReturn(false);
		// 1. bright, too warm
		when(_sensor.getBrightness()).thenReturn(15);
		when(_sensor.getTemperature()).thenReturn(30);
		_controller.singleDecision();
		verify(_actor).moveMotor(-10);

		// 2. bright, too cold
		when(_sensor.getBrightness()).thenReturn(15);
		when(_sensor.getTemperature()).thenReturn(15);
		_controller.singleDecision();
		verify(_actor).moveMotor(5);

		// ...

	}

}
