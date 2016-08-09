package com.posgrado.gui.graficas;

import com.posgrado.comun.Instancia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.time.LocalTime;
import java.util.*;

abstract class GraficaInstanciaBase extends LineChart<String, Number> {
    protected CategoryAxis ejeX;
    protected NumberAxis   ejeY;

    public GraficaInstanciaBase(Instancia instancia) {
      super(new CategoryAxis(), new NumberAxis());
      ejeX = (CategoryAxis) this.getXAxis();
      ejeY = (NumberAxis)   this.getYAxis();
      ejeX.setLabel("Intervalo de hora del día");
      ejeY.setLabel("Cantidad de clientes");

      Map<Integer, String> mapeoCategorias = hacerMapeoCategorias(9, 17);

      ArrayList<String> categorias  = new ArrayList<>(mapeoCategorias.values());
      Collections.sort(categorias);
      ejeX.setCategories(FXCollections.observableArrayList(categorias));

      this
        .getData()
        .add(hacerSerie(FXCollections.observableArrayList(hacerDatapoints(instancia, mapeoCategorias))));
    }

    protected abstract List<Data<String, Number>> hacerDatapoints(Instancia instancia,
                                                                  Map<Integer, String> mapeoCategorias);

    protected Series<String, Number> hacerSerie(ObservableList<Data<String, Number>> datapoints) {
        return new Series<>("Llegadas", datapoints);
    }

    private static Map<Integer, String> hacerMapeoCategorias(int horaInicial, int horaFinal) {
      int horas = horaFinal - horaInicial;

      LocalTime hora = LocalTime.of(horaInicial, 0, 0, 0);
      Map<Integer, String> categorias = new HashMap<>();

      for (int i=0; i<horas; i++) {
        categorias.put(hora.getHour(), hora.toString() + " - " + hora.plusHours(1).minusMinutes(1).toString());
        hora = hora.plusHours(1);
      }

      return categorias;
    }
}