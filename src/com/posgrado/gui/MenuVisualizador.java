package com.posgrado.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

// TODO: Quizas puede haber una mejor manera de hacer esto
public class MenuVisualizador extends MenuBar {

  private final MenuArchivo menuArchivo = new MenuArchivo();

  public MenuVisualizador(EventHandler<ActionEvent> handlerAbrirCarpeta) {
    super();
    this.getMenus().add(menuArchivo);
    menuArchivo.abrirCarpetaItem.setOnAction(handlerAbrirCarpeta);
  }
}

class MenuArchivo extends Menu {
  public final MenuItem abrirCarpetaItem;

  MenuArchivo() {
    super("Archivo");
    abrirCarpetaItem = new MenuItem("Abrir carpeta");
    this.getItems().add(abrirCarpetaItem);
  }
}
