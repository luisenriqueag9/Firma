package com.grupo2.firma;

public class Signature {
    private String descripcion;
    private byte[] firmaDigital;

    public Signature(String descripcion, byte[] firmaDigital) {
        this.descripcion = descripcion;
        this.firmaDigital = firmaDigital;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public byte[] getFirmaDigital() {
        return firmaDigital;
    }
}

