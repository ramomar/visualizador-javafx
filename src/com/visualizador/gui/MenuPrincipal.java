package com.visualizador.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuPrincipal extends MenuBar {

  private final MenuArchivo menuArchivo = new MenuArchivo();

  public MenuPrincipal(EventHandler<ActionEvent> handlerAbrirCarpeta,
                       EventHandler<ActionEvent> handlerExportarImagen) {
    super();
    this.getMenus().add(menuArchivo);
    menuArchivo.abrirCarpetaItem.setOnAction(handlerAbrirCarpeta);
    menuArchivo.exportarImagenItem.setOnAction(handlerExportarImagen);
  }
}

class MenuArchivo extends Menu {
  public final MenuItem abrirCarpetaItem;
  public final MenuItem exportarImagenItem;

  MenuArchivo() {
    super("Archivo");
    abrirCarpetaItem =   new MenuItem("Abrir carpeta");
    exportarImagenItem = new MenuItem("Exportar como imágen");
    this.getItems().addAll(abrirCarpetaItem, exportarImagenItem);
  }
}
