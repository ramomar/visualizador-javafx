package com.posgrado.gui;

import com.posgrado.comun.Instancia;
import com.posgrado.gui.graficas.AcumuladaLlegadasGrafica;
import com.posgrado.gui.graficas.RealLlegadasGrafica;
import com.posgrado.parser.CargadorInstancias;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Toggle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

// TODO: Quizas en el futuro sea buena idea migrar a FXML para tener MVC
// TODO: Encontrar forma mejor de mantener el estado de las graficas
public class Visualizador {
  private Stage                 escenario;
  private Scene                 escenaPrincipal;
  private BorderPane            contenedorPrincipal;
  private VBox                  contenedorSuperior;
  private MenuPrincipal         menuPrincipal;
  private MenuOpcionesContenido menuOpcionesContenido;

  // Mapeo de un nombre de un archivo de instancia -> Instancia
  private Map<String, Instancia> instanciasCargadas;

  // Mapeo de un nombre de un archivo de instancia -> Grafica (LineChart<String, Number>)
  private Map<String, Graficas> graficasCargadas;

  // Tipo de grafica actual
  private MenuOpcionesContenido.TipoGrafica tipoGrafica = MenuOpcionesContenido.TipoGrafica.Real;
  private String graficaActual;

  public Visualizador(Stage stage, int width, int heigth) {
    escenario             = stage;
    contenedorPrincipal   = new BorderPane();
    contenedorSuperior    = new VBox();
    menuPrincipal         = new MenuPrincipal(cargarInstanciasHandler);
    menuOpcionesContenido = new MenuOpcionesContenido(instanciaSeleccionadaListener, tipoGraficaListener);

    contenedorSuperior.setAlignment(Pos.CENTER);
    contenedorSuperior.setSpacing(10);
    contenedorSuperior.getChildren().add(menuPrincipal);
    contenedorSuperior.getChildren().add(menuOpcionesContenido);
    contenedorPrincipal.setTop(contenedorSuperior);

    escenaPrincipal = new Scene(contenedorPrincipal, width, heigth);
    escenario.setScene(escenaPrincipal);
  }

  public void mostrar() {
    escenario.show();
  }

  private EventHandler<ActionEvent> cargarInstanciasHandler = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      instanciasCargadas = new HashMap<String, Instancia>();
      graficasCargadas   = new HashMap<String, Graficas>();

      File carpetaInstancias = hacerSelectorDirectorio().showDialog(escenario);

      ObservableList<String> nombresArchivos = FXCollections.observableArrayList();

      for (File f : carpetaInstancias.listFiles()) {
        String    nombreArchivo = f.getName();
        Instancia instancia     = CargadorInstancias.cargar(f); // TODO: manejar excepcion

        instanciasCargadas.put(nombreArchivo, instancia);
        nombresArchivos.add(nombreArchivo);
      }

      menuOpcionesContenido.setListaInstancias(nombresArchivos);
    }
  };

  private ChangeListener<Toggle> tipoGraficaListener = new ChangeListener<Toggle>() {
    @Override
    public void changed(ObservableValue observable, Toggle anterior, Toggle seleccionado) {
      LineChart<String, Number> grafica;
      if (seleccionado.getUserData() == MenuOpcionesContenido.TipoGrafica.Real) {
        grafica =  graficasCargadas.get(graficaActual).real;
        tipoGrafica = MenuOpcionesContenido.TipoGrafica.Real;
      } else {
        grafica = graficasCargadas.get(graficaActual).acumulada;
        tipoGrafica = MenuOpcionesContenido.TipoGrafica.Acumulada;
      }

      contenedorPrincipal.setCenter(grafica);
    }
  };


  private ChangeListener<String> instanciaSeleccionadaListener = new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue ov, String anterior, String seleccionado) {
      Instancia instancia = instanciasCargadas.get(seleccionado);
      LineChart<String, Number> grafica;
      Graficas graficas;

      if (graficasCargadas.containsKey(seleccionado)) {
        graficas = graficasCargadas.get(seleccionado);
      } else {
        LineChart<String, Number> real      = new RealLlegadasGrafica(instancia);
        LineChart<String, Number> acumulada = new AcumuladaLlegadasGrafica(instancia);
        graficas = new Graficas(real, acumulada);
        graficasCargadas.put(seleccionado, graficas);
      }

      if (tipoGrafica == MenuOpcionesContenido.TipoGrafica.Real) {
        grafica = graficas.real;
      }
      else {
        grafica = graficas.acumulada;
      }

      graficaActual = seleccionado;
      contenedorPrincipal.setCenter(grafica);
    }
  };

  public static DirectoryChooser hacerSelectorDirectorio() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Seleccionar instancias");
    directoryChooser.setInitialDirectory(new File("."));
    return directoryChooser;
  }

  class Graficas {
    public final LineChart<String, Number> real;
    public final LineChart<String, Number> acumulada;

    public Graficas(LineChart<String, Number> real, LineChart<String, Number> acumulada) {
      this.real      = real;
      this.acumulada = acumulada;
    }
  }

}
