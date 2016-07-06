import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

public abstract class CargadorInstancias {
  public static Instancia cargar(File archivo) {
    List<Llegada> llegadas = parsearArchivo(archivo);
    return new Instancia(llegadas);
  }

  private static List<Llegada> parsearArchivo(File archivo) {
    List<Llegada> llegadas = new ArrayList<Llegada>();

    try {
      BufferedReader lector  = new BufferedReader(new FileReader(archivo));
      String linea;

      while((linea = lector.readLine()) != null) {
        llegadas.add(parsearLinea(linea));
      }

    } catch (FileNotFoundException e) {
      System.out.println("El archivo no existe");
      System.exit(1);
    } catch (IOException e) {
      System.out.println("Ocurrio un problema al intentar leer el archivo");
      System.exit(1);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    return llegadas;
  }

  private static Llegada parsearLinea(String linea) {
    String[] componentes = linea.split(",");

    int cliente        = Integer.parseInt(componentes[0]);
    int segundos       = Integer.parseInt(componentes[1]);
    int depositos      = Integer.parseInt(componentes[2]);
    int retiros        = Integer.parseInt(componentes[3]);
    int pagos          = Integer.parseInt(componentes[4]); 
    int transferencias = Integer.parseInt(componentes[5]);
    int entregas       = Integer.parseInt(componentes[6]);
    int cambios        = Integer.parseInt(componentes[7]);

    Transacciones transacciones = new Transacciones(depositos,
                                                    retiros,
                                                    pagos,
                                                    transferencias,
                                                    entregas,
                                                    cambios);
                          
    return new Llegada(cliente, segundos, transacciones);
  }

  public static void main(String[] args) {
    String filename     = "1.txt";
    File archivo        = new File(filename);
    Instancia instancia = CargadorInstancias.cargar(archivo);
    
    System.out.println(instancia.tamano);

    for (Llegada l : instancia.llegadas) {
      System.out.println(l);
    }
  }
}

