package com.base.engine.rendering.meshLoading;

public class OBJIndex {
	public int vertexIndex;
	public int texCoordIndex;
	public int normalIndex;
	public int materialIndex;
	
	@Override
	public boolean equals(Object obj) {
		OBJIndex index = (OBJIndex)obj;
		return vertexIndex == index.vertexIndex && texCoordIndex == index.texCoordIndex && normalIndex == index.normalIndex && materialIndex == index.materialIndex;
	}
	
	@Override
	public int hashCode() {
		final int BASE = 11;
		final int MULTIPLIER = 51;
		int result = BASE;
		result = MULTIPLIER * result + vertexIndex;
		result = MULTIPLIER * result + texCoordIndex;
		result = MULTIPLIER * result + normalIndex;
		result = MULTIPLIER * result + materialIndex;
		return result;
	}
}
