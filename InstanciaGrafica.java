import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalTime;
import javafx.util.StringConverter;

public class InstanciaGrafica extends LineChart<Number, Number> {
  public InstanciaGrafica(Instancia instancia) {
    super(hacerEjeX(), hacerEjeY());
    this.setTitle("Llegadas en un día");
    this.getData().add(hacerSerie(hacerDatapoints(instancia.llegadas)));
  }

  private Series<Number, Number> hacerSerie(ObservableList<Data<Number, Number>> datapoints) {
    return new Series<Number, Number>(datapoints);
  }

  private ObservableList<Data<Number, Number>> hacerDatapoints(List<Llegada> llegadas) {
    ArrayList<Data<Number, Number>> datapoints = new ArrayList<Data<Number, Number>>();

    for (Llegada llegada : llegadas) { 
      Data<Number, Number> datapoint =
        new Data<Number, Number>(llegada.hora.toSecondOfDay(), llegada.cantidadTransacciones);
      datapoints.add(datapoint);
    }

    return FXCollections.observableList(datapoints);
  }

  private static NumberAxis hacerEjeX() {
    // quizas puede moverse todo esto a una clase
    double lowerBound           = LocalTime.parse("09:00").toSecondOfDay();
    double upperBound           = LocalTime.parse("17:30").toSecondOfDay();
    double cotaCadaHora         = 3600;
    double cotaCadaCuartoDeHora = cotaCadaHora/4;
    NumberAxis ejeX = new NumberAxis(lowerBound, upperBound, cotaCadaCuartoDeHora);
    ejeX.setLabel("Hora del día");
    ejeX.setTickLabelFormatter(new SegundosConverter());
    return ejeX;
  }

  private static NumberAxis hacerEjeY() {
    NumberAxis ejeY = new NumberAxis();
    ejeY.setLabel("Cantidad de transacciones");
    return ejeY;
  }

  private static class SegundosConverter extends StringConverter<Number> {
    public Number fromString(String str) {
      return LocalTime.parse(str).toSecondOfDay();
    }
    
    public String toString(Number seconds) {
      return LocalTime.ofSecondOfDay(seconds.intValue()).toString();
    }
  }
}

