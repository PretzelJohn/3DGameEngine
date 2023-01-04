package com.base.engine.components.geometry;

import java.awt.Color;

public class Ellipse {
	private double centerX;
	private double centerY;
	private double A;
	private double B;
	
	public Ellipse(double centerX, double centerY, double semiMajorAxis, double semiMinorAxis) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.A = semiMajorAxis;
		this.B = semiMinorAxis;
	}
	
	public Ellipse(double centerX, double centerY, double semiMajorAxis, double semiMinorAxis, Color color) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.A = semiMajorAxis;
		this.B = semiMinorAxis;
	}
	
	public double getIntersectX(double a1, double b1, double a2, double b2) {
		return (Math.sqrt(((Math.pow(a2,4)*Math.pow(b1,2)*Math.pow(a1,2))-(Math.pow(a1,4)*Math.pow(b2,2)*Math.pow(a2,2)))/((Math.pow(a2,4)*Math.pow(b1,2))-(Math.pow(a1,4)*Math.pow(b2,2)))));
	}
	
	public double getY(double x) {
		return (B*Math.sqrt((A*A)-(x*x)))/A;
	}
}
