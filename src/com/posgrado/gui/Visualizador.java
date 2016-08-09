package com.posgrado.gui;

import com.posgrado.comun.Instancia;
import com.posgrado.gui.graficas.ResumenRealLlegadasGrafica;
import com.posgrado.parser.CargadorInstancias;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

public class Visualizador {
  private Stage            escenario;
  private Scene            escenaPrincipal;
  private BorderPane       contenedorPrincipal;
  private VBox             contenedorSuperior;
  private MenuVisualizador menuPrincipal;
  private ComboBox selectorInstancias;

  // Mapeo de un nombre de un archivo de instancia -> Instancia
  private Map<String, Instancia> instanciasCargadas;

  // Mapeo de un nombre de un archivo de instancia -> Grafica (LineChart<String, Number>)
  private Map<String, LineChart<String, Number>> graficasCargadas;

  public Visualizador(Stage stage, int width, int heigth) {
    escenario           = stage;
    contenedorPrincipal = new BorderPane();
    contenedorSuperior  = new VBox();
    selectorInstancias  = new ComboBox();
    menuPrincipal       = new MenuVisualizador(cargarInstancias);

    selectorInstancias.valueProperty().addListener(instanciaSeleccionadaListener);

    contenedorSuperior.setAlignment(Pos.CENTER);
    contenedorSuperior.setSpacing(10);
    contenedorSuperior.getChildren().add(menuPrincipal);
    contenedorSuperior.getChildren().add(selectorInstancias);
    contenedorPrincipal.setTop(contenedorSuperior);

    escenaPrincipal = new Scene(contenedorPrincipal, width, heigth);
    escenario.setScene(escenaPrincipal);
  }

  public void mostrar() {
    escenario.show();
  }

  private EventHandler<ActionEvent> cargarInstancias = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      instanciasCargadas = new HashMap<String, Instancia>();
      graficasCargadas   = new HashMap<String, LineChart<String, Number>>();

      File carpetaInstancias = hacerSelectorDirectorio().showDialog(escenario);

      ObservableList<String> nombresArchivos = FXCollections.observableArrayList();

      for (File f : carpetaInstancias.listFiles()) {
        String    nombreArchivo = f.getName();
        Instancia instancia     = CargadorInstancias.cargar(f); // TODO: manejar excepcion

        instanciasCargadas.put(nombreArchivo, instancia);
        nombresArchivos.add(nombreArchivo);
      }

      selectorInstancias.setItems(nombresArchivos);
    }
  };

  private ChangeListener<String> instanciaSeleccionadaListener = new ChangeListener<String>() {
    public void changed(ObservableValue ov, String anterior, String seleccionado) {
      Instancia instancia = instanciasCargadas.get(seleccionado);
      LineChart<String, Number> grafica;

      if (graficasCargadas.containsKey(seleccionado)) {
        grafica = graficasCargadas.get(seleccionado);
      } else {
        grafica = new ResumenRealLlegadasGrafica(instancia);
        graficasCargadas.put(seleccionado, grafica);
      }

      contenedorPrincipal.setCenter(grafica);
    }
  };

  public static DirectoryChooser hacerSelectorDirectorio() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Seleccionar instancias");
    directoryChooser.setInitialDirectory(new File("."));
    return directoryChooser;
  }
}
