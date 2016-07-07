import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.File;

public class Visualizador extends Application {
  @Override
  public void start(Stage stage) {
    Instancia instancia      = CargadorInstancias.cargar(new File("1.txt"));
    InstanciaGrafica grafica = new InstanciaGrafica(instancia);

    Scene scene = new Scene(grafica, 1366, 768);

    stage.setTitle("Visualizador");
    stage.setScene(scene);
    stage.show();
  }
}

