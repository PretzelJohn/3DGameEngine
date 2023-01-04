package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.components.GameComponent;
import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.meshLoading.IndexedModel;
import com.base.engine.rendering.meshLoading.OBJModel;
import com.base.engine.rendering.resourceManagement.Resource;

public class Mesh extends GameComponent {
	private static HashMap<String, Resource> resources = new HashMap<String, Resource>();
	private Resource resource;
	private String fileName;

	public Mesh(String fileName) {
		this(fileName, new Vector3f(0,0,0));
	}
	
	public Mesh(String fileName, Vector3f origin) {
		this.fileName = fileName;
		Resource oldResource = resources.get(fileName);
		if(oldResource != null) {
			resource = oldResource;
			resource.addReference();
		}else {
			loadMesh(fileName, origin);
			resources.put(fileName, resource);
		}
	}

	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
		fileName = "";
		addVertices(vertices, indices, calcNormals);
	}
	
	@Override
	protected void finalize() {
		if(resource.removeReference() && !fileName.isEmpty()) 
			resources.remove(fileName);
	}

	private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
		if(calcNormals) calcNormals(vertices, indices);
		
		resource = new Resource();
		resource.addInt("vbo", glGenBuffers());
		resource.addInt("ibo", glGenBuffers());
		resource.addInt("size", indices.length);
		
		glBindBuffer(GL_ARRAY_BUFFER, resource.getInt("vbo"));
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getInt("ibo"));
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}
	
	public void calcNormals(Vertex[] vertices, int[] indices) {
		for(int i = 0; i < indices.length; i += 3) {
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];
			
			Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
			Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());
			Vector3f normal = v1.cross(v2).normalized();

			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
		}
		
		for(int i = 0; i < vertices.length; i++)
			vertices[i].setNormal(vertices[i].getNormal().normalized());
	}

	public void draw() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);

		glBindBuffer(GL_ARRAY_BUFFER, resource.getInt("vbo"));
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getInt("ibo"));
		glDrawElements(GL_TRIANGLES, resource.getInt("size"), GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
	}

	private void loadMesh(String fileName, Vector3f origin) {
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		if (!ext.equals("obj")) {
			System.err.println("Error: '" + ext + "' file format not supported for mesh data.");
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		IndexedModel model = new IndexedModel();
		OBJModel test = new OBJModel("./res/models/" + fileName, origin);
		model = test.toIndexedModel();
		model.calcNormals();
		model.calcTangents();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		for(int i = 0; i < model.getPositions().size(); i++)
			vertices.add(new Vertex(model.getPositions().get(i), model.getTexCoords().get(i), model.getNormals().get(i), model.getTangents().get(i)));
		
		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);
		
		Integer[] indexData = new Integer[model.getIndices().size()];
		model.getIndices().toArray(indexData);
		
		addVertices(vertexData, Util.toIntArray(indexData), false);
	}
}
