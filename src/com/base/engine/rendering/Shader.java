package com.base.engine.rendering;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import com.base.engine.components.BaseLight;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.resourceManagement.Resource;

public class Shader {
	private static HashMap<String, Resource> resources = new HashMap<String, Resource>();
	private Resource resource;
	private String fileName;

	public Shader(String fileName) {
		resources = new HashMap<String, Resource>();
		resource = new Resource();
		this.fileName = fileName;
		
		Resource oldResource = resources.get(fileName);
		if(oldResource != null) {
			resource = oldResource;
			resource.addReference();
		}else {
			resource = new Resource();
			resource.addStringArray("uniformNames");
			resource.addStringArray("uniformTypes");
			resource.addInt("program", glCreateProgram());
			if(resource.getInt("program") == 0) { 
				System.err.println("Shader creation failed on construction"); 
				System.exit(1); 
			}
			
			String vertexShaderText = loadShader(fileName + ".vs");
			String fragmentShaderText = loadShader(fileName + ".fs");
			
			addVertexShader(vertexShaderText);
			addFragmentShader(fragmentShaderText);
			compileShader();
			
			findUniformTypes(vertexShaderText);
			findUniformTypes(fragmentShaderText);
			
			resources.put(fileName, resource);
		}
	}
	
	@Override
	protected void finalize() {
		if(resource.removeReference() && !fileName.isEmpty())
			resources.remove(fileName);
	}
	
	private void addAllUniforms(String shaderText) {		
		for(int i = 0; i < glGetProgrami(resource.getInt("program"), GL_ACTIVE_UNIFORMS); i++) {
			String uniformName = glGetActiveUniform(resource.getInt("program"), i, 128);
			resource.addInt(uniformName, glGetUniformLocation(resource.getInt("program"), uniformName));
		}
	}
	
	public void findUniformTypes(String shaderText) {
		final String KEYWORD = "uniform";
		String[] lines = shaderText.split("\n");
		
		for(String line:lines) {
			if(line.startsWith(KEYWORD)) {
				String[] values = line.split(" ");
				resource.getStringArray("uniformNames").add(values[2].replace(';',' ').trim());
				resource.getStringArray("uniformTypes").add(values[1]);
			}
		}
		
		addAllUniforms(shaderText);
	}

	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		Matrix4f world = transform.getTransformation();
		Matrix4f MVP = renderingEngine.getMainCamera().getViewProjection().mul(world);
		
		for(int i = 0; i < resource.getStringArray("uniformNames").size(); i++) {
			String uniformName = resource.getStringArray("uniformNames").get(i);
			String uniformType = resource.getStringArray("uniformTypes").get(i);
			
			if(uniformType.equals("sampler2D")) {
				int sampler = renderingEngine.getSamplerSlot(uniformName);
				material.getTexture(uniformName).bind(sampler);
				setUniformI(uniformName, sampler);
			} else if(uniformName.startsWith("T_")) {
				if(uniformName.equals("T_MVP"))
					setUniformM(uniformName, MVP);
				else if(uniformName.equals("T_model"))
					setUniformM(uniformName, world);
				else throw new IllegalArgumentException(uniformName + " is not a valid component of Transform");
			} else if(uniformName.startsWith("R_")) {
				if(uniformType.equals("vec3"))
					setUniformV(uniformName, renderingEngine.getVector3f(uniformName.substring(2)));
				else if(uniformType.equals("float"))
					setUniformF(uniformName, renderingEngine.getFloat(uniformName.substring(2)));
				else if(uniformType.equals("DirectionalLight"))
					setUniformDirectionalLight(uniformName, (DirectionalLight)renderingEngine.getActiveLight());
				else if(uniformType.equals("PointLight"))
					setUniformPointLight(uniformName, (PointLight)renderingEngine.getActiveLight());
				else if(uniformType.equals("SpotLight"))
					setUniformSpotLight(uniformName, (SpotLight)renderingEngine.getActiveLight());
				//else renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);
			} else if(uniformName.startsWith("C_")){
				if(uniformName.equals("C_eyePos")) 
					setUniformV(uniformName, renderingEngine.getMainCamera().getTransform().getTransformedPos());
				else throw new IllegalArgumentException(uniformName + " is not a valid component of Camera");
			} else {
				if(uniformType.equals("vec3"))
					setUniformV(uniformName, material.getVector3f(uniformName));
				else if(uniformType.equals("float"))
					setUniformF(uniformName, material.getFloat(uniformName));
				else throw new IllegalArgumentException(uniformType + " is not a supported type in Material");
			}
		}
	}

	public void bind() { glUseProgram(resource.getInt("program")); }

	private void addVertexShader(String text) { addProgram(text, GL_VERTEX_SHADER); }
	private void addGeometryShader(String text) { addProgram(text, GL_GEOMETRY_SHADER); }
	private void addFragmentShader(String text) { addProgram(text, GL_FRAGMENT_SHADER); }

	private void compileShader() {
		glLinkProgram(resource.getInt("program"));
		if (glGetProgrami(resource.getInt("program"), GL_LINK_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(resource.getInt("program"), 1024)); System.exit(1);
		}
		
		glValidateProgram(resource.getInt("program"));
		if (glGetProgrami(resource.getInt("program"), GL_VALIDATE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(resource.getInt("program"), 1024)); System.exit(1);
		}
	}

	private void addProgram(String text, int type) {
		int shader = glCreateShader(type);
		if (shader == 0) {
			System.out.println("Shader creation failed on add"); System.exit(1);
		}

		glShaderSource(shader, text);
		glCompileShader(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024)); System.exit(1);
		}
		glAttachShader(resource.getInt("program"), shader);
	}

	public void setUniformI(String uniformName, int value) {
		glUniform1i(resource.getInt(uniformName), value);
	}

	public void setUniformF(String uniformName, float value) {
		glUniform1f(resource.getInt(uniformName), value);
	}

	public void setUniformV(String uniformName, Vector3f value) {
		glUniform3f(resource.getInt(uniformName), value.getX(), value.getY(), value.getZ());
	}

	public void setUniformM(String uniformName, Matrix4f value) {
		glUniformMatrix4(resource.getInt(uniformName), true, Util.createFlippedBuffer(value));
	}
	
	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
		setUniformV(uniformName + ".color", baseLight.getColor());
		setUniformF(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight) {
		setUniformBaseLight(uniformName + ".base", (BaseLight)directionalLight);
		setUniformV(uniformName + ".direction", directionalLight.getDirection());
	}
	
	public void setUniformPointLight(String uniformName, PointLight pointLight) {
		setUniformBaseLight(uniformName + ".base", (BaseLight)pointLight);
		setUniformF(uniformName + ".atten.constant", pointLight.getAttenuation().getConstant());
		setUniformF(uniformName + ".atten.linear", pointLight.getAttenuation().getLinear());
		setUniformF(uniformName + ".atten.exponent", pointLight.getAttenuation().getExponent());
		setUniformV(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		setUniformF(uniformName + ".range", pointLight.getRange());
	}
	
	public void setUniformSpotLight(String uniformName, SpotLight spotLight) {
		setUniformPointLight(uniformName + ".pointLight", (PointLight)spotLight);
		setUniformV(uniformName + ".direction", spotLight.getDirection());
		setUniformF(uniformName + ".cutoff", spotLight.getCutoff());
	}

	private static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		final String INCLUDE_DIRECTIVE = "#include";
		try {
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
			String line;
			while ((line = shaderReader.readLine()) != null) {
				if(line.startsWith(INCLUDE_DIRECTIVE)) 
					shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1))); 
				else 
					shaderSource.append(line).append("\n");
			}
			shaderReader.close();
		} catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
		return shaderSource.toString();
	}
}
