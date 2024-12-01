package org.itpc_advanced.App;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.input.ClipboardContent;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DataFile {

	private static int      filesCounter = 0;
	private List<Double>    values;
	private List<Double>    sortedValues;
	private BooleanProperty fileExists;
	private IntegerProperty id;
	private IntegerProperty targetTemperature;
	private DoubleProperty  averageMax;
	private DoubleProperty  averageMin;
	private DoubleProperty  relativeMax;
	private DoubleProperty  relativeMin;
	private boolean         chartExists;
	private Settings        settings;
	private double[]        upperAndLowerBounds;
	
	public DataFile(byte[] byteData) {
		settings          = Settings.getInstance();
		fileExists        = new SimpleBooleanProperty();
		id                = new SimpleIntegerProperty();
		targetTemperature = new SimpleIntegerProperty();
		averageMax        = new SimpleDoubleProperty();
		averageMin        = new SimpleDoubleProperty();
		relativeMax       = new SimpleDoubleProperty();
		relativeMin       = new SimpleDoubleProperty();
		filesCounter++;
		id.set(filesCounter);
		values            = separateValues(byteData);
		values            = removeParasiticValues(values);
		sortedValues      = new ArrayList<Double>(values);
		Collections.sort(sortedValues);
		fileExists.set(!values.isEmpty());

		if(fileExists.get()) {
			calculateAllDerivatives();
		}

		upperAndLowerBounds = findUpperAndLowerBounds();
		targetTemperature.set(findTargetValue());
		System.out.println("Чистые данные файла " + getID() + " : " + Arrays.toString(byteData));
		System.out.println("Отделённые значения файла " + getID() + " : " + values.toString());
		System.out.println("Целевая температура: " + targetTemperature.get());
	}

	public DataFile(int i) {				// Mock Constructor
		byte[] data;

		switch(i) {
		case 1: data  = ByteSequence.Examples.EMPTYDATA_EXAMPLE; break;
		case 2: data  = ByteSequence.Examples.DATA_EXAMPLE_1;    break;
		case 3: data  = ByteSequence.Examples.DATA_EXAMPLE_2;    break;
		case 4: data  = ByteSequence.Examples.DATA_EXAMPLE_3;    break;
		case 5: data  = ByteSequence.Examples.DATA_EXAMPLE_4;    break;
		default: data = ByteSequence.Examples.EMPTYDATA_EXAMPLE; break;
		}

		settings          = Settings.getInstance();
		fileExists        = new SimpleBooleanProperty();
		id                = new SimpleIntegerProperty();
		targetTemperature = new SimpleIntegerProperty();
		averageMax        = new SimpleDoubleProperty();
		averageMin        = new SimpleDoubleProperty();
		relativeMax       = new SimpleDoubleProperty();
		relativeMin       = new SimpleDoubleProperty();
		values            = separateValues(data);
		values            = removeParasiticValues(values);
		sortedValues      = new ArrayList<Double>(values);

		Collections.sort(sortedValues);
		fileExists.set(!values.isEmpty());
		if(fileExists.get()) {
			calculateAllDerivatives();
		}
		upperAndLowerBounds = findUpperAndLowerBounds();
		targetTemperature.set(findTargetValue());
		filesCounter++;
		id.set(filesCounter);
		System.out.println("Чистые данные файла " + getID() + " : " + Arrays.toString(data));
		System.out.println("Отделённые значения файла " + getID() + " : " + values.toString());
		System.out.println("Целевая температура: " + targetTemperature.get());
	}

	private ArrayList<Double> separateValues(byte[] data) {
		ArrayList<Double> values = new ArrayList<>();
		try {
			for(int i = 19; i < data.length; i += 2) {
				if(data[i] == -35 && data[i + 1] == 125) {
					break;
				}

				if(data[i] == -1) {
					break;
				}

				values.add(bytesToValue(data[i], data[i + 1]));
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Неожиданный конец файла, чтение завершено");
		}

		return values;
	}

    /**
     * Remove parasitic values from the list of values.
     * Parasitic is the values ​​that are significantly out of line 
     * or are at the upper or lower bound.
     * 
     * @param values
     *
     */
	private ArrayList<Double> removeParasiticValues(List<Double> values) {
		if(values.isEmpty()) {
			return (ArrayList<Double>) values;
		}

		System.out.println("Алгоритм удаления паразитных значений запущен");
		ArrayList<Double> resultValues    = new ArrayList<Double>(values);
		ArrayList<Double> sortedRawValues = new ArrayList<Double>(values);
		Collections.sort(sortedRawValues);
		double middleValue  = sortedRawValues.get( (sortedRawValues.size()/2) );
		double minValue     = sortedRawValues.get(0);
		double maxValue     = sortedRawValues.get(sortedRawValues.size() - 1);
		double maxDeviation = Math.max(maxValue - middleValue, middleValue - minValue);	

		if(maxDeviation > settings.getMaxDeviation()) {
			System.out.println("\u001B[31m" + "Отклонение некоторых значений превышает максимально допустимое отклонение" + "\u001B[0m");
			for(double value : values) {
				if(Math.abs(middleValue - value) > settings.getMaxDeviation()) {
				resultValues.remove(value);
				} 
			} 
		}
		System.out.println("Алгоритм удаления паразитных значений завершил работу");

		return resultValues;
	}

	private void calculateAllDerivatives() {
		if(sortedValues.size() < 20) {
			System.out.println("У файла " + getID() + " недостаточно данных: " + sortedValues.size() + ", расчёт приостановлен.");
			return;
		}
		double[] minArray = new double[10];
		double[] maxArray = new double[10];
		
		for(int i = 0; i < 10; i++) {
			minArray[i] = sortedValues.get(i);
			maxArray[i] = sortedValues.get((sortedValues.size() - 1) - i);
		}
		averageMax.set(findAverage(maxArray));
		averageMin.set(findAverage(minArray));
		relativeMax.set(findRelative(averageMax.get(), targetTemperature.get()));
		relativeMin.set(findRelative(averageMin.get(), targetTemperature.get()));
	}

	private double[] findUpperAndLowerBounds() {
		if(sortedValues.size() < 5) {
			return new double[] {0, 10};
		}

		double[] bounds     = new double[2];
		double   lowerBound = sortedValues.get(0);
		double   upperBound = sortedValues.get(sortedValues.size() - 1);
		upperBound = Math.ceil(upperBound) / 10;
		upperBound = Math.ceil(upperBound) * 10;
		lowerBound = Math.floor(lowerBound) / 10;
		lowerBound = Math.floor(lowerBound) * 10;
		bounds[0]  = lowerBound;
		bounds[1]  = upperBound;
		
		if(bounds[1] - bounds[0] == 20) {
			bounds[0] += 5;
			bounds[1] -= 5;
			while(sortedValues.get(sortedValues.size() - 1) > bounds[1]) {
				bounds[0] += 1;
				bounds[1] += 1;
			}
			while(sortedValues.get(1) < bounds[0]) {
				bounds[0] -= 1;
				bounds[1] -= 1;
			}
		}

		return bounds;
	}

	private int findTargetValue() {
		double sum = 0;
		for(Double value : values) {
			value = Math.round(value) / 10.0;
			value = Math.round(value) * 10.0;
			sum += value;
		}

		double target = sum / values.size();
		target = Math.round(target) / 10.0;
		target = Math.round(target) * 10.0;

		return (int) target;
	}

	private double findRelative(double average, double target) {
		double relative = average - target;
		return Math.ceil(relative * 10) / 10;
	}

	private double findAverage(double[] array) {
		double average = 0.0;
		for(double value : array) {
			average += value;
		}
		average /= array.length;

		return Math.floor(average * 10) / 10;
	}

	private List<Double> getPackedValues() {
		List<Double> packedValues = new ArrayList<Double>();
		List<Double> minArray     = new ArrayList<Double>();
		List<Double> maxArray     = new ArrayList<Double>();
		
		for(int i = 0; i < 10; i++) {
			minArray.add(sortedValues.get(i));
			maxArray.add(sortedValues.get((sortedValues.size() - 1) - i));
		}
		
		if (settings.isShuffleValues()) {
			Collections.shuffle(maxArray);
			Collections.shuffle(minArray);
		}
		packedValues.addAll(maxArray);
		packedValues.addAll(minArray);
		packedValues.add(averageMax.get());
		packedValues.add(averageMin.get());
		packedValues.add(relativeMax.get());
		packedValues.add(relativeMin.get());

		return packedValues;
	}

	private double bytesToValue(byte x, byte y) {
		int intResult = (y << 8) | (x & 0xFF);
		return (double) intResult / 10;
	}

	public ObservableList<XYChart.Data> getChartData() {
		ObservableList<XYChart.Data> chartData = FXCollections.observableArrayList();
		double time = 0.0;
		for(int i = 0; i < values.size(); i++){
			chartData.add(new XYChart.Data(time, values.get(i)));
			time += settings.getTimeStep();
		}

		return chartData;
	}
	
	public void setNewTarget(int target) {
		targetTemperature.set(target);
		calculateAllDerivatives();
	}
	public void setChartExists() {
		chartExists = true;
		}

	public ClipboardContent getFormattedHTML() {
		List<Double> values     = getPackedValues();
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
		for(double value : values) {
			casualText.append(value + " " + "\n");
			htmlText.append(trStyle + tdStyle + divStyle + value + "</div></td></tr>");
		}
		htmlText.append("</tbody></table>");
		ClipboardContent content = new ClipboardContent();
		content.putString(casualText.toString().replace(".", ","));
		content.putHtml(htmlText.toString().replace(".", ","));

		return content;
	}

	public static void resetFilesCounter() {
		filesCounter = 0;
	}

	public static int      getCount()               { return filesCounter;            }
	public int             getID()                  { return id.get();                }
	public int             getTargetTemperature()   { return targetTemperature.get(); }
	public boolean         isFileExists()           { return fileExists.get();        }
	public boolean         isChartExists()          { return chartExists;             }
	public double          getAverageMin()          { return averageMin.get();        }
	public double          getAverageMax()          { return averageMax.get();        }
	public double          getRelativeMin()         { return relativeMin.get();       }
	public double          getRelativeMax()         { return relativeMax.get();       }
	public double[]        getBounds()              { return upperAndLowerBounds;     }
	public List<Double>    getValues()              { return values;                  }
	public List<Double>    getSortedValues()        { return sortedValues;            }
	public String          getFileName()            { return "Файл " + id.get();      }
	public BooleanProperty isFileExistsProperty()   { return fileExists;              }
	public DoubleProperty  getAverageMinProperty()  { return averageMin;              }
	public DoubleProperty  getAverageMaxProperty()  { return averageMax;              }
	public DoubleProperty  getRelativeMinProperty() { return relativeMin;             }
	public DoubleProperty  getRelativeMaxProperty() { return relativeMax;             }
	public IntegerProperty getTargetTempProperty()  { return targetTemperature;       }
	public IntegerProperty getIDProperty()          { return id;                      }

}
