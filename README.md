# Spring Web - Gestión de Menús de Restaurante

Proyecto basado en el patrón MVC (Modelo-Vista-Controlador) de Spring. La idea es separar la lógica de negocio de la presentación: los modelos representan los datos (entrantes, principales, postres, menús), las vistas renderizan el HTML con Thymeleaf, y los controladores gestionan las peticiones del usuario.

Además de las vistas web, el proyecto incluye una API REST para poder consumir los datos desde otras aplicaciones.

## Tecnologías

- Java 21
- Spring Boot 3.5.9
- MySQL 8.0
- Thymeleaf (vistas web)
- JPA/Hibernate
- Docker

## Requisitos

- Java 21 (o Docker)
- Maven (incluido con el wrapper)
- MySQL 8.0 (o Docker)

## Ejecución

### Con Docker (recomendado)

Desde la carpeta `springWeb`:

```bash
docker-compose up -d --build
```

El flag `--build` construye la imagen antes de levantar los contenedores. Después de hacer cambios en el código, hay que volver a ejecutar este comando para que se apliquen.

Accede a `http://localhost:8080`.

### Sin Docker

1. Tener MySQL corriendo en el puerto 3307 con una base de datos llamada `restaurante_dam`
2. Ejecutar:

```bash
cd springWeb
./mvnw spring-boot:run
```

## Estructura

El proyecto tiene dos tipos de controladores:

- **Web** (`/controllers/Web/`): Vistas HTML con Thymeleaf
- **API REST** (`/controllers/RestAPI/`): Endpoints JSON

### Entidades

- `Entrante`: Primer plato
- `Principal`: Segundo plato
- `Postre`: Postre
- `Menu`: Combinación de entrante + principal + postre con precio calculado

## Documentación API

Swagger disponible en: `http://localhost:8080/swagger-ui.html`

Especificación OpenAPI en: `http://localhost:8080/api-docs`

## Configuración

La configuración de conexión a BD está en `src/main/resources/application.properties`. Por defecto:

- Host: localhost
- Puerto: 3307
- Base de datos: restaurante_dam
- Usuario: root
- Contraseña: 1234
