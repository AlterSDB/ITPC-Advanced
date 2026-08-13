package org.itpc_advanced.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.itpc_advanced.model.TemperatureStats;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class ReportBuilder {

	public static void buildReport(TemperatureStats df) {
		if (df == null) {
			return;
		}
		
		Clipboard clipboard = Clipboard.getSystemClipboard();
		clipboard.setContent(getReport(df));
	}

	public static ClipboardContent getReport(TemperatureStats df) {
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

	    ArrayList<Double> maxTemps = new ArrayList<Double>(df.getMaxTemperaturePoints());
	    ArrayList<Double> minTemps = new ArrayList<Double>(df.getMinTemperaturePoints());

		if ( true/*Settings.getInstance().isShuffleValues()*/ ) {
			Collections.shuffle(maxTemps);
			Collections.shuffle(minTemps);
		}

	    for (double value : maxTemps) {
	    	casualText.append(value + " " + "\n");
	    	htmlText.append(trStyle + tdStyle + divStyle + value + "</div></td></tr>");
	    }

	    for (double value : minTemps) {
	    	casualText.append(value + " " + "\n");
	    	htmlText.append(trStyle + tdStyle + divStyle + value + "</div></td></tr>");
	    }

	    casualText.append(df.getAverageMax() + " " + "\n");
    	htmlText.append(trStyle + tdStyle + divStyle + df.getAverageMax() + "</div></td></tr>");

    	casualText.append(df.getAverageMin() + " " + "\n");
    	htmlText.append(trStyle + tdStyle + divStyle + df.getAverageMin() + "</div></td></tr>");

    	casualText.append(df.getRelativeMax() + " " + "\n");
    	htmlText.append(trStyle + tdStyle + divStyle + df.getRelativeMax() + "</div></td></tr>");

    	casualText.append(df.getRelativeMin() + " " + "\n");
    	htmlText.append(trStyle + tdStyle + divStyle + df.getRelativeMin() + "</div></td></tr>");

	    htmlText.append("</tbody></table>");
	    ClipboardContent content = new ClipboardContent();
	    content.putString(casualText.toString().replace(".", ","));
	    content.putHtml(htmlText.toString().replace(".", ","));

	    return content;
	}

	public static String getTextFromRawValues(List<Double> values) {
		if (values == null || values.isEmpty()) {
			return "";
		}

		String result = values.toString()
				.replaceAll("[\\[\\]]", "")
				.replaceAll(",\\s+", "\n");
		
		return result;
	}

}