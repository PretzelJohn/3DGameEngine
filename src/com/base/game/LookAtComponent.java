package com.base.game;

import com.base.engine.components.GameComponent;
import com.base.engine.core.GameObject;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class LookAtComponent extends GameComponent {
	private RenderingEngine renderingEngine;
	private GameObject lookAtObj;
	
	public LookAtComponent() {
		
	}
	
	public LookAtComponent(GameObject lookAtObj) { 
		this.lookAtObj = lookAtObj;
	}
	
	@Override
	public void update(float delta) {
		if(renderingEngine != null) {
			if(lookAtObj == null) {
				lookAtObj = renderingEngine.getMainCamera().getParent();
			}else {
				Quaternion newRot = getTransform().getLookAtRotation(lookAtObj.getTransform().getTransformedPos(), new Vector3f(0,1,0));
				//getTransform().setRot(getTransform().getRot().nlerp(newRot, delta * 5.0f, true));
				getTransform().setRot(getTransform().getRot().slerp(newRot, delta * 5.0f, true));
			}
		}
	}
	
	@Override 
	public void render(Shader shader, RenderingEngine renderingEngine) {
		this.renderingEngine = renderingEngine;
	}
}
