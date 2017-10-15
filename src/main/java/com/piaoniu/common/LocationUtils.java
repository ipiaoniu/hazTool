package com.piaoniu.common;

import java.math.BigDecimal;

/**
 * Created by Stephen Cai on 2017-07-04 11:11.
 */
public class LocationUtils {

	private static final  double EARTH_RADIUS = 6378137;//赤道半径
	private static double rad(double d){
		return d * Math.PI / 180.0;
	}
	public static int getDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
		double radLat1 = rad(lat1.doubleValue());
		double radLat2 = rad(lat2.doubleValue());
		double a = radLat1 - radLat2;
		double b = rad(lon1.doubleValue()) - rad(lon2.doubleValue());
		double s = 2 *Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;
		return (int) s;//单位米
	}
}
