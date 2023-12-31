# language: es
Característica: Tareas

  Antecedentes: Disponemos de una API de tareas
    Dado una API ubicada en "/hexamod/v1/task"
    Y una documentación disponible en "/v3/api-docs"

# LIST

  @users @listado @unauthorized @error
  Escenario: Obtener un listado inicial de tareas sin autenticar
    Dado un usuario no autenticado
    Cuando trata de obtener su listado de tareas
    Entonces obtiene un error de autenticación

  @users @listado @success
  Escenario: Obtener un listado inicial de tareas autenticado
    Dado el usuario autenticado "user@acme.es"
    Cuando trata de obtener su listado de tareas
    Entonces obtiene una respuesta correcta
    Y contiene una lista paginada de 2 página con 5 tareas por página y un total de 10 elementos

  @users @listado @success @pdf
  Escenario: Obtener un listado inicial de tareas autenticado en pdf
    Dado el usuario autenticado "user@acme.es"
    Cuando trata de obtener su listado de tareas en pdf
    Entonces obtiene una respuesta correcta
    Y contiene un documento pdf de 1676384 bytes

  @users @listado @success
  Escenario: Obtener un listado de tareas breves autenticado
    Dado el usuario autenticado "user@acme.es"
    Cuando trata de obtener su listado de tareas breves
    Entonces obtiene una respuesta correcta
    Y contiene una lista paginada de 2 página con 5 tareas por página y un total de 7 elementos

  @users @listado @success
  Escenario: Obtener un listado de tareas filtrada por duración
    Dado el usuario autenticado "user@acme.es"
    Cuando trata de obtener su listado de tareas de menos de 18 minutos y que empiecen por "MITAREA 01"
    Entonces obtiene una respuesta correcta
    Y contiene una lista paginada de 1 página con 5 tareas por página y un total de 4 elementos
