import java.time.LocalDateTime;
import java.time.Duration;

public class InstanciaResuelta {
  public final int           numeroCliente;
  public final Duration      tiempoAtencion;
  public final Duration      tiempoEspera;
  public final Duration      tiempoTotal;
  public final LocalDateTime llegada;
  public final LocalDateTime salida; 

  public InstanciaResuelta(int _numeroCliente,
                 long _tiempoAtencion,
                 long _tiempoEspera,
                 long _tiempoTotal,
                 long _llegada,
                 long _salida) {

    numeroCliente  = _numeroCliente;
    tiempoAtencion = _tiempoAtencion;
    tiempoEspera   = _tiempoEspera;
    tiempoTotal    = _tiempoTotal;
    llegada        = _llegada;
    salida         = _salida;
  }
}
