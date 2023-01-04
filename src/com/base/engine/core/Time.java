package com.base.engine.core;

public class Time {
	public static double getTime() { 
		return (double)System.nanoTime()/(double)1000000000L; 
	}
}
