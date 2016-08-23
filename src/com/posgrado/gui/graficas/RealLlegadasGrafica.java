package com.posgrado.gui.graficas;

import com.posgrado.comun.Instancia;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

// TODO: quizas se pueda nombrar a esto de una manera mejor
public class RealLlegadasGrafica extends GraficaInstanciaBase {

  public RealLlegadasGrafica(Instancia instancia, List<Instancia> instancias) {
    super(instancia, instancias);
    this.setTitle("Llegadas en un día");
  }

  protected List<Data<String, Number>> hacerDatapoints(Instancia instancia, Map<Integer, String> mapeoCategorias) {
    ArrayList<Data<String, Number>> datapoints = new ArrayList<>();

    for (Integer hora : instancia.getHorasConLlegadasDeClientes()) {
      Data<String, Number> datapoint = new Data<>(mapeoCategorias.get(hora), instancia.getLlegadasEnHora(hora).size());
      datapoints.add(datapoint);
    }

    return datapoints;
  }

  protected List<Data<String, Number>> hacerDatapointsPromedio(List<Instancia> instancias, Map<Integer, String> mapeoCategorias) {
    ArrayList<Data<String, Number>> datapoints = new ArrayList<>();
    Map<Integer, Integer> sumatoriaLlegadasPorHora = new HashMap<>();

    for (Instancia instancia : instancias) {
      for (Integer hora : instancia.getHorasConLlegadasDeClientes()) {
        if (sumatoriaLlegadasPorHora.containsKey(hora)) {
          sumatoriaLlegadasPorHora.put(hora, sumatoriaLlegadasPorHora.get(hora) + instancia.getLlegadasEnHora(hora).size());
        } else {
          sumatoriaLlegadasPorHora.put(hora, instancia.getLlegadasEnHora(hora).size());
        }
      }
    }

    for(Map.Entry<Integer, Integer> entry : sumatoriaLlegadasPorHora.entrySet()) {
      datapoints.add(new Data<>(mapeoCategorias.get(entry.getKey()), entry.getValue()/instancias.size()));
    }

    return datapoints;
  }
}

