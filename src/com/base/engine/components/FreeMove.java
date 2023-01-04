package com.base.engine.components;

import org.lwjgl.input.Keyboard;

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;

public class FreeMove extends GameComponent {
	private float speed;
	private int forwardKey;
	private int backKey;
	private int leftKey;
	private int rightKey;
	private int upKey;
	private int downKey;
	
	public FreeMove(float speed) {
		this(speed, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_SPACE, Keyboard.KEY_LSHIFT);
	}
	
	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
		this(speed, forwardKey, backKey, leftKey, rightKey, 0, 0);
	}
	
	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey, int upKey, int downKey) {
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.upKey = upKey;
		this.downKey = downKey;
	}
	
	@Override
	public void input(float delta) {	
		float moveAmt = speed * delta;
		
		if (Input.getKey(Keyboard.KEY_LCONTROL)) {
			moveAmt = speed * delta / 100;
		}
		if (Input.getKey(forwardKey)) {
			move(getTransform().getRot().getForward(), moveAmt);
		}
		if (Input.getKey(leftKey)) {
			move(getTransform().getRot().getLeft(), moveAmt);
		}
		if (Input.getKey(backKey)){
			move(getTransform().getRot().getBack(), moveAmt);
		}
		if (Input.getKey(rightKey)){
			move(getTransform().getRot().getRight(), moveAmt);
		}			
		if (Input.getKey(upKey)) {
			move(new Vector3f(0,1,0), moveAmt);
		}
		if (Input.getKey(downKey)) {
			move(new Vector3f(0,1,0), -moveAmt);
		}
	}
	
	public void move(Vector3f dir, float amt) {
		Vector3f newPos = getTransform().getPos().add(dir.mul(amt));
		getTransform().getPos().set(newPos);
		//System.out.println(newPos.sub(new Vector3f(-255000000, 0, 0)));
	}
}
