package com.mariner.process;

 

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mariner.entities.Report;

import com.mariner.parsers.CsvParser;
import com.mariner.parsers.JsonParser;
import com.mariner.parsers.XmlParser;

public class ExtractAndOutput {
	
	private XmlParser xmlParser;
	private JsonParser jsonParser;
	private CsvParser csvParser;
	private List<Report> reports = new ArrayList<Report>();
	
	public ExtractAndOutput(String pathName){
//		pathName = "./bin/reports";
		xmlParser = new XmlParser(pathName + ".xml");
		jsonParser = new JsonParser(pathName + ".json");
		csvParser = new CsvParser(pathName + ".csv");
		xmlParser.parse();
		jsonParser.parse();
		csvParser.parse();
	}
	
	public List<Report> extract(){
		reports.addAll(xmlParser.getRps().getReport());
		reports.addAll(jsonParser.getRps().getReport());
		reports.addAll(csvParser.getRps().getReport());

		Collections.sort(reports);
//		for(Report r : reports){
//			System.out.println("********"+r.getRequest_time() + " | "+r.getService_guid());
//		}
		return reports;
	}
	public void outPut(){
		FileWriter w = null;
		try{
			w = new FileWriter("./bin/merge_report.csv");
			w.append("client-address,client-guid,request-time,service-guid,retries-request,packets-requested,packets-serviced,max-hole-size");
			w.append('\n');
			for(Report rp : reports){
				w.append(rp.getClient_address()).append(",");
				w.append(rp.getClient_guid()).append(",");
				w.append(rp.getRequest_time()).append(",");
				w.append(rp.getService_guid()).append(",");
				w.append(rp.getRetries_request()).append(",");
				w.append(rp.getPackets_requested()).append(",");
				w.append(rp.getPackets_serviced()).append("\n");
			}
		}catch(IOException e){
			
		}finally{
			try {
				w.flush();
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}	
	}
	public void printSummary(){
		Map<String, List<Report>> reportsGrouped = reports.stream().collect(Collectors.groupingBy(w -> w.getService_guid()));
		reportsGrouped.forEach((k,v)->System.out.println("service_guid : " + k + " entities' num : " + v.size()));
	}
	public static void main(String[] args){
		ExtractAndOutput do1 = new ExtractAndOutput("");
		do1.extract();
	}
}
