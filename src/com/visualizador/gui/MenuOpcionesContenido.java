package com.visualizador.gui;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.beans.value.ChangeListener;

import java.util.List;

public class MenuOpcionesContenido extends GridPane {
  private ComboBox<String> selectorInstancias;
  private ToggleGroup      selectorTipoGrafica;
  private RadioButton      selectorGraficaReal;
  private RadioButton      selectorGraficaAcum;

  public MenuOpcionesContenido(ChangeListener<String> instanciaSeleccionadaListener,
                               ChangeListener<Toggle> tipoGraficaListener) {
    super();

    selectorInstancias = new ComboBox<>();
    selectorInstancias.valueProperty().addListener(instanciaSeleccionadaListener);

    selectorTipoGrafica = new ToggleGroup();
    selectorTipoGrafica.selectedToggleProperty().addListener(tipoGraficaListener);
    selectorGraficaReal = new RadioButton("Real");
    selectorGraficaAcum = new RadioButton("Acumulada");

    selectorGraficaReal.setToggleGroup(selectorTipoGrafica);
    selectorGraficaReal.setUserData(TipoGrafica.Real);
    selectorGraficaReal.setSelected(true);
    selectorGraficaAcum.setToggleGroup(selectorTipoGrafica);
    selectorGraficaAcum.setUserData(TipoGrafica.Acumulada);

    this.setHgap(10);
    this.setVgap(10);
    this.add(selectorGraficaReal, 1, 1);
    this.add(selectorGraficaAcum, 2, 1);
    this.add(selectorInstancias, 3, 1);
  }

  public void setListaInstancias(List<String> nombresArchivos) {
    selectorInstancias.setItems(FXCollections.observableArrayList(nombresArchivos));
  }

  public enum TipoGrafica {
    Real, Acumulada
  }
}
