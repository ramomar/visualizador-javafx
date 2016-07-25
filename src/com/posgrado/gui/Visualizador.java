package com.posgrado.gui;

import com.posgrado.comun.Instancia;
import com.posgrado.parser.CargadorInstancias;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class Visualizador {
  private Stage escenario;
  private Scene escenaPrincipal;
  private BorderPane contenedorPrincipal = new BorderPane();
  private VBox contenedorSuperior        = new VBox();
  private MenuVisualizador menuPrincipal = new MenuVisualizador();

  public Visualizador(Stage stage, int width, int heigth) {
    escenario = stage;
    contenedorSuperior.getChildren().add(menuPrincipal);
    contenedorPrincipal.setTop(contenedorSuperior);

    escenaPrincipal = new Scene(contenedorPrincipal, width, heigth);
    escenario.setScene(escenaPrincipal);
    cambiarGrafica(new File("/Users/Omar/Developer/Visualizador/instancias/2.txt"));
  }

  public void mostrar() {
    escenario.show();
  }

  private void cambiarGrafica(File file) {
    Instancia instancia = CargadorInstancias.cargar(file);
    LineChart<String, Number> grafica = new RealInstanciaGrafica(instancia);
    contenedorPrincipal.setCenter(grafica);
  }

}
