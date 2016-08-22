package com.posgrado;

import com.posgrado.gui.Visualizador;
import javafx.application.Application;
import javafx.stage.Stage;

public class VisualizadorApp extends Application {

  @Override
  public void start(Stage stage) {
    (new Visualizador(stage, 800, 600)).mostrar();
  }

  public static void main(String[] args) {
    Application.launch(VisualizadorApp.class, args);
  }
}
