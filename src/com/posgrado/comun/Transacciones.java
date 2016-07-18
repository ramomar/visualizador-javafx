package com.posgrado.comun;

public class Transacciones {
  public final int depositos;
  public final int retiros;
  public final int pagos;
  public final int transferencias;
  public final int entregas;
  public final int cambios;
  public final int cantidadTransacciones;

  public Transacciones(int depositos,
                       int retiros,
                       int pagos,
                       int transferencias,
                       int entregas,
                       int cambios) {

    this.depositos             = depositos;
    this.retiros               = retiros;  
    this.pagos                 = pagos;
    this.transferencias        = transferencias;
    this.entregas              = entregas;
    this.cambios               = cambios;
    this.cantidadTransacciones = depositos + retiros + pagos +
                                 transferencias + entregas + cambios;
  }

  @Override
  public String toString() {
    return this.depositos             + "D " +
           this.retiros               + "R " +
           this.pagos                 + "P " +
           this.transferencias        + "T " +
           this.entregas              + "E " +
           this.cambios               + "C " +
           this.cantidadTransacciones + "TOT";
  }

}

