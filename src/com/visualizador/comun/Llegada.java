package com.visualizador.comun;

import java.time.LocalTime;

public class Llegada {
  public final int           numeroCliente;
  public final LocalTime     hora;
  public final Transacciones transacciones;
  public final int           cantidadTransacciones;

  public Llegada(int numeroCliente,
                 int segundosDesdeInicioDelDia,
                 Transacciones transacciones) {

    this.numeroCliente         = numeroCliente;
    this.transacciones         = transacciones;
    this.cantidadTransacciones = transacciones.cantidadTransacciones;
    this.hora                  = LocalTime.ofSecondOfDay(segundosDesdeInicioDelDia);
  }

  public String toString() {
    return "Número del cliente: " + numeroCliente         + "\n" +
           "Hora de llegada: "    + hora.toString()       + "\n" +
           "# transacciones: "    + cantidadTransacciones + "\n" +
           "Resumen:  "           + transacciones.toString();
  } 

  public static void main(String[] args) {
    Transacciones txns = new Transacciones(1, 0, 2, 0, 1, 0);
    Llegada l = new Llegada(1, 3600, txns);
    System.out.println(l.toString());
  }
}

