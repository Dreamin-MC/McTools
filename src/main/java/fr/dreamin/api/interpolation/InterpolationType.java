package fr.dreamin.api.interpolation;

public enum InterpolationType {
    LINEAR,    // Interpolation linéaire classique
    EASE_OUT,  // Rapide au début, normal à la fin
    EASE_IN_OUT, // Normal au début, rapide au milieu, normal à la fin
    EASE_IN   // Normal au début, rapide à la fin
  }