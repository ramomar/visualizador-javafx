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

public class GeneradasGrafica extends LineChart<String, Number> {
  public GeneradasGrafica(Instancia instancia) {
    super(new CategoryAxis(), hacerEjeY());
    this.setTitle("Llegadas en un día");
    this.getData().add(hacerSerie(hacerDatapoints(instancia.llegadas)));
  }

  private Series<String, Number> hacerSerie(ObservableList<Data<String, Number>> datapoints) {
    return new Series<String, Number>(datapoints);
  }

  private ObservableList<Data<String, Number>> hacerDatapoints(List<Llegada> llegadas) {
    ArrayList<Data<String, Number>> datapoints = new ArrayList<Data<String, Number>>();

    for (Llegada llegada : llegadas) { 
      Data<String, Number> datapoint =
        new Data<String, Number>(llegada.hora.toString(), llegada.cantidadTransacciones);
      datapoints.add(datapoint);
    }

    return FXCollections.observableList(datapoints);
  }

  private static CategoryAxis hacerEjeX() {
    // Asumí que el horario del banco es de 9 a 4 de la tarde,
    // quizas sea mejor hacer la generación de estas categorias de manera
    // dinámica si hay necesidad
    ObservableList<String> categorias = FXCollections.observableList(new ArrayList<String>());
    categorias.add("9");
    categorias.add("10");
    categorias.add("11");
    categorias.add("12");
    categorias.add("13");
    categorias.add("14");
    categorias.add("15");
    categorias.add("16");
    categorias.add("17");
    categorias.add("18");

    CategoryAxis ejeX = new CategoryAxis(categorias);
    ejeX.setLabel("Hora del día");

    return ejeX;
  }

  private static NumberAxis hacerEjeY() {
    NumberAxis ejeY = new NumberAxis();
    ejeY.setLabel("Cantidad de llegadas");
    return ejeY;
  }
}
