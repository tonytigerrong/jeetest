package com.mariner.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.mariner.entities.Report;
import com.mariner.entities.Reports;

public class CsvParser {
	
	private String csv;
	private Reports rps = new Reports();
	private Scanner scanner;
	private List<String> lines = new ArrayList<String>();
	
	public Reports getRps() {
		return rps;
	}
	public void setRps(Reports rps) {
		this.rps = rps;
	}
	
	public CsvParser(String pathName){
		try {
			this.lines = readFile(pathName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	private List<String> readFile(String pathname) throws FileNotFoundException {

    	File file = new File(pathname); 
    	List<String> result = new ArrayList<String>();
        scanner = new Scanner(file);
        if(scanner.hasNextLine()) {
		     scanner.nextLine();
		}
        while(scanner.hasNextLine()) {
		    result.add(scanner.nextLine());
		}
		return result;
    }
	public boolean parse(){
		boolean result = false;
		for(String line : this.lines){
			String[] ss = line.split(",");
			if(ss.length == 8){
				Report rp = new Report();
				rp.setClient_address(ss[0]);
				rp.setClient_guid(ss[1]);
				rp.setRequest_time(ss[2]);
				rp.setService_guid(ss[3]);
				rp.setRetries_request(ss[4]);
				rp.setPackets_requested(ss[5]);
				rp.setPackets_serviced(ss[6]);
				rp.setMax_hole_size(ss[7]);
				rp.format(2);
				//System.out.println(rp.getI_time());
				if(!rp.needRemove()){
					rps.getReport().add(rp);
				}
				
			}else{
				System.out.println("line=["+line+"] is error format!");
				return result;
			}	
		}
		result = true; 
		return result;
	}
	public static void main(String[] args){
		CsvParser parser = new CsvParser("./bin/reports.csv");
		parser.parse();
		

	}
}
