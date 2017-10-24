package com.a000webhostapp.mymuseum.Modelo;

import java.io.Serializable;

public interface Guardable extends Serializable {
    String configGuardar();
    String configModificar();
}
