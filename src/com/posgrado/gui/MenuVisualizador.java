package com.posgrado.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

// TODO: Quizas puede haber una mejor manera de hacer esto
public class MenuVisualizador extends MenuBar {

  public final MenuArchivo menuArchivo = new MenuArchivo();

  public MenuVisualizador() {
    super();
    this.getMenus().add(menuArchivo);
  }
}

class MenuArchivo extends Menu {
  private final MenuItem menuAbrir;

  MenuArchivo() {
    super("Archivo");
    menuAbrir = new MenuItem("Abrir carpeta");
    this.getItems().add(menuAbrir);
  }

  public void setAccionClickAbrirMenuitem(EventHandler<ActionEvent> handler) {
    menuAbrir.setOnAction(handler);
  }
}
