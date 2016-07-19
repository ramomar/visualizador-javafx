package com.posgrado.gui;

import com.posgrado.comun.Instancia;
import com.posgrado.parser.CargadorInstancias;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class VisualizadorApp extends Application {

  @Override
  public void start(Stage stage) {
    Instancia instancia                = CargadorInstancias.cargar(new File("/Users/Omar/Developer/VisualizadorApp/instancias/1.txt"));
    LineChart<String, Number> grafica  = new RealInstanciaGrafica(instancia);
    LineChart<String, Number> grafica2 = new RealInstanciaGrafica(instancia);

    VBox vbox             = new VBox();
    MenuBar menuPrincipal = hacerMenu(vbox);

    vbox.getChildren().add(menuPrincipal);
    vbox.getChildren().add(grafica);

    Scene scene = new Scene(vbox, 800, 600);
    stage.setTitle("Visualizador");
    stage.setScene(scene);
    stage.show();
  }

  private MenuBar hacerMenu(final VBox vbox) {
    MenuBar barraMenu    = new MenuBar();
    Menu menuArchivo     = new Menu("Archivo");
    MenuItem opcionAbrir = new MenuItem("Abrir carpeta");

    EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent t) {
        Instancia instancia = CargadorInstancias.cargar(new File("/Users/Omar/Developer/VisualizadorApp/instancias/1.txt"));
        LineChart<String, Number> grafica2 = new RealInstanciaGrafica(instancia);
        vbox.getChildren().add(grafica2);
        vbox.getChildren().clear();
      }
    };

    menuArchivo.getItems().add(opcionAbrir);
    barraMenu.getMenus().add(menuArchivo);
    return barraMenu; 
  }

  public static void main(String[] args) {
    Application.launch(VisualizadorApp.class, args);
  }
}

