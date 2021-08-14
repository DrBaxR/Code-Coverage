package de.syngenio.demo4;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestController {

	private Actor _actor;
	private Sensor _sensor;
	private Controller _controller;
	private InfrastructureFactory _factory;

	@BeforeEach
	public void setUp() throws Exception {
		_actor = mock(Actor.class);
		_sensor = mock(Sensor.class);
		_factory = mock(InfrastructureFactory.class);
		when(_factory.obtainActor()) .thenReturn(_actor);
		when(_factory.obtainSensor()).thenReturn(_sensor);
		_controller = new Controller(_factory);
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
