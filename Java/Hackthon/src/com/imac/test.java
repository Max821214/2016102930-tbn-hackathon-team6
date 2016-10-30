package com.imac;

public class test {

	public static void main(String[] args) {
		System.out.println(getDate("2013-12-06 PM 12:24"));
	}

	public static String getDate(String string) {
		String[] date = string.split(" ");
		String[] time = date[2].split(":");
		String hour, minute;
		if(date[1].equals("PM") && !time[0].equals("12")){
			hour = String.valueOf((Integer.valueOf(date[2].split(":")[0])+12));
		}else if(date[1].equals("AM") && time[0].equals("12")){
			hour = "00";
		}else {
			hour = date[2].split(":")[0];
		}
		minute = date[2].split(":")[1];
		return date[0]+"T"+hour+":"+minute+":00"+"Z";
	}
}
