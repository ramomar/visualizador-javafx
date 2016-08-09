package com.posgrado.gui.graficas;

import com.posgrado.comun.Instancia;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

// TODO: quizas se pueda nombrar a esto de una manera mejor
public class ResumenRealLlegadasGrafica extends GraficaInstanciaBase {

  public ResumenRealLlegadasGrafica(Instancia instancia) {
    super(instancia);
    this.setTitle("Llegadas en un día (Real)");
  }

  protected List<Data<String, Number>> hacerDatapoints(Instancia instancia, Map<Integer, String> mapeoCategorias) {
    ArrayList<Data<String, Number>> datapoints = new ArrayList<>();

    for (Integer hora : instancia.getHorasConLlegadasDeClientes()) {
      Data<String, Number> datapoint = new Data<>(mapeoCategorias.get(hora), instancia.getLlegadasEnHora(hora).size());
      datapoints.add(datapoint);
    }

    return datapoints;
  }
}

