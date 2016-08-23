package com.visualizador.comun;

import java.util.*;

// TODO : hacer la lista de llegadas inmutable
public class Instancia {
  private Map<Integer, List<Llegada>> mapeoHoraLlegadas;

  public Instancia(List<Llegada> llegadas) {
    inicializarMapeos(llegadas);
  }

  // Regresa un conjunto con las horas en las que al menos un cliente llego al banco
  public Set<Integer> getHorasConLlegadasDeClientes() {
    return mapeoHoraLlegadas.keySet();
  }

  // 0 < hora < 23
  public List<Llegada> getLlegadasEnHora(int hora) {
    return mapeoHoraLlegadas.get(hora);
  }

  public List<Llegada> getLlegadas() {
    Collection<List<Llegada>> listaDeLlegadas = this.mapeoHoraLlegadas.values();
    List<Llegada> llegadas                    = new ArrayList<>();

    for (List<Llegada> l : listaDeLlegadas) llegadas.addAll(l);

    return llegadas;
  }

  public int getTamano() {
   Collection<List<Llegada>> listasDeLlegadas = this.mapeoHoraLlegadas.values();
   int tamano = 0;

   for (List<Llegada> l : listasDeLlegadas) tamano += l.size();

   return tamano;
  }

  private void inicializarMapeos(List<Llegada> llegadas) {
    Map<Integer, List<Llegada>> mapeos = new HashMap<>();

    for(int i=0; i<24; i++) mapeos.put(i, new ArrayList<>());

    for (Llegada l : llegadas) mapeos.get(l.hora.getHour()).add(l);

    this.mapeoHoraLlegadas = mapeos;
  }
}

