import java.util.List;

public class Instancia {
  public final List<Llegada> llegadas;
  public final int           tamano;

  public Instancia(List<Llegada> llegadas) {
    this.llegadas = llegadas;
    this.tamano   = llegadas.size();
  }
}

