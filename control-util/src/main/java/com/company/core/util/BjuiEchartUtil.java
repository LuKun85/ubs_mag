package com.company.core.util;

import java.util.List;
import java.util.Map;

public class BjuiEchartUtil {

	private StringBuffer option;

	private String title;

	private List<String> legendName;

	private List<String> xAxisData;

	private Map<String,List<String>> seriesMap;
	
	public BjuiEchartUtil(String title,List<String> legendName,List<String> xAxisData,Map<String,List<String>> seriesMap){
		this.title = title;
		this.legendName = legendName;
		this.xAxisData = xAxisData;
		this.seriesMap = seriesMap;
	}

	public String getOption() {
		StringBuffer titles = new StringBuffer("\"title\":{\"text\":\""+title+"\"},");
		
		StringBuffer tooltip = new StringBuffer("\"tooltip\": {\"trigger\": \"axis\"},");
		
		StringBuffer legend = new StringBuffer("\"legend\": {\"data\": [ ");
		for(String legendN:legendName){
			legend.append("\""+legendN+"\",");
		}
		legend.deleteCharAt(legend.length()-1);
		legend.append("]},");
		
		StringBuffer toolbox = new StringBuffer("\"toolbox\": {\"show\": true,\"feature\": {\"mark\": {\"show\": true},\"dataView\": {\"show\": true,\"readOnly\": false},\"magicType\": {\"show\": true,\"type\": [\"line\",\"bar\"]},\"restore\": {\"show\": true},\"saveAsImage\": {\"show\": true}}},");
		StringBuffer calculable = new StringBuffer("\"calculable\": true,");
		
		StringBuffer xAxis = new StringBuffer("\"xAxis\": [{\"type\": \"category\",\"data\": [ ");
		for(String data:xAxisData){
			xAxis.append("\""+data+"\",");
		}
		xAxis.deleteCharAt(xAxis.length()-1);
		xAxis.append("]}],");
		
		StringBuffer yAxis = new StringBuffer(" \"yAxis\": [{\"type\": \"value\",\"splitArea\": {\"show\": true}}],");
		
		StringBuffer series = new StringBuffer("\"series\": [ ");
		for(String legendName : seriesMap.keySet()){
			series.append("{\"name\": \"");
			series.append(legendName).append("\",\"type\": \"bar\",\"data\": [ ");
			List<String> list = seriesMap.get(legendName);
			for(String data : list){
				series.append(data+",");
			}
			series.deleteCharAt(series.length()-1);
			series.append("]},");
		}
		series.deleteCharAt(series.length()-1);
		series.append("]");
		
		option = new StringBuffer("{");
		option.append(titles).append(tooltip).append(legend).append(toolbox).append(calculable).append(xAxis).append(yAxis).append(series).append("}");
		return option.toString();
	}
}
