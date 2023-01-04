package com.base.engine.components.geometry;

public class Ellipsoid {
	private double a;
	private double b;
	private double f;
	private double gamma0 = 0;
	private double sinSigma = 0;
	private double cosSigma = 0;
	private double sigma = 0;
	private double sinAlpha;
	private double cosSqrdAlpha = 0;
	private double cos2SigmaM = 0;
	public Ellipsoid() {
		this.a = this.b = 1;
		this.f = 0;
	}
	public Ellipsoid(double a, double f) {
		this.a = a;
		this.f = f;
		this.b = (1-f)*a;
	}
	
	public static double getDistanceXorY(double radius, double angle) {
		return Math.sqrt(2*radius*radius*(1-Math.cos(angle)));
	}
	public double getSurfaceDistance(double x0, double y0, double x1, double y1, double sigFigs) {
		double u0 = Math.atan((1-f)*Math.tan(y0));
		double u1 = Math.atan((1-f)*Math.tan(y1));
		double L = x1-x0;
		double gamma1 = L;
		//0.000000000000001
		while(Math.abs(gamma1 - gamma0) > 1*Math.pow(10, -sigFigs)) {
			sinSigma = Math.sqrt(Math.pow(Math.cos(y1)*Math.sin(gamma1),2) + Math.pow((Math.cos(y0)*Math.sin(y1)) - (Math.sin(y0)*Math.cos(y1)*Math.cos(gamma1)), 2));
			cosSigma = (Math.sin(y0)*Math.sin(y1) + Math.cos(y0)*Math.cos(y1)*Math.cos(gamma1));
			sigma = Math.atan2(sinSigma, cosSigma);
			sinAlpha = (Math.cos(y0)*Math.cos(y1)*Math.sin(gamma1))/sinSigma;
			cosSqrdAlpha = 1-(sinAlpha*sinAlpha);
			cos2SigmaM = cosSigma - ((2*Math.sin(y0)*Math.sin(y1)/cosSqrdAlpha));
			double C = (f/16)*cosSqrdAlpha*(4+f*(4-3*cosSqrdAlpha));
			System.out.println("Gamma0: " + gamma0);
			gamma0 = gamma1;
			gamma1 = L + ((1-C)*f*sinAlpha*(sigma + (C*sinSigma*(cos2SigmaM+C*cosSigma*(-1 + (2*cos2SigmaM*cos2SigmaM))))));
			System.out.println("Gamma1: " + gamma1 + "; L: "+L);
		}
		double uSqrd = cosSqrdAlpha * ((a*a)-(b*b))/(b*b);
		double A = 1+((uSqrd/16384)*(4096+(uSqrd*(-768+(uSqrd*(320-(175*uSqrd)))))));
		double B = (uSqrd/1024)*(256+(uSqrd*(-128+(uSqrd*(74-(47*uSqrd))))));
		double deltaSigma = B*sinSigma*(cos2SigmaM + (0.25*B*(cosSigma*(-1+(2*cos2SigmaM*cos2SigmaM)-((B/6)*cos2SigmaM*(-3+(4*sinSigma*sinSigma))*(-3+(4*cos2SigmaM*cos2SigmaM)))))));
		System.out.println("Distance: " + (b*(A*(sigma-deltaSigma))));
		return (b*(A*(sigma-deltaSigma)));
	}
	
	public void getSurfacePoint(double longitude, double latitude) {
		
	}
}
