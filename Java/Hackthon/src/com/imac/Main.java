package com.imac;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.codehaus.jettison.json.JSONObject;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;

import com.google.gson.JsonObject;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import scala.Tuple2;

public class Main {
	
	public static void main(String[] args) {
		
		SparkConf conf = new SparkConf().setAppName("Hackson");
		conf.set("es.index.auto.create", "true");
		conf.set("es.nodes", "10.26.1.134:9200");
		conf.set("es.resource", args[1]);
		conf.set("es.input.json" , "true");
		
		JavaSparkContext sparkContext = new JavaSparkContext(conf);
		JavaRDD<String> result = sparkContext.textFile(args[0]).mapToPair(new PairFunction<String, String, Integer>() {

			@Override
			public Tuple2<String, Integer> call(String arg0) throws Exception {
				// TODO Auto-generated method stub
				return new Tuple2<String, Integer>(arg0, 1);
			}
		}).reduceByKey(new Function2<Integer, Integer, Integer>() {
			
			@Override
			public Integer call(Integer arg0, Integer arg1) throws Exception {
				// TODO Auto-generated method stub
				return arg0+arg1;
			}
		}).mapToPair(new PairFunction<Tuple2<String,Integer>, Integer, String>() {

			@Override
			public Tuple2<Integer, String> call(Tuple2<String, Integer> arg0) throws Exception {
				// TODO Auto-generated method stub
				return new Tuple2<Integer, String>(arg0._2, arg0._1);
			}
		}).sortByKey(false).map(new Function<Tuple2<Integer,String>, String>() {

			@Override
			public String call(Tuple2<Integer, String> arg0) throws Exception {
				// TODO Auto-generated method stub
				JSONObject jsonOutput = new JSONObject();
				jsonOutput.put("Text", arg0._2);
				jsonOutput.put("Value", arg0._1);
				jsonOutput.put("@timestamp", nowDate());
				return jsonOutput.toString();
			}
		});
		
		List<String> list = result.collect();
		
//		for (String string : list) {
//			System.out.println(string);
//		}

		JavaEsSpark.saveToEs(result, "test/Hackthon");
	}
	
	public static String nowDate() {
		return new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()).replace(" ", "T") + "Z";
	}
}
