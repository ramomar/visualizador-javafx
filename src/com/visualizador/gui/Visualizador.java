package com.visualizador.gui;

import com.visualizador.comun.Instancia;
import com.visualizador.gui.graficas.AcumuladaLlegadasGrafica;
import com.visualizador.gui.graficas.RealLlegadasGrafica;
import com.visualizador.parser.CargadorInstancias;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Toggle;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
    menuPrincipal         = new MenuPrincipal(cargarInstanciasHandler, exportarComoImagenHandler);
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

  private EventHandler<ActionEvent> exportarComoImagenHandler = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      SnapshotParameters params = new SnapshotParameters();
      params.setTransform(new Scale(2, 2));
      WritableImage img = contenedorPrincipal.getCenter().snapshot(params, null);
      BufferedImage bi  = SwingFXUtils.fromFXImage(img, null);
      File archivo      = hacerSelectorArchivo("Guardar archivo").showSaveDialog(escenario);
      if (archivo != null) {
        try {
          ImageIO.write(bi, "png", archivo);
        } catch (IOException ex){
          System.out.println(ex.getMessage());
        }
      }
    }
  };

  private EventHandler<ActionEvent> cargarInstanciasHandler = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      instanciasCargadas = new HashMap<String, Instancia>();
      graficasCargadas   = new HashMap<String, Graficas>();

      File carpetaInstancias = hacerSelectorDirectorio("Seleccionar instancias").showDialog(escenario);

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
        LineChart<String, Number> real      = new RealLlegadasGrafica(instancia, new ArrayList(instanciasCargadas.values()));
        LineChart<String, Number> acumulada = new AcumuladaLlegadasGrafica(instancia, new ArrayList(instanciasCargadas.values()));
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


  private static FileChooser hacerSelectorArchivo(String titulo) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(titulo);
    fileChooser.setInitialFileName(LocalDate.now() + ".png");
    return fileChooser;
  }

  private static DirectoryChooser hacerSelectorDirectorio(String titulo) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle(titulo);
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
