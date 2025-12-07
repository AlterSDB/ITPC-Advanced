package org.itpc_advanced.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.itpc_advanced.model.DataFile;
import org.itpc_advanced.model.Settings;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class ReportBuilder {

	public static void buildReport(DataFile df) {
		if (df == null) {
			return;
		}
		
		Clipboard clipboard = Clipboard.getSystemClipboard();
		clipboard.setContent(getReport(df));
	}

	public static ClipboardContent getReport(DataFile df) {
		StringBuilder casualText = new StringBuilder();
		StringBuilder htmlText   = new StringBuilder();
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

	    ArrayList<Double> maxTemps = new ArrayList<>(df.getMaxTemps());
	    ArrayList<Double> minTemps = new ArrayList<>(df.getMinTemps());

		if (Settings.getInstance().isShuffleValues()) {
			Collections.shuffle(maxTemps);
			Collections.shuffle(minTemps);
		}

	    for (double value : maxTemps) {
	    	casualText.append(value).append(" ").append("\n");
	    	htmlText.append(trStyle).append(tdStyle).append(divStyle).append(value).append("</div></td></tr>");
	    }

	    for (double value : minTemps) {
	    	casualText.append(value).append(" ").append("\n");
	    	htmlText.append(trStyle).append(tdStyle).append(divStyle).append(value).append("</div></td></tr>");
	    }

	    casualText.append(df.getAverageMax()).append(" ").append("\n");
    	htmlText.append(trStyle).append(tdStyle).append(divStyle).append(df.getAverageMax()).append("</div></td></tr>");

    	casualText.append(df.getAverageMin()).append(" ").append("\n");
    	htmlText.append(trStyle).append(tdStyle).append(divStyle).append(df.getAverageMin()).append("</div></td></tr>");

    	casualText.append(df.getRelativeMax()).append(" ").append("\n");
    	htmlText.append(trStyle).append(tdStyle).append(divStyle).append(df.getRelativeMax()).append("</div></td></tr>");

    	casualText.append(df.getRelativeMin()).append(" ").append("\n");
    	htmlText.append(trStyle).append(tdStyle).append(divStyle).append(df.getRelativeMin()).append("</div></td></tr>");

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

        return values.toString()
                .replaceAll("[\\[\\]]", "")
                .replaceAll(",\\s+", "\n");
	}

}