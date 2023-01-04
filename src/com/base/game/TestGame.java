package com.base.game;

import org.lwjgl.input.Keyboard;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.physics.PhysicsEngine;
import com.base.engine.physics.PhysicsObject;
import com.base.engine.rendering.*;
import com.base.engine.rendering.meshLoading.MeshGenerator;

public class TestGame extends Game {
	public void init() {
		Material material = new Material(	new Texture("hardwoods.png", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.01f, 2, 
											new Texture("hardwoods_normal.png", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 
											new Texture("hardwoods_bump.png", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.001f, -0.0005f);
		
		Material material2 = new Material(	new Texture("bricks.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.2f, 4, 
											new Texture("bricks_normal.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 
											new Texture("bricks_disp.png", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.04f, -0.5f);
		
		Material material3 = new Material(	new Texture("hardwood_light.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH, Texture.W_MIRRORED_REPEAT), 0.2f, 8);
		
		Material material4 = new Material(	new Texture("concrete.png", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.4f, 16, 
											new Texture("concrete_normal.png", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 
											new Texture("concrete_bump.png", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.01f, 0.1f);
		
		Animation anim1 = new Animation(true);
		for(int i = 0; i < 360; i++) {
			Transform t = new Transform();
			t.setPos(0, 6, 0);
			t.setRot(new Quaternion(Quaternion.Y_AXIS, (float)Math.toRadians(i)));
			anim1.addKeyFrame(i*100, t);
		}
		
		Mesh plane = MeshGenerator.getPlane("ground", 37,30, 8,8);
		GameObject planeObject = new GameObject().addComponent(new MeshRenderer(plane, material3));
		
		GameObject impala = new GameObject().addComponent(new MeshRenderer(new Mesh("impala.obj", new Vector3f(0,-1,0)), material2));	
		GameObject cube = new GameObject().addComponent(new MeshRenderer(MeshGenerator.getCube("cube", 1), material4)).addComponent(anim1);//.addComponent(new LookAtComponent(testMesh1));
		
//		GameObject directionalLightObject = new GameObject().addComponent(new DirectionalLight(new Vector3f(1,0.8f,0.8f), 0.6f));
//		directionalLightObject.getTransform().setPos(new Vector3f(0,5000,0));
//		directionalLightObject.getTransform().setRot(new Quaternion(Quaternion.X_AXIS, (float)Math.toRadians(-90)));
		
		GameObject spotLight = new GameObject().addComponent(new SpotLight(new Vector3f(1,1,0.9f), 5f, new Attenuation(0,0,1), 0.01f));
		spotLight.getTransform().setPos(new Vector3f(0,10,0));
		spotLight.getTransform().setRot(new Quaternion(Quaternion.X_AXIS, (float)(Math.PI/2f)));
		
		cube.getTransform().setPos(0, 6, 0);
		
		addObject(planeObject);
		addObject(impala);
		addObject(cube);
		
		GameObject camera = new GameObject().addComponent(new FreeLook(0.5f))
				.addComponent(new FreeMove(10))
				.addComponent(new Camera((float)Window.getWidth()/(float)Window.getHeight()));
		camera.getTransform().setPos(new Vector3f(15,5,15));
		camera.getTransform().setRot(new Quaternion(Quaternion.Y_AXIS, (float)(5.0f*Math.PI/4.0f)));
		addObject(camera);
		
		//addObject(directionalLightObject);
		addObject(spotLight);
		
		anim1.start();
	}
	
	@Override
	public void input(float delta) {
		super.input(delta);
		if(Input.getKey(Keyboard.KEY_ESCAPE)) {
			System.exit(0);
		}
	}
}
