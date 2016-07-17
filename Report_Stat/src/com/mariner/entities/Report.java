package com.mariner.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Report  implements Comparable<Report>,Log {
	
	@XmlElement(name="client-address")
	private String client_address;
	@XmlElement(name="client-guid")
	private String client_guid;
	@XmlElement(name="request-time")
	private String request_time;
	@XmlElement(name="service-guid")
	private String service_guid;
	@XmlElement(name="retries_request")
	private String retries_request;
	@XmlElement(name="packets-request")
	private String packets_requested;
	@XmlElement(name="packets-serviced")
	private String packets_serviced;
	@XmlElement(name="max_hole_size")
	private String max_hole_size;
 
	private Date requireTime;
	public Date getRequireTime() {
		return requireTime;
	}
	public void setRequireTime(Date requireTime) {
		this.requireTime = requireTime;
	}
	public long getI_time() {
		return i_time;
	}
	public void setI_time(long i_time) {
		this.i_time = i_time;
	}
	private long i_time;
	
	public String getClient_address() {
		return client_address;
	}
	public void setClient_address(String client_address) {
		this.client_address = client_address;
	}
	public String getClient_guid() {
		return client_guid;
	}
	public void setClient_guid(String client_guid) {
		this.client_guid = client_guid;
	}
	public String getRequest_time() {
		return request_time;
	}
	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}
	public String getService_guid() {
		return service_guid;
	}
	public void setService_guid(String service_guid) {
		this.service_guid = service_guid;
	}
	public String getRetries_request() {
		return retries_request;
	}
	public void setRetries_request(String retries_request) {
		this.retries_request = retries_request;
	}
	public String getPackets_requested() {
		return packets_requested;
	}
	public void setPackets_requested(String packets_requested) {
		this.packets_requested = packets_requested;
	}
	public String getPackets_serviced() {
		return packets_serviced;
	}
	public void setPackets_serviced(String packets_serviced) {
		this.packets_serviced = packets_serviced;
	}
	public String getMax_hole_size() {
		return max_hole_size;
	}
	public void setMax_hole_size(String max_hole_size) {
		this.max_hole_size = max_hole_size;
	}
	@Override
	public int compareTo(Report o) {
//		return this.requireTime.compareTo(o.requireTime);
		if(this.i_time - o.i_time > 0) return 1;
		else if((this.i_time - o.i_time) == 0) return 0;
		else return -1;
	}
	public static Comparator<Report> guidComparator = new Comparator<Report>(){

		@Override
		public int compare(Report o1, Report o2) {
			return o1.getService_guid().compareTo(o2.getService_guid());
		}
		
	};
	@Override
	public void format(int type){
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss zzz");
		if(type == 1 || type == 2){//xml csv
			try {
				this.requireTime = ft.parse(this.getRequest_time());
				this.i_time = (long) this.requireTime.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{//json
			this.i_time = Long.valueOf(this.request_time);
			this.request_time = ft.format(new Date(this.i_time));
		}
		
	}
	public boolean needRemove(){
		if(this.getPackets_serviced().equals("0")){
			return true;
		}else{
			return false;
		}
	}
	
	

}
