package com.imac;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.codehaus.jettison.json.JSONObject;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;

public class Biology {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("Hackson");
		conf.set("es.index.auto.create", "true");
		conf.set("es.nodes", "10.26.1.134:9200");
		conf.set("es.resource", args[1]);
		conf.set("es.input.json" , "true");
		
		JavaSparkContext sparkContext = new JavaSparkContext(conf);
		JavaRDD<String> result = sparkContext.textFile(args[0]).map(new Function<String, String>() {

			@Override
			public String call(String arg0) throws Exception {
				String[] str = arg0.split(",");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("NO", str[0]);
				jsonObject.put("CHN", str[4]);
				jsonObject.put("EN", str[5]);
				jsonObject.put("TOWN", str[10].split(" ")[0]);
				jsonObject.put("@timestamp", getDate(str[7]));
				return jsonObject.toString();
			}
		});
		
//		List<String> list = result.collect();
//		
//		for (String string : list) {
//			System.out.println(string);
//		}
		
//		JavaEsSpark.saveToEs(result, "Hackthon/Biology");
		JavaEsSpark.saveToEs(result, "test/Hackthon");
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
