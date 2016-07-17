package com.mariner;

import java.io.IOException;
import com.mariner.process.ExtractAndOutput;
/**
 * 
 * 
 * 1. project's name is Report_Stat
 * 2. output folder is ./bin/reports
 * 3. input folder(reports.*) is ./bin
 * 4. Service_Guid summary information print in console
 */
public class Main {

	// Answers for the assignment 
	// dom4j is easy for coding. StAX & Woodstox are better performance but more code, but jaxb is very popular.
	// Using Jaxb for xml parse, it should work for json as well, but I failed by dependencies jar
	// Using Json-lib for json parse
	// Using Jdk1.8's new function groupingBy of Collectors for service_guid groupby operation
	// Using compareTo interface of Comparable for request_time sorting
	// this is the first version, just achieve all required functions. there is still more than one day left.
	// if you need to see an other optimized version, please let me know. if so, i am going to rewrite as the following aspects:
	/**
	 * 
	 * 1. all parser classes show implements from an interface
	 * 2. using jaxb to parse json data, so the JsonParser and XmlParser should be almost same, just some difference of configuration
	 * 3. for sorting, it should build a customized comparable factory, in order to easily sort all attributes of Report.class
	 * 4. merger all dependencies jar in libs folder with pom.xml in order to build the project code neat and easy to deploy.
	 * 5. add some unit test for parsers.
	 */
	public static void main(String[] args) throws IOException {
		ExtractAndOutput do1 = new ExtractAndOutput("./bin/reports");
		do1.extract();
		do1.outPut();
		do1.printSummary();
	}
}
