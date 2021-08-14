package de.syngenio.demo5;

public interface Actor {
	public void moveMotor(int amount);
	public void stopMotor();
	public void waitUntilLastActionFinished();
}
