package com.base.engine.rendering;

public class Face {
	private int[] vIndices, tIndices, nIndices;
	
	public Face(String i0, String i1, String i2) {
		vIndices = new int[3];
		tIndices = new int[3];
		nIndices = new int[3];
		
		String[] i0s = i0.split("/");
		String[] i1s = i1.split("/");
		String[] i2s = i2.split("/");
		
		vIndices = new int[] {Integer.parseInt(i0s[0]), Integer.parseInt(i1s[0]), Integer.parseInt(i2s[0])};
		if(i0s.length > 1) {
			tIndices = new int[] {Integer.parseInt(i0s[1]), Integer.parseInt(i1s[1]), Integer.parseInt(i2s[1])};
			if(i0s.length > 2)
				nIndices = new int[] {Integer.parseInt(i0s[2]), Integer.parseInt(i1s[2]), Integer.parseInt(i2s[2])};
		}
		
	}
	
	public int getVertexIndex(int index) {
		if(index < vIndices.length)
			return vIndices[index];
		else return 0;
	}
	
	public int getTexCoordIndex(int index) {
		if(index < tIndices.length)
			return tIndices[index];
		else return 0;
	}
	
	public int getNormalIndex(int index) {
		if(index < nIndices.length)
			return nIndices[index];
		else return 0;
	}
}
