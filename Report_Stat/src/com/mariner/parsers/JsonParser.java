package com.mariner.parsers;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



import com.mariner.entities.Report;
import com.mariner.entities.Reports;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
public class JsonParser {
	
	private String pathname;
	// json字符串转
    private  String json;
    private Reports rps = new Reports();
    public Reports getRps() {
		return rps;
	}



	public void setRps(Reports reports) {
		this.rps = reports;
	}



	public String getJson() {
		return json;
	}



	public void setJson(String json) {
		this.json = json;
	}



	public JsonParser(String pathname){
    	try {
			json = readFile(pathname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	
    
    private String readFile(String pathname) throws IOException {

    	File file = new File(pathname); 
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
 

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() );
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }
    public boolean parse(){
    	boolean result = false;
    	try {
			List<Map<String, String>> list = jsonStringToList(getJson());
			for(Map m : list){
				Report rp = new Report();
				rp.setMax_hole_size((String) m.get("max-hole-size"));
				rp.setPackets_serviced((String) m.get("packets-serviced"));
				rp.setPackets_requested((String) m.get("packets-requested"));
				rp.setClient_guid((String) m.get("client-guid"));
				rp.setClient_address((String) m.get("client-address"));
				rp.setRequest_time((String) m.get("request-time"));
				rp.setService_guid((String) m.get("service-guid"));
				rp.setRetries_request((String) m.get("retries-request"));
				rp.format(3);
				//System.out.println(rp.getI_time());
				if(!rp.needRemove()){
					this.rps.getReport().add(rp);
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    /***
     * json字符串转java List
     * @param rsContent
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> jsonStringToList(String rsContent) throws Exception
    {
        JSONArray arry = JSONArray.fromObject(rsContent);
       // System.out.println("json字符串内容如下");
        //System.out.println(arry);
        List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < arry.size(); i++)
        {
            JSONObject jsonObject = arry.getJSONObject(i);
            Map<String, String> map = new HashMap<String, String>();
            for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();)
            {
                String key = (String) iter.next();
                String value = jsonObject.get(key).toString();
                map.put(key, value);
            }
            rsList.add(map);
        }
        return rsList;
    }
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {	
    	JsonParser parser = new JsonParser("./bin/reports.json");
    	parser.parse();
//        List<Map<String, String>> list1 = parser.jsonStringToList(parser.getJson());
//        System.out.println("json字符串成map");
//        for (Map<String, String> m : list1)
//        {
////            System.out.println(m);
//        }
//        System.out.println("map转换成json字符串");
//        for (Map<String, String> m : list1)
//        {
////            JSONArray jsonArray = JSONArray.fromObject(m);
////            System.out.println(jsonArray.toString());
//        }
//        System.out.println("list转换成json字符串");
////        JSONArray jsonArray2 = JSONArray.fromObject(list1);
////        System.out.println(jsonArray2.toString());
    }

  
 
}

 
