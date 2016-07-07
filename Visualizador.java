import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.File;

public class Visualizador extends Application {
  @Override
  public void start(Stage stage) {
    Instancia instancia      = CargadorInstancias.cargar(new File("1.txt"));
    GeneradasGrafica grafica = new GeneradasGrafica(instancia);

    Scene scene = new Scene(grafica, 800, 600);

    stage.setTitle("Visualizador");
    stage.setScene(scene);
    stage.show();
  }
}

