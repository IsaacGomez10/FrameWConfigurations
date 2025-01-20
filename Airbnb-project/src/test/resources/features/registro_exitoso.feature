# language:es
  @RegistroExistoso
  Característica: realizar registro de usuario
    Escenario: registro exitoso
      Dado el usuario se encuentra en la página Airbnb
      Entonces carga el archivo excel datos_registro.xlsx de la fila 2
      Cuando complete las caracteristicas de la reserva
      Entonces obtendra un mensaje de registro exitoso
