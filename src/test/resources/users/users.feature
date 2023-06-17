# language: es
Característica: Usuarios

  Antecedentes: Disponemos de una API de usuarios documentada
    Dado una API ubicada en "/hexamod/v1/user"
    Y una documentación disponible en "/v3/api-docs"

# LIST

  @users @listado @unauthorized @error
  Escenario: Obtener un listado inicial de usuarios sin autenticar
    Dado un usuario no autenticado
    Cuando trata de obtener un listado de usuarios
    Entonces obtiene un error de autenticación

  @users @listado @unauthorized @error
  Escenario: Obtener un listado inicial de usuarios autenticado
    Dado el usuario autenticado "user@acme.es"
    Cuando trata de obtener un listado de usuarios
    Entonces obtiene un error de autorización

  @users @listado @success
  Escenario: Obtener un listado inicial de usuarios autenticado
    Dado el usuario autenticado "admin@acme.es"
    Cuando trata de obtener un listado de usuarios
    Entonces obtiene una respuesta correcta
    Y contiene una lista paginada de 10 páginas con 5 usuarios por página y un total de 50 elementos
    Y el primer usuario de la lista se llama "USUARIO 000"
