package org.itpc_advanced.service;

import org.itpc_advanced.model.ProcessedDataFile;

import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class ReportBuilder {
	
	public static void buildReport(TableView<ProcessedDataFile> table) {
		if(table == null) {
			System.out.println("Ошибка: Таблицы не существует.");
			return;
		}
		if(table.getSelectionModel().getSelectedItem() == null) {
			System.out.println("Ошибка: Файл в таблице не выбран.");
			return;
		}

		Clipboard clipboard = Clipboard.getSystemClipboard();
		ProcessedDataFile df = (ProcessedDataFile) table.getSelectionModel().getSelectedItem();
		clipboard.setContent(ReportBuilder.getReport(df));
		
	}

	public static ClipboardContent getReport(ProcessedDataFile df) {
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
	    
	    for (double value : df.getMaxTemps()) {
	    	casualText.append(value + " " + "\n");
	    	htmlText.append(trStyle + tdStyle + divStyle + value + "</div></td></tr>");
	    }
	    for (double value : df.getMinTemps()) {
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

}
