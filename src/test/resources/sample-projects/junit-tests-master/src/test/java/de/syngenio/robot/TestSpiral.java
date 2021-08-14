package de.syngenio.robot;

import static org.mockito.Mockito.inOrder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestSpiral {
	
	@Mock private Robot _robot;
	private Spiral _spiral;

	@BeforeEach
	public void setUp() {
		_spiral = new Spiral(_robot);
	}
	
	@Test
	public void assureThatRobotWalksSpiral() {
		InOrder inorder = inOrder(_robot);
		
		_spiral.walkSpiral();
		
		inorder.verify(_robot).move(1);
		inorder.verify(_robot).turnleft(90);
		inorder.verify(_robot).move(1);
		inorder.verify(_robot).turnleft(90);

		inorder.verify(_robot).move(2);
		inorder.verify(_robot).turnleft(90);
		inorder.verify(_robot).move(2);
		inorder.verify(_robot).turnleft(90);

		inorder.verify(_robot).move(3);
		inorder.verify(_robot).turnleft(90);
		inorder.verify(_robot).move(3);
		inorder.verify(_robot).turnleft(90);

		inorder.verify(_robot).move(4);
		inorder.verify(_robot).turnleft(90);
		inorder.verify(_robot).move(4);
		inorder.verify(_robot).turnleft(90);

		inorder.verify(_robot).move(5);
		inorder.verify(_robot).turnleft(90);
		inorder.verify(_robot).move(5);
		inorder.verify(_robot).turnleft(90);
	}

}
