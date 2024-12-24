
# Arquitectura del Proyecto: API de Gestión de Usuarios

## Diagrama de Arquitectura
El sistema se organiza en capas modulares para garantizar escalabilidad y claridad:

```plantuml
@startuml

package "Clientes" {
  [Navegador Web] --> [API REST]
  [Postman] --> [API REST]
  [Swagger UI] --> [API REST]
}

package "Backend" {
  [API REST] --> [Capa de Servicios]
  [Capa de Servicios] --> [Capa de Seguridad]
  [Capa de Seguridad] --> [Capa de Persistencia]
}

package "Persistencia" {
  [Capa de Persistencia] --> [H2 Database]
}

@enduml
```

## Diagrama de Solución de Arquitectura

```plantuml
@startuml

actor Cliente
Cliente --> "Solicitud POST: /register"

"Solicitud POST: /register" --> API : Validar estructura JSON
API --> Servicios : Verificar unicidad del correo
Servicios --> Seguridad : Generar token JWT
Seguridad --> Persistencia : Guardar datos del usuario
Persistencia --> "H2 Database" : Persistir información

"H2 Database" --> API : Retornar usuario con token
API --> Cliente : Respuesta con datos del usuario

@enduml
```

## Requisitos Técnicos

- **Java 17**: Lenguaje principal.
- **Spring Boot**: Framework para construir la API REST.
- **H2 Database**: Base de datos en memoria para persistencia.
- **JWT**: Para autenticación basada en tokens.
- **Swagger**: Para documentación y pruebas de API.
- **Gradle**: Herramienta de construcción del proyecto.

---

Este diseño asegura modularidad, seguridad y claridad, con un flujo bien definido entre cada capa del sistema.
