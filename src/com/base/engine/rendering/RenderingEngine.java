package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.components.FreeLook;
import com.base.engine.components.FreeMove;
import com.base.engine.core.GameObject;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.core.Util;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.resourceManagement.MappedValues;

public class RenderingEngine extends MappedValues {
	private HashMap<String, Integer> samplers;
	private ArrayList<BaseLight> lights;
	private BaseLight activeLight;
	private Shader defaultShader;
	private Camera mainCamera;
	private Texture tempTarget;
	private Mesh mesh;
	private Material material;
	private Transform t;
	private Camera camera;
	private GameObject cameraObject;

	public RenderingEngine() {
		super();
		lights = new ArrayList<BaseLight>();
		samplers = new HashMap<String, Integer>();
		samplers.put("diffuse", 0);
		samplers.put("normalMap", 1);
		samplers.put("dispMap", 2);
		
		addVector3f("ambient", new Vector3f(0.2f,0.2f,0.2f));
		defaultShader = new Shader("forward-ambient");
		
		glClearColor(0.0f,0.0f,0.0f,0.0f);
		
		System.out.println(getOpenGLVersion());
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_DEPTH_CLAMP);
		glEnable(GL_TEXTURE_2D);
		
//		int width = Window.getWidth() / 3;
//		int height = Window.getHeight() / 3;
//		int dataSize = width * height * 4;
//		
//		ByteBuffer data = Util.createByteBuffer(dataSize);
//		tempTarget = new Texture(width, height, data, GL_TEXTURE_2D, GL_NEAREST, GL_REPEAT, GL_COLOR_ATTACHMENT0);
//		
//		Vertex vertices[] = { 	new Vertex(new Vector3f(-1,-1,0), new Vector2f(0,0)),
//								new Vertex(new Vector3f(-1,1,0), new Vector2f(1,1)),
//								new Vertex(new Vector3f(1,1,0), new Vector2f(0,1)),
//								new Vertex(new Vector3f(1,-1,0), new Vector2f(0,0)) };
//		int[] indices = { 	2,1,0,
//							3,2,0 };
//		
//		material = new Material(tempTarget);
//		t = new Transform();
//		t.setScl(0.9f, 0.9f, 0.9f);
//		mesh = new Mesh(vertices, indices, false);
//		
//		camera = new Camera(new Matrix4f().initIdentity());
//		cameraObject = new GameObject().addComponent(camera);
//		cameraObject.getTransform().rotate(new Vector3f(0,1,0), (float)Math.toRadians(180));
	}
	
	public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType) {
		throw new IllegalArgumentException(uniformType + " is not a supported type in RenderingEngine");
	}

	public void render(GameObject object) {
		//tempTarget.bindAsRenderTarget();
		Window.bindAsRenderTarget();
		
		if(getMainCamera() == null) System.err.println("Error! Main camera not found.");
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		object.renderAll(defaultShader, this);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		//Begin light blending
		for(BaseLight light : lights) {
			activeLight = light;
			object.renderAll(light.getShader(), this);
		}
		//End light blending
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	
//		Window.bindAsRenderTarget();
//		
//		Camera temp = mainCamera;
//		mainCamera = camera;
//		
//		glClearColor(0.0f,0.0f,0.5f,1.0f);
//		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//		defaultShader.bind();
//		defaultShader.updateUniforms(t, material, this);
//		mesh.draw();
//		
//		mainCamera = temp;
	}
	
	public void addLight(BaseLight light) {	lights.add(light); }
	public void addCamera(Camera camera) { mainCamera = camera;	}
	
	public Camera getMainCamera() { return mainCamera; }
	public BaseLight getActiveLight() { return activeLight; }
	public int getSamplerSlot(String samplerName) { return samplers.get(samplerName); }
	public static String getOpenGLVersion() { return glGetString(GL_VERSION); }
	
	public void setMainCamera(Camera mainCamera) { this.mainCamera = mainCamera; }
}
