package de.syngenio.demo6;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class TestController {

	private Actor _actor;
	private Sensor _sensor;

	private Controller _controller;

	@BeforeEach
	public void setUp() throws Exception {
		try (MockedConstruction<Actor> actorMock = mockConstruction(Actor.class)) {
			try (MockedConstruction<Sensor> sensorMock = mockConstruction(Sensor.class)) {
				_controller = new Controller();
				_actor = actorMock.constructed().get(0);
				_sensor = sensorMock.constructed().get(0);
			}
		}

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
