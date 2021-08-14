package de.syngenio.robot;

public class Spiral {

	private Robot _robot;

	public Spiral(Robot robot) {
		_robot = robot;
	}
	
	public void walkSpiral() {
		_robot.move(1);
		_robot.turnleft(90);
		_robot.move(1);
		_robot.turnleft(90);

		_robot.move(2);
		_robot.turnleft(90);
		_robot.move(2);
		_robot.turnleft(90);

		_robot.move(3);
		_robot.turnleft(90);
		_robot.move(3);
		_robot.turnleft(90);

		_robot.move(4);
		_robot.turnleft(90);
		_robot.move(4);
		_robot.turnleft(90);

		_robot.move(5);
		_robot.turnleft(90);
		_robot.move(5);
		_robot.turnleft(90);
	}
}
