package android.demo;

import java.io.Serializable;

public class Building implements Serializable {
    public int            num;
    public String      name;
    public double    leftLat;
    public double    leftLong;
    public double    rightLat;
    public double    rightLong;
    public String      openTime;
    public String      introduction;
    public String      news;
    
    public Building(){
	
    }
    public Building(int num, String name, double leftLat, double leftLong, 
	    			double rightLat, double rightLong, String openTime, 
	    			String introduction, String news){
	this.num = num;
	this.name = name;
	this.leftLat = leftLat;
	this.leftLong = leftLong;
	this.rightLat = rightLat;
	this.rightLong = rightLong;
	this.openTime = openTime;
	this.introduction = introduction;
	this.news = news;
    }
    
    public double getLatCenter(){
	return (this.leftLat + this.rightLat) / 2;
    }

    public double getLongCenter(){
	return (this.leftLong + this.rightLong) / 2;
    }
}
