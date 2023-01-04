package com.base.engine.rendering.meshLoading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.components.GameComponent;
import com.base.engine.core.Util;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

public class OBJModel extends GameComponent{
	private ArrayList<Vector3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<OBJIndex> indices;
	private boolean hasTexCoords, hasNormals;
	private Vector3f offset;
	
	public OBJModel(String fileName, Vector3f origin) {
		positions = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indices = new ArrayList<OBJIndex>();
		hasTexCoords = false;
		hasNormals = false;
		Vector3f minPos = new Vector3f(1000,1000,1000);
		Vector3f maxPos = new Vector3f(-1000,-1000,-1000);
		
		BufferedReader meshReader = null;
		try {
			meshReader = new BufferedReader(new FileReader(fileName));
			String line;

			while ((line = meshReader.readLine()) != null) {
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);

				if (tokens.length == 0 || tokens[0].equals("#")) {
					continue;
				} else if (tokens[0].equals("v")) {
					Float x = Float.valueOf(tokens[1]);
					Float y = Float.valueOf(tokens[2]);
					Float z = -Float.valueOf(tokens[3]);
					minPos = new Vector3f(Math.min(x, minPos.getX()), Math.min(y, minPos.getY()), Math.min(z, minPos.getZ()));
					maxPos = new Vector3f(Math.max(x, maxPos.getX()), Math.max(y, maxPos.getY()), Math.max(z, maxPos.getZ()));
					positions.add(new Vector3f(x, y, z));
				} else if (tokens[0].equals("vt")) {
					texCoords.add(new Vector2f(	Float.valueOf(tokens[1]), 
												Float.valueOf(tokens[2])));
				} else if (tokens[0].equals("vn")) {
					normals.add(new Vector3f(	Float.valueOf(tokens[1]),
												Float.valueOf(tokens[2]), 
												Float.valueOf(tokens[3])));
				} else if (tokens[0].equals("f")) {
					for(int i = 0; i < tokens.length - 3; i++) {
						indices.add(parseOBJIndex(tokens[3 + i]));
						indices.add(parseOBJIndex(tokens[2 + i]));
						indices.add(parseOBJIndex(tokens[1]));
					}
				}
			}
			meshReader.close();
			offset = new Vector3f(	-(maxPos.getX() + minPos.getX())/2,	//center x position
									-(maxPos.getY() + minPos.getY())/2,	//center y position
									-(maxPos.getZ() + minPos.getZ())/2);	//center z position
			offset = offset.add(origin.mul(maxPos.add(offset)).mul(-1));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public IndexedModel toIndexedModel() {
		IndexedModel resultModel = new IndexedModel();
		IndexedModel normalModel = new IndexedModel();
		HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<OBJIndex, Integer>();		
		HashMap<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < indices.size(); i++) {
			OBJIndex currentIndex = indices.get(i);
			
			Vector3f currentPosition = (positions.get(currentIndex.vertexIndex).add(offset));
			Vector2f currentTexCoord;
			Vector3f currentNormal;
			
			if(hasTexCoords)
				currentTexCoord = texCoords.get(currentIndex.texCoordIndex);
			else
				currentTexCoord = new Vector2f(0,0);
			
			if(hasNormals)
				currentNormal = normals.get(currentIndex.normalIndex);
			else
				currentNormal = new Vector3f(0,0,0);
			
			Integer modelVertexIndex = resultIndexMap.get(currentIndex);
			if(modelVertexIndex == null) {
				modelVertexIndex = resultModel.getPositions().size();
				resultIndexMap.put(currentIndex, modelVertexIndex);
				
				resultModel.getPositions().add(currentPosition);
				resultModel.getTexCoords().add(currentTexCoord);
				if(hasNormals)
					resultModel.getNormals().add(currentNormal);
				resultModel.getTangents().add(new Vector3f(0,0,0));
			}
			
			Integer modelNormalIndex = normalIndexMap.get(currentIndex.vertexIndex);
			if(modelNormalIndex == null)
			{
				modelNormalIndex = normalModel.getPositions().size();
				normalIndexMap.put(currentIndex.vertexIndex, modelNormalIndex);
				
				normalModel.getPositions().add(currentPosition);
				normalModel.getTexCoords().add(currentTexCoord);
				normalModel.getNormals().add(currentNormal);
				normalModel.getTangents().add(new Vector3f(0,0,0));
			}
			resultModel.getIndices().add(modelVertexIndex);
			normalModel.getIndices().add(modelNormalIndex);
			indexMap.put(modelVertexIndex, modelNormalIndex);
		}
		
		if(!hasNormals) {
			normalModel.calcNormals();
			for(int i = 0; i < resultModel.getPositions().size(); i++)
				resultModel.getNormals().add(normalModel.getNormals().get(indexMap.get(i)));
		}
		
		normalModel.calcTangents();
		for(int i = 0; i < resultModel.getPositions().size(); i++)
			resultModel.getTangents().add(normalModel.getTangents().get(indexMap.get(i)));
		
		for(int i = 0; i < resultModel.getTexCoords().size(); i++)
			resultModel.getTexCoords().get(i).setY(1.0f - resultModel.getTexCoords().get(i).getY());
		
		return resultModel;
	}
	
	private OBJIndex parseOBJIndex(String token) {
		String[] values = token.split("/");
		
		OBJIndex result = new OBJIndex();
		result.vertexIndex = Integer.parseInt(values[0]) - 1;
		if(values.length > 1) {
			if(!values[1].isEmpty()) { hasTexCoords = true; result.texCoordIndex = Integer.parseInt(values[1]) - 1; }
			if(values.length > 2)
				if(!values[2].equals("")) { hasNormals = true; result.normalIndex = Integer.parseInt(values[2]) - 1; }
		}
		return result;
	}
}
