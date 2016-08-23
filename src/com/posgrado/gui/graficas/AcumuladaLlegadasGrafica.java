package com.posgrado.gui.graficas;

import com.posgrado.comun.Instancia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcumuladaLlegadasGrafica extends GraficaInstanciaBase {

  public AcumuladaLlegadasGrafica(Instancia instancia, List<Instancia> instancias) {
    super(instancia, instancias);
    this.setTitle("Llegadas en un día");
  }

  protected List<Data<String, Number>> hacerDatapoints(Instancia instancia, Map<Integer, String> mapeoCategorias) {
    ArrayList<Data<String, Number>> datapoints = new ArrayList<>();

    int acumulado = 0;
    for (Integer hora : instancia.getHorasConLlegadasDeClientes()) {
      acumulado += instancia.getLlegadasEnHora(hora).size();
      Data<String, Number> datapoint = new Data<>(mapeoCategorias.get(hora), acumulado);
      datapoints.add(datapoint);
    }

    return datapoints;
  }

  protected List<Data<String, Number>> hacerDatapointsPromedio(List<Instancia> instancias, Map<Integer, String> mapeoCategorias) {
    ArrayList<Data<String, Number>> datapoints     = new ArrayList<>();
    Map<Integer, Integer> acumuladoLlegadasPorHora = new HashMap<>();

    for (Instancia instancia : instancias) {
      for (Integer hora : instancia.getHorasConLlegadasDeClientes()) {
        if (acumuladoLlegadasPorHora.containsKey(hora)) {
          acumuladoLlegadasPorHora.put(hora, acumuladoLlegadasPorHora.get(hora) + instancia.getLlegadasEnHora(hora).size());
        } else {
          acumuladoLlegadasPorHora.put(hora, instancia.getLlegadasEnHora(hora).size());
        }
      }
    }

    int acum = 0;

    for(Map.Entry<Integer, Integer> entry : acumuladoLlegadasPorHora.entrySet()) {
      acum += entry.getValue()/instancias.size();
      datapoints.add(new Data<>(mapeoCategorias.get(entry.getKey()), acum));
    }

    return datapoints;
  }
}
