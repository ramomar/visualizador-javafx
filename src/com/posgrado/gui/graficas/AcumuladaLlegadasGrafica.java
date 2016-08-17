package com.posgrado.gui.graficas;

import com.posgrado.comun.Instancia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AcumuladaLlegadasGrafica extends GraficaInstanciaBase {

  public AcumuladaLlegadasGrafica(Instancia instancia) {
    super(instancia);
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
}
