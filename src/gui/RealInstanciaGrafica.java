import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.time.LocalTime;
import javafx.util.StringConverter;

// no es la mejor opción heredar de LineChart, hay que cambiar esto
public class RealInstanciaGrafica extends LineChart<String, Number> {

  private static Map<Integer, String> mapeoCategorias = hacerMapeoCategorias(LocalTime.parse("09:00"));
  private static ArrayList<String> categorias         = new ArrayList<String>(mapeoCategorias.values());

  public RealInstanciaGrafica(Instancia instancia) {
    super(hacerEjeX(FXCollections.observableList(categorias)), hacerEjeY());
    Collections.sort(categorias);
    this.setTitle("Llegadas en un día");
    this.getData().add(hacerSerie(hacerDatapoints(instancia)));
  }

  // TODO: cambiarle el nombre a esta serie
  private Series<String, Number> hacerSerie(ObservableList<Data<String, Number>> datapoints) {
    return new Series<String, Number>("Llegadas de una instancia", datapoints);
  }

  private ObservableList<Data<String, Number>> hacerDatapoints(Instancia instancia) {
    ArrayList<Data<String, Number>> datapoints = new ArrayList<Data<String, Number>>();

    for (Integer hora : instancia.getHorasEnInstancia()) {
      Data<String, Number> datapoint =
        new Data<String, Number>(mapeoCategorias.get(hora), instancia.getLlegadasEnHora(hora).size());
      datapoints.add(datapoint);
    }

    return FXCollections.observableList(datapoints);
  }

  private static CategoryAxis hacerEjeX(ObservableList<String> categorias) {
    CategoryAxis ejeX = new CategoryAxis(categorias);
    ejeX.setLabel("Intervalo de hora del día");
    return ejeX;
  }

  private static NumberAxis hacerEjeY() {
    NumberAxis ejeY = new NumberAxis();
    ejeY.setLabel("Cantidad de clientes");
    return ejeY;
  }


  // TODO: Reescribir esto para las categorias
  private static Map<Integer, String> hacerMapeoCategorias(LocalTime horaInicial) {
    Map<Integer, String> categorias = new HashMap<Integer, String>();
    for (int i=0; i<8; i++) {
      categorias.put(horaInicial.getHour(), horaInicial.toString() + " - " + horaInicial.plusHours(1).minusMinutes(1).toString());
      horaInicial = horaInicial.plusHours(1);
    }

    return categorias;
  }
}

