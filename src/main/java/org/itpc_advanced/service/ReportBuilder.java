package org.itpc_advanced.service;

import java.util.List;

import javafx.scene.input.ClipboardContent;

public class ReportBuilder {
	
	public static ClipboardContent getReport(List<Double> values) {
		StringBuffer casualText = new StringBuffer("");
	    StringBuffer htmlText   = new StringBuffer("");
	    String trStyle = "<tr style=\"height:22pt\">";
	    String tdStyle = "<td style=\"border-left:solid #000000 1pt;"
	    			   + "border-right:solid #000000 1pt; "
	    			   + "border-bottom:solid #000000 1pt;"
	    			   + "border-top:solid #000000 1pt;"
	    			   + "vertical-align:top;"
	    			   + "padding:5pt 5pt 5pt 5pt;"
	    			   + "overflow:hidden;"
	    			   + "overflow-wrap:break-word;\">";
	    String divStyle = "<div dir=\"ltr\" style=\"margin-left:0pt;\" align=\"center\">";
	    htmlText.append("<table><tbody>");
	    
	    for (double value : values) {
	    	casualText.append(value + " " + "\n");
	    	htmlText.append(trStyle + tdStyle + divStyle + value + "</div></td></tr>");
	    }
	    htmlText.append("</tbody></table>");
	    ClipboardContent content = new ClipboardContent();
	    content.putString(casualText.toString().replace(".", ","));
	    content.putHtml(htmlText.toString().replace(".", ","));
        
	    return content;
	}

}
