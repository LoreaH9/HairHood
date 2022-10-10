package com.example.hairhood.model

import com.google.type.Date


class ClienteModel {
    private var contraseña : String?=null
    private var direccion : String?=null
    private var dni : String?=null
    private var email : String?=null
    private var fechaNacimiento : Date?=null
    private var foto : String?=null
    private var info : String?=null
    private var nombre : String?=null
    private var numTelefono : Int?=null
    private var usuario : String?=null

    constructor(
        contraseña: String?,
        direccion: String?,
        dni: String?,
        email: String?,
        fechaNacimiento: Date?,
        foto: String?,
        info: String?,
        nombre: String?,
        numTelefono: Int?,
        usuario: String?
    ) {
        this.contraseña = contraseña
        this.direccion = direccion
        this.dni = dni
        this.email = email
        this.fechaNacimiento = fechaNacimiento
        this.foto = foto
        this.info = info
        this.nombre = nombre
        this.numTelefono = numTelefono
        this.usuario = usuario
    }


}
