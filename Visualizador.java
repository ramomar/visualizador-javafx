import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.ArrayList;


public class Visualizador extends Application {

  private List<Instancia> instanciasCargadas = new ArrayList<Instancia>();

  @Override
  public void start(Stage stage) {
    Instancia instancia               = CargadorInstancias.cargar(new File("1.txt"));
    LineChart<String, Number> grafica = new RealInstanciaGrafica(instancia);

    Group grupoPrincipal  = new Group();
    VBox vbox = new VBox();
    Holder<File> holder = new Holder<File>(null);
    MenuBar menuPrincipal  = hacerMenu(stage, holder);
    ChoiceBox cajaOpciones = hacerOpciones();

    vbox.getChildren().add(menuPrincipal);
    vbox.getChildren().add(cajaOpciones);
    vbox.getChildren().add(grafica);


    Scene scene = new Scene(vbox, 800, 600);
    stage.setTitle("Visualizador");
    stage.setScene(scene);
    stage.show();
  }

  private static void cargarInstancia(File carpetaInicial) {

  }

  private static ChoiceBox hacerOpciones() {
    ChoiceBox cajaOpciones = new ChoiceBox();
    List<String> listadoOpciones = new ArrayList<String>();
    cajaOpciones.getItems().addAll(listadoOpciones);
    return cajaOpciones;
  }
  
  private static MenuBar hacerMenu(Stage stage) {
    MenuBar barraMenu    = new MenuBar();
    Menu menuArchivo     = new Menu("Archivo");
    MenuItem opcionAbrir = new AbrirMenuItem(stage);

    menuArchivo.getItems().add(opcionAbrir);
    barraMenu.getMenus().add(menuArchivo);
    return barraMenu; 
  }

  private static DirectoryChooser hacerSelectorDirectorio(File carpetaInicial) {
    DirectoryChooser selectorDirectorio = new DirectoryChooser();
    selectorDirectorio.setTitle("Seleccionar instancias");
    selectorDirectorio.setInitialDirectory(carpetaInicial);
    return selectorDirectorio;
  }

  private static class AbrirMenuItem extends MenuItem {
    public AbrirMenuItem(Stage stage, Holder<File> holder) {
      super("Abrir");
      configurarEvento(this, stage, holder);
    }

    private static void configurarEvento(MenuItem item, Stage stage, Holder<File> holder) {
      item.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
          DirectoryChooser selectorDirectorioInstancias = hacerSelectorDirectorio(new File("."));
          File directorioSeleccionado = selectorDirectorioInstancias.showDialog(stage);
          holder.set(directorioSeleccionado);
          System.out.println(holder.get());
        }
      });
    }
  }

  private static class Holder<T> {
    private T valorGuardado;
    Holder(T valor) {
      valorGuardado = valor;
    }

    public T get() {
      return valorGuardado;
    }

    public T set(T valor) {
      valorGuardado = valor;
      return valorGuardado;
    }
  }

}

