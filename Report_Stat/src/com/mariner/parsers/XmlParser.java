package com.mariner.parsers;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.mariner.entities.Report;
import com.mariner.entities.Reports;


public class XmlParser {

	private JAXBContext jaxbContext;
	private Unmarshaller jaxbUnmarshaller;
	private Reports rps = new Reports();
	private File inputFile;
	
	public Reports getRps() {
		return rps;
	}
	public XmlParser(String pathname){
		inputFile = new File(pathname); 
		
	}
	public boolean parse(){
		boolean result = false;
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Reports.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Reports rps1 = (Reports) jaxbUnmarshaller.unmarshal(this.inputFile);
			for(Report rp : rps1.getReport()){
				rp.format(1);
				//System.out.println(rp.getI_time());
				if(!rp.needRemove()){
					rps.getReport().add(rp);
				}
				
			}
			result = true;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args){
		XmlParser parser = new XmlParser("./bin/reports.xml");
		parser.parse();
	}
}
