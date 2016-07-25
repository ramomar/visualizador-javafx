package com.posgrado.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class VisualizadorApp extends Application {

  @Override
  public void start(Stage stage) {
    (new Visualizador(stage, 800, 600)).mostrar();
  }

  /*private static DirectoryChooser hacerSelectorDirectorio(File carpetaInicial) {
    DirectoryChooser selectorDirectorio = new DirectoryChooser();
    selectorDirectorio.setTitle("Seleccionar instancias");
    selectorDirectorio.setInitialDirectory(carpetaInicial);
    return selectorDirectorio;
  }*/

  public static void main(String[] args) {
    Application.launch(VisualizadorApp.class, args);
  }
}

