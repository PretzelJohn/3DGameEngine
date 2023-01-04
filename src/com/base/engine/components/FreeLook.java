package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Window;

public class FreeLook extends GameComponent {
	private float sensitivity;
	private int mouseLockButton;
	private boolean mouseLockState;

	public FreeLook() {
		this(0.5f, 1);
	}
	
	public FreeLook(float sensitivity) {
		this(sensitivity, 1);
	}
	
	public FreeLook(float sensitivity, int mouseLockButton) {
		this.sensitivity = sensitivity;
		this.mouseLockButton = mouseLockButton;
	}
	
	@Override
	public void input(float delta) {
		Vector2f centerPos = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
		
		if (Input.getMouseDown(mouseLockButton)) {
			Input.setMousePos(centerPos);
			Input.setCursor(false);
			mouseLockState = true;
		}else if(Input.getMouseUp(mouseLockButton)){
			Input.setCursor(true);
			mouseLockState = false;
		}
		
		if (mouseLockState) {
			Vector2f deltaPos = Input.getMousePos().sub(centerPos);
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

			if (rotY)
				getTransform().rotate(new Vector3f(0,1,0), (float)Math.toRadians(deltaPos.getX() * sensitivity));
			if (rotX)
				getTransform().rotate(getTransform().getRot().getRight(), (float)Math.toRadians(-deltaPos.getY() * sensitivity));
			if (rotY || rotX)
				Input.setMousePos(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
		}
	}

	
}
