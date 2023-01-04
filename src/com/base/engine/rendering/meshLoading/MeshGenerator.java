package com.base.engine.rendering.meshLoading;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Face;
import com.base.engine.rendering.Mesh;

public class MeshGenerator {
	public static Mesh getPlane(String name, int width, int depth) {
		return MeshGenerator.getPlane(name, width,depth,1,1);
	}
	
	public static Mesh getPlane(String name, int width, int depth, float tileX, float tileZ) {
		ArrayList<Vector3f> vertexData = new ArrayList<Vector3f>();
		ArrayList<Face> faceData = new ArrayList<Face>();
		
		float maxWD = Math.max(width, depth);
		
		vertexData.add(new Vector3f(-width, 0.0f, -depth));
		vertexData.add(new Vector3f(-width, 0.0f, depth));
		vertexData.add(new Vector3f(width, 0.0f, -depth));
		vertexData.add(new Vector3f(width, 0.0f, depth));
		
		Vector2f[] texCoords = new Vector2f[] {	new Vector2f(0.0f, 0.0f),
												new Vector2f(0.0f, tileZ * depth/(maxWD)),
												new Vector2f(tileX * width/(maxWD), 0.0f),
												new Vector2f(tileX * width/(maxWD), tileZ * depth/(maxWD))};
		
		Vector3f[] normals = new Vector3f[] {	new Vector3f( 0, 1, 0)};
		
		//v/t/n, v/t/n, v/t/n
		faceData.add(new Face("1/1/0","2/2/0","0/0/0"));
		faceData.add(new Face("3/3/0","2/2/0","1/1/0"));
		
		Vector3f[] vertices = new Vector3f[vertexData.size()];
		for(int i = 0; i < vertexData.size(); i++) {
			vertices[i] = vertexData.get(i);
		}
		
		Face[] faces = new Face[faceData.size()];
		for(int i = 0; i < faceData.size(); i++) {
			faces[i] = faceData.get(i);
		}

		return save(name, vertices, texCoords, normals, faces);
	}
	
	public static Mesh getCube(String name, float size) {
		return getBox(name, new Vector3f(size, size, size));
	}
	
	public static Mesh getBox(String name, Vector3f dimensions) {		
		float w = dimensions.getX();
		float h = dimensions.getY();
		float d = dimensions.getZ();
		
		Vector3f[] vertices = new Vector3f[] {	new Vector3f(-w, -h,-d),
												new Vector3f(-w, -h, d),
												new Vector3f( w, -h,-d),
												new Vector3f( w, -h, d),
												new Vector3f(-w, h,-d),
												new Vector3f(-w, h, d),
												new Vector3f( w, h,-d),
												new Vector3f( w, h, d)};
		
		Vector2f[] texCoords = new Vector2f[] { new Vector2f(0, 0).div(dimensions.getXY().max()),
												new Vector2f(0, h).div(dimensions.getXY().max()),
												new Vector2f(w, 0).div(dimensions.getXY().max()),
												new Vector2f(w, h).div(dimensions.getXY().max()),
												
												new Vector2f(0, 0).div(dimensions.getZY().max()),
												new Vector2f(0, h).div(dimensions.getZY().max()),
												new Vector2f(d, 0).div(dimensions.getZY().max()),
												new Vector2f(d, h).div(dimensions.getZY().max()),
												
												new Vector2f(0, 0).div(dimensions.getXZ().max()),
												new Vector2f(0, d).div(dimensions.getXZ().max()),
												new Vector2f(w, 0).div(dimensions.getXZ().max()),
												new Vector2f(w, d).div(dimensions.getXZ().max())};
		
		Vector3f[] normals = new Vector3f[] {	new Vector3f( 0, 0,-1),
												new Vector3f(-1, 0, 0),
												new Vector3f( 1, 0, 0),
												new Vector3f( 0, 0, 1),
												new Vector3f( 0, 1, 0),
												new Vector3f( 0,-1, 0)};
		
		Face[] faces = new Face[] {	new Face("0/11/5", "2/9/5", "1/10/5"),
									new Face("1/10/5", "2/9/5", "3/8/5"),
									new Face("3/0/3", "7/1/3", "1/2/3"),
									new Face("1/2/3", "7/1/3", "5/3/3"),
									new Face("5/5/1", "4/7/1", "1/4/1"),
									new Face("1/4/1", "4/7/1", "0/6/1"),
									new Face("0/0/0", "6/3/0", "2/2/0"),
									new Face("2/4/2", "6/5/2", "3/6/2"),
									new Face("3/6/2", "6/5/2", "7/7/2"),
									new Face("7/11/4", "6/10/4", "5/9/4"),
									new Face("5/9/4", "6/10/4", "4/8/4"),
									new Face("4/1/0", "6/3/0", "0/0/0")};
		
		
		return save(name, vertices, texCoords, normals, faces);
	}
	
	public static Mesh getSkydome(String name, int slices, int verticesPerSlice) {
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		ArrayList<Face> faces = new ArrayList<Face>();
		
		vertices.add(new Vector3f(0,slices,0));
		texCoords.add(new Vector2f(0,1));
		
		for(int i = 1; i < slices; i++) {
			for(int j = 0; j <= verticesPerSlice; j++) {				
				float r = (float)Math.sqrt(i * ((slices*2) - i));
				float angle = (float)j * (2*(float)Math.PI/(float)verticesPerSlice);
				
				vertices.add(new Vector3f(r*(float)Math.cos(angle), (float)(slices-i), r*(float)Math.sin(angle)));
				texCoords.add(new Vector2f((float)j/(float)verticesPerSlice,(float)(slices-i)/(float)slices));
				if(j > 0) {
					if(i == 1) {
						faces.add(new Face("0/0/0", (vertices.size()-2) + "/" + (vertices.size()-2) + "/" + (vertices.size()-2), (vertices.size()-1) + "/" + (vertices.size()-1) + "/" + (vertices.size()-1)));
					}else {
						faces.add(new Face(	(verticesPerSlice*(i-2) + j + 1) + 	"/" + (verticesPerSlice*(i-2) + j + 1) + 	"/" + (verticesPerSlice*(i-2) + j + 1), 
											(verticesPerSlice*(i-2) + j) + 		"/" + (verticesPerSlice*(i-2) + j) + 		"/" + (verticesPerSlice*(i-2) + j), 
											(verticesPerSlice*(i-1) + j + 1) + 	"/" + (verticesPerSlice*(i-1) + j + 1) + 	"/" + (verticesPerSlice*(i-1) + j + 1)));
				
						faces.add(new Face(	(verticesPerSlice*(i-1) + j + 1) + 	"/" + (verticesPerSlice*(i-1) + j + 1) + 	"/" + (verticesPerSlice*(i-1) + j + 1),
											(verticesPerSlice*(i-2) + j) + 		"/" + (verticesPerSlice*(i-2) + j) + 		"/" + (verticesPerSlice*(i-2) + j), 
											(verticesPerSlice*(i-1) + j) + 		"/" + (verticesPerSlice*(i-1) + j) + 		"/" + (verticesPerSlice*(i-1) + j)));
					}
				}else if(j==0){
					
				}
			}
		}
		
		for(int i = 0; i < vertices.size(); i++)
			normals.add(vertices.get(i).normalized());
		
		Vector3f[] vertexArr = new Vector3f[vertices.size()];
		vertices.toArray(vertexArr);
		
		Vector2f[] texCoordArr = new Vector2f[texCoords.size()];
		texCoords.toArray(texCoordArr);
		
		Vector3f[] normalArr = new Vector3f[normals.size()];
		normals.toArray(normalArr);
		
		Face[] faceArr = new Face[faces.size()];
		faces.toArray(faceArr);
		
		return save(name, vertexArr, texCoordArr, normalArr, faceArr);
	}
	
	public static Mesh getTopEighth(String name, float radius) {
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		ArrayList<Face> faces = new ArrayList<Face>();
		
		//Pole
		vertices.add(new Vector3f(0, radius, 0));
		texCoords.add(new Vector2f(0, 1));
		
		for(int i = 1; i < 90; i++) {
			for(int j = 0; j < 90; j++) {
				float x = radius*(float)Math.cos(Math.toRadians(j))*(float)Math.sin(Math.toRadians(i));
				float y = radius*(float)Math.cos(Math.toRadians(i));
				float z = radius*(float)Math.sin(Math.toRadians(j))*(float)Math.sin(Math.toRadians(i));
				vertices.add(new Vector3f(x, y,z));
				texCoords.add(new Vector2f(j/90,(90-i)/90));
				
				if(j > 0) {
					if(i == 1) {
						faces.add(new Face("0/0/0", (vertices.size()-1) + "/" + (vertices.size()-1) + "/" + (vertices.size()-1), (vertices.size()-2) + "/" + (vertices.size()-2) + "/" + (vertices.size()-2)));
					}else {
						faces.add(new Face(	(90*(i-2) + j) + 		"/" + (90*(i-2) + j) + 		"/" + (90*(i-2) + j),
											(90*(i-2) + j + 1) + 	"/" + (90*(i-2) + j + 1) + 	"/" + (90*(i-2) + j + 1), 
											(90*(i-1) + j + 1) + 	"/" + (90*(i-1) + j + 1) + 	"/" + (90*(i-1) + j + 1)));
	
						faces.add(new Face(	(90*(i-1) + j + 1) + 	"/" + (90*(i-1) + j + 1) + 	"/" + (90*(i-1) + j + 1),
											(90*(i-1) + j) + 		"/" + (90*(i-1) + j) + 		"/" + (90*(i-1) + j),
											(90*(i-2) + j) + 		"/" + (90*(i-2) + j) + 		"/" + (90*(i-2) + j)));
					}
				}
			}
		}
		
		for(int i = 0; i < vertices.size(); i++)
			normals.add(vertices.get(i).normalized());
		
		Vector3f[] vertexArr = new Vector3f[vertices.size()];
		vertices.toArray(vertexArr);
		
		Vector2f[] texCoordArr = new Vector2f[texCoords.size()];
		texCoords.toArray(texCoordArr);
		
		Vector3f[] normalArr = new Vector3f[normals.size()];
		normals.toArray(normalArr);
		
		Face[] faceArr = new Face[faces.size()];
		faces.toArray(faceArr);
		
		return save(name, vertexArr, texCoordArr, normalArr, faceArr);
	}
	
	private static Mesh save(String fileName, Vector3f[] vertices, Vector2f[] texCoords, Vector3f[] normals, Face[] faces) {
		try {
			File file = new File("./res/models/temp/" + fileName + ".obj");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("# 3D model created by Pretzel's Mesh Generator");
			writer.write("\n" + "# " + vertices.length + " vertex positions\n");
			for(Vector3f vertex:vertices) {
				writer.write("v  " + vertex.getX() + " " + vertex.getY() + " " + vertex.getZ() + "\n");
			}
			writer.write("\n" + "# " + texCoords.length + " UV coordinates\n");
			for(Vector2f texCoord:texCoords) {
				writer.write("vt " + texCoord.getX() + " " + texCoord.getY() + " 0\n");
			}
			writer.write("\n" + "# " + normals.length + " vertex normals\n");
			for(Vector3f normal:normals) {
				writer.write("vn " + normal.getX() + " " + normal.getY() + " " + normal.getZ()+ "\n");
			}
			writer.write("\n" + "# " + faces.length + " triangles" + "\n");
			for(int i = 0; i < faces.length; i++) {
				writer.write("f  ");
				for(int j = 0; j < 3; j++) {
					writer.write((faces[i].getVertexIndex(j)+1) + "/" + (faces[i].getTexCoordIndex(j)+1) + "/" + (faces[i].getNormalIndex(j)+1));
					
					if(j < 2) writer.write(" ");
					else writer.write("\n");
				}		
			}
			if(writer != null)
				writer.close();
			return new Mesh("/temp/" + fileName + ".obj");
		}catch(IOException e) {
			
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
