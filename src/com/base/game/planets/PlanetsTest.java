package com.base.game.planets;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.base.engine.components.Animation;
import com.base.engine.components.Camera;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.FreeLook;
import com.base.engine.components.FreeMove;
import com.base.engine.components.Gravity;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.PhysicsEngineComponent;
import com.base.engine.components.PhysicsObjectComponent;
import com.base.engine.components.PointLight;
import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.Input;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector3f;
import com.base.engine.physics.PhysicsEngine;
import com.base.engine.physics.PhysicsObject;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.Window;
import com.base.engine.rendering.meshLoading.MeshGenerator;

public class PlanetsTest extends Game {
	private Camera main = new Camera(70f, Window.getWidth()/Window.getHeight(), 0.1f, 1000000000);
	private GameObject camera = new GameObject().addComponent(new FreeLook(0.5f)).addComponent(new FreeMove(1000)).addComponent(main);
	private ArrayList<Planet> planets = new ArrayList<Planet>();
	
	public void init() {		
		//Material starsMat = new Material(	new Texture("8k_stars_milky_way.jpg", Texture.T_2D, Texture.F_NONE_M_NONE));
		
//		Material sunMat = new Material(		new Texture("8k_sun.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH));
//		
//		Material mercuryMat = new Material( new Texture("8k_mercury.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.4f, 16,
//											new Texture("default_normal.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH),
//											new Texture("mercurybump.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.006f, -0.012f);
//		
//		Material venusMat = new Material(	new Texture("8k_venus.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 1f, 8,
//											new Texture("default_normal.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH),
//											new Texture("venusbump.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.08f, -0.008f);
		
		Material earthMat = new Material(	new Texture("8081_earthmap10k.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.4f, 4,
											new Texture("8081_earthnormal8k.png", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH),
											new Texture("8081_earthbump10k.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.004f, 0);
		
		Material moonMat = new Material(	new Texture("8k_moon.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.1f, 4, 
											new Texture("default_normal.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 
											new Texture("moonbump4k.jpg",Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.01f, 0.005f);
		
		Material marsMat = new Material(	new Texture("5672_mars_12k_color.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.4f, 8, 
											new Texture("5672_mars_12k_normal.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 
											new Texture("5672_mars_12k_topo.jpg",Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.008f, 0.005f);
//		
//		Material jupiterMat = new Material(	new Texture("8k_jupiter.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.8f, 8);
//		
//		Material saturnMat = new Material(	new Texture("8k_saturn.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 1f, 8);
//		
//		Material uranusMat = new Material(	new Texture("2k_uranus.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 1f, 16);
//		
//		Material neptuneMat = new Material(	new Texture("2k_neptune.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.8f, 8);
//		
//		Material plutoMat = new Material(	new Texture("plutomap2k.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.4f, 4,
//											new Texture("default_normal.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH),
//											new Texture("plutobump2k.jpg", Texture.T_2D, Texture.F_TRILINEAR_M_SMOOTH), 0.05f, 0.1f);
		
		//GameObject stars = new GameObject().addComponent(new MeshRenderer(new Mesh("stars1.obj"), starsMat));
		//addObject(stars);
		
//		GameObject sun = new GameObject().addComponent(new MeshRenderer(new Mesh("sun1.obj"), sunMat));
//		addObject(sun);
//		
//		planets.add(new Planet(new MeshRenderer(new Mesh("mercury" + "1.obj"), mercuryMat), 2.44f, 57900, Quaternion.Y_AXIS.rotate(Quaternion.X_AXIS, (float)Math.toRadians(0.034)), 0.259965f, 0.170532f));
//		planets.add(new Planet(new MeshRenderer(new Mesh("venus" + "1.obj"), venusMat), 6.052f, 108200, Quaternion.Y_AXIS.rotate(Quaternion.X_AXIS, (float)Math.toRadians(177.36)), 0.061728f, 0.066762f));
		planets.add(new Planet(new MeshRenderer(new Mesh("earth" + "1.obj"), earthMat), 6.371f, 0, Quaternion.Y_AXIS.rotate(Quaternion.X_AXIS, (float)Math.toRadians(23.4)), 15f, 0.041067f));
		planets.add(new Planet(new MeshRenderer(new Mesh("moon" + "1.obj"), moonMat), 1.737f, 149600, Quaternion.Y_AXIS.rotate(Quaternion.X_AXIS, (float)Math.toRadians(1.5)), 0.549008f, 0.555556f));
		planets.add(new Planet(new MeshRenderer(new Mesh("mars" + "1.obj"), marsMat), 3.389f, 227900, Quaternion.Y_AXIS.rotate(Quaternion.X_AXIS, (float)Math.toRadians(25)), 14.619883f, 0.021835f));
//		planets.add(new Planet(new MeshRenderer(new Mesh("jupiter" + "1.obj"), jupiterMat), 69.911f, 778300, Quaternion.Y_AXIS.rotate(Quaternion.X_AXIS, (float)Math.toRadians(3.13)), 36.585366f, 0.003462f));
//		planets.add(new Planet(new MeshRenderer(new Mesh("saturn" + "1.obj"), saturnMat), 58.232f, 1427000, Quaternion.Y_AXIS.rotate(Quaternion.X_AXIS, (float)Math.toRadians(26.7)), 35.294118f, 0.001394f));
//		planets.add(new Planet(new MeshRenderer(new Mesh("uranus" + "1.obj"), uranusMat), 25.362f, 2871000, Quaternion.Y_AXIS.rotate(Quaternion.X_AXIS, (float)Math.toRadians(98)), 20.111732f, 0.000488f));
//		planets.add(new Planet(new MeshRenderer(new Mesh("neptune" + "1.obj"), neptuneMat), 24.622f, 4497100, Quaternion.Y_AXIS.rotate(Quaternion.X_AXIS, (float)Math.toRadians(28.32)), 18.848168f, 0.000249f));
//		planets.add(new Planet(new MeshRenderer(new Mesh("pluto" + "1.obj"), plutoMat), 1.188f, 5913000, Quaternion.Y_AXIS.rotate(Quaternion.X_AXIS, (float)Math.toRadians(119.61)), 2.347418f, 0.000166f));
	
		for(int i = 0; i < planets.size(); i++) {
			Planet planet = planets.get(i);
			
			Animation anim = new Animation(true);
			for(int j = 0; j < (float)Math.ceil(360.0f/planet.getRotSpeed()); j++) {
				Transform t = new Transform();
				t.setPos(planet.getPos());
				t.setRot(new Quaternion(planet.getRotAxis(), -(float)Math.toRadians(j*planet.getRotSpeed())));
				anim.addKeyFrame(j*1000, t);
			}
			
			GameObject planetObject = new GameObject().addComponent(planet.getMeshRenderer()).addComponent(anim);
			addObject(planetObject);
			
			anim.start();
		}
		
		
		DirectionalLight sunLight2 = new DirectionalLight(new Vector3f(0.988f,0.832f,0.254f), 1.5f);
		GameObject sun1 = new GameObject().addComponent(sunLight2);
		sun1.getTransform().setRot(new Quaternion(Quaternion.Y_AXIS, (float)Math.toRadians(180)));
		addObject(sun1);
		
//		PointLight sunLight = new PointLight(new Vector3f(0.988f,0.832f,0.254f), 1000000000, new Attenuation(0f, 0f, 0.8f));
//		GameObject sun0 = new GameObject().addComponent(sunLight);
//		addObject(sun0);
		
		camera.getTransform().setPos(planets.get(0).getPos().add(new Vector3f(0, 0, -16)));
		addObject(camera);
		
//		PhysicsEngine physicsEngine = new PhysicsEngine();
//		physicsEngine.addObject(new PhysicsObject(new Vector3f(0,3390000,0), new Vector3f(0,0,0), 801068037060.5f, 3390000, 3));
//		physicsEngine.addObject(new PhysicsObject(new Vector3f(0,0,0), new Vector3f(0,0,0), 65, 3390000, 3390000));
//		PhysicsEngineComponent pec = new PhysicsEngineComponent(physicsEngine);
//		
//		for(int i = 0; i < physicsEngine.getNumObjects(); i++) {
//			GameObject player = new GameObject().addComponent(new PhysicsObjectComponent(pec.getEngine().getObject(i)));
//			player.getTransform().setScl(pec.getEngine().getObject(i).getRadius()/3390, pec.getEngine().getObject(i).getRadius()/3390, pec.getEngine().getObject(i).getRadius()/3390);
//			addObject(player);
//			if(i == 0) player.addChild(camera);
//		}
//		
//		addObject(new GameObject().addComponent(pec));
	}
	
	@Override
	public void input(float delta) {
		super.input(delta);
		if(Input.getKey(Keyboard.KEY_ESCAPE)) {
			System.exit(0);
		}
		
		if(Input.getKey(Keyboard.KEY_0)) { camera.getTransform().setPos(planets.get(9).getPos().add(new Vector3f(0,0,-8))); }
		if(Input.getKey(Keyboard.KEY_1)) { camera.getTransform().setPos(planets.get(0).getPos().add(new Vector3f(0,0,-8))); }
		if(Input.getKey(Keyboard.KEY_2)) { camera.getTransform().setPos(planets.get(1).getPos().add(new Vector3f(0,0,-16))); }
		if(Input.getKey(Keyboard.KEY_3)) { camera.getTransform().setPos(planets.get(2).getPos().add(new Vector3f(0,0,-24))); }
		if(Input.getKey(Keyboard.KEY_4)) { camera.getTransform().setPos(planets.get(3).getPos().add(new Vector3f(0,0,-24))); }
		if(Input.getKey(Keyboard.KEY_5)) { camera.getTransform().setPos(planets.get(4).getPos().add(new Vector3f(0,0,-80))); }
		if(Input.getKey(Keyboard.KEY_6)) { camera.getTransform().setPos(planets.get(5).getPos().add(new Vector3f(0,0,-80))); }
		if(Input.getKey(Keyboard.KEY_7)) { camera.getTransform().setPos(planets.get(6).getPos().add(new Vector3f(0,0,-64))); }
		if(Input.getKey(Keyboard.KEY_8)) { camera.getTransform().setPos(planets.get(7).getPos().add(new Vector3f(0,0,-64))); }
		if(Input.getKey(Keyboard.KEY_9)) { camera.getTransform().setPos(planets.get(8).getPos().add(new Vector3f(0,0,-48))); }
	}
}
