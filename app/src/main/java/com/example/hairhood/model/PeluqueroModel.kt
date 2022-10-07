package com.example.hairhood.model

class PeluqueroModel{
    private var contraseña : String?=null
    private var dni : String?=null
    private var email : String?=null
    private var especialidad : String?=null
    private var foto : String?=null
    private var nombre : String?=null
    private var numTelefono : Int?=null
    private var titulo : String?=null
    private var usuario : String?=null

    constructor(
        contraseña: String?,
        dni: String?,
        email: String?,
        especialidad: String?,
        foto: String?,
        nombre: String?,
        numTelefono: Int?,
        titulo: String?,
        usuario: String?
    ) {
        this.contraseña = contraseña
        this.dni = dni
        this.email = email
        this.especialidad = especialidad
        this.foto = foto
        this.nombre = nombre
        this.numTelefono = numTelefono
        this.titulo = titulo
        this.usuario = usuario
    }





}