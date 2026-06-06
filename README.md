# API Biblioteca

API REST construida con **Spring Boot 4** y **JPA/Hibernate** para gestionar una biblioteca: libros, autores y categorías. Desarrollada como actividad de evaluación final de la UT6.

## Tecnologías

- Java 17
- Spring Boot 4.0.6 (Web MVC, Data JPA, Validation, Security)
- Hibernate / JPA
- Base de datos H2 en memoria
- Lombok
- Maven (con wrapper `mvnw`)

## Requisitos previos

- JDK 17 o superior instalado (`java -version`)
- No hace falta instalar Maven: el proyecto incluye el wrapper (`mvnw` / `mvnw.cmd`)

## Cómo arrancar el proyecto

Desde la carpeta raíz del proyecto:

**Windows**
```bash
mvnw.cmd spring-boot:run
```

**Linux / macOS**
```bash
./mvnw spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`.

La base de datos H2 es **en memoria**: se crea al arrancar y se borra al detener la aplicación.

## Consola de la base de datos (H2)

Para ver las tablas generadas y los datos persistidos:

1. Abre `http://localhost:8080/h2-console`
2. Datos de conexión:
   - **JDBC URL:** `jdbc:h2:mem:bibliotecadb`
   - **User:** `sa`
   - **Password:** *(vacío)*

## Seguridad

La autenticación es **HTTP Basic** (Spring Security):

- Los endpoints de **lectura** (`GET`) son **públicos**.
- Los endpoints de **escritura** (`POST`, `PUT`, `DELETE`) requieren **autenticación**.

Usuario en memoria:

| Usuario | Contraseña |
|---------|------------|
| `admin` | `admin123` |

En Postman / Thunder Client: pestaña **Authorization → Basic Auth** con esas credenciales.

> La contraseña se almacena cifrada con **BCrypt** (no en texto plano).

## Modelo de datos

- **Libro** — `id`, `titulo`, `isbn`
- **Autor** — `id`, `nombre`, `nacionalidad`
- **Categoria** — `id`, `nombre`

Relaciones:

- `Libro` **N:1** `Autor` (`@ManyToOne`)
- `Libro` **N:M** `Categoria` (`@ManyToMany`, tabla intermedia `libro_categoria`)

## Endpoints

### Libros — `/api/v1/libros`

| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/libros` | Lista todos los libros | No |
| GET | `/api/v1/libros/{id}` | Obtiene un libro por id (200 / 404) | No |
| POST | `/api/v1/libros` | Crea un libro (201) | Sí |
| PUT | `/api/v1/libros/{id}` | Actualiza un libro (200 / 404) | Sí |
| DELETE | `/api/v1/libros/{id}` | Elimina un libro (204 / 404) | Sí |
| GET | `/api/v1/libros/autor/{id}` | Lista los libros de un autor (usa la relación) | No |
| GET | `/api/v1/libros/buscar?titulo=` | Busca libros por título (param opcional) | No |
| GET | `/api/v1/libros/count/categoria/{id}` | Cuenta libros de una categoría (JPQL) | No |

### Autores — `/api/v1/autores`

| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/autores` | Lista todos los autores | No |
| GET | `/api/v1/autores/{id}` | Obtiene un autor por id (200 / 404) | No |
| POST | `/api/v1/autores` | Crea un autor (201) | Sí |
| PUT | `/api/v1/autores/{id}` | Actualiza un autor (200 / 404) | Sí |
| DELETE | `/api/v1/autores/{id}` | Elimina un autor (204 / 404) | Sí |
| GET | `/api/v1/autores/buscar?nacionalidad=` | Busca autores por nacionalidad (param opcional) | No |

### Categorías — `/api/v1/categorias`

| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/categorias` | Lista todas las categorías | No |
| GET | `/api/v1/categorias/{id}` | Obtiene una categoría por id (200 / 404) | No |
| POST | `/api/v1/categorias` | Crea una categoría (201) | Sí |
| PUT | `/api/v1/categorias/{id}` | Actualiza una categoría (200 / 404) | Sí |
| DELETE | `/api/v1/categorias/{id}` | Elimina una categoría (204 / 404) | Sí |

### Ejemplo: crear un libro (requiere auth)

```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/v1/libros \
  -H "Content-Type: application/json" \
  -d '{"titulo":"El Quijote","isbn":"978-84","autor":{"id":1}}'
```

### Ejemplo: buscar por título (público)

```bash
curl "http://localhost:8080/api/v1/libros/buscar?titulo=quijote"
```

## Arquitectura

Estructura por capas: **Controller → Service → Repository**. El controlador nunca accede directamente al repositorio.

```
controller/   -> endpoints REST
Service/      -> lógica de negocio
Repository/   -> acceso a datos (JpaRepository)
model/        -> entidades JPA
config/       -> configuración de seguridad
exception/    -> manejo global de errores (@ControllerAdvice)
```
