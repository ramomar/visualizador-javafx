package com.posgrado.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuPrincipal extends MenuBar {

  private final MenuArchivo menuArchivo = new MenuArchivo();

  public MenuPrincipal(EventHandler<ActionEvent> handlerAbrirCarpeta) {
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
