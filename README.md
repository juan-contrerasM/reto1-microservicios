# Reto 1 — Servicio de Empleados

API REST en Spring Boot para registrar y consultar empleados. Usa **PostgreSQL** y se despliega con **Docker**.

## Requisitos

- Java 21
- Maven (incluido wrapper `mvnw`)
- Docker y Docker Compose
- PostgreSQL (solo si ejecutas la app fuera de Docker Compose)

## Modelo de empleado

```json
{
  "id": "E001",
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@empresa.com",
  "numeroEmpleado": "EMP-2026-001",
  "cargo": "Desarrollador Senior",
  "area": "Tecnología",
  "departamentoId": "IT",
  "fechaIngreso": "2026-02-10",
  "estado": "ACTIVO"
}
```

Estados previstos: `ACTIVO`, `EN_VACACIONES`, `RETIRADO`. En este reto solo se maneja `ACTIVO`.

## Endpoints

| Método | Ruta | Descripción | Código |
|--------|------|-------------|--------|
| `POST` | `/empleados` | Registrar empleado | `200` |
| `GET` | `/empleados/{id}` | Consultar por id | `200` / `404` |
| Otros | cualquier ruta o método no definido | Recurso no encontrado | `404` |

Validaciones al registrar (`409 Conflict`):

- Email ya registrado
- `numeroEmpleado` ya registrado

Los errores se responden en JSON, por ejemplo:

```json
{ "mensaje": "Ya existe un empleado registrado con ese email" }
```

Sin incluir el valor del email en el mensaje.

## Ejecutar con Docker Compose (recomendado)

Levanta la API y PostgreSQL juntos:

```bash
docker compose up --build
```

La API queda en: `http://localhost:8080`

Detener:

```bash
docker compose down
```

### Comandos del reto (imagen de la app)

```bash
docker build -t servidor-empleados .
docker run -p 8080:8080 -e DB_HOST=host.docker.internal -e DB_PORT=5433 -e DB_NAME=hr_management -e DB_USER=empleados -e DB_PASSWORD=empleados servidor-empleados
```

> Nota: con `docker run` necesitas PostgreSQL corriendo en tu máquina (por ejemplo con `docker compose up db`). Con `docker compose up --build` no hace falta nada extra.

## Ejecutar en local

1. Arranca solo la base de datos:

```bash
docker compose up db -d
```

2. Ejecuta la aplicación:

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
.\mvnw.cmd spring-boot:run
```

En contenedor:

```bash
docker compose up --build
```

Configuración por defecto:

| Variable | Valor |
|----------|-------|
| Host | `localhost` |
| Puerto | `5433` (contenedor Docker; evita choque con Postgres local) |
| Base de datos | `hr_management` |
| Usuario | `empleados` |
| Contraseña | `empleados` |

## Pruebas con curl / Postman / Bruno

### Registrar empleado

```bash
curl -X POST http://localhost:8080/empleados ^
  -H "Content-Type: application/json" ^
  -d "{\"id\":\"E001\",\"nombre\":\"Juan\",\"apellido\":\"Pérez\",\"email\":\"juan.perez@empresa.com\",\"numeroEmpleado\":\"EMP-2026-001\",\"cargo\":\"Desarrollador Senior\",\"area\":\"Tecnología\",\"departamentoId\":\"IT\",\"fechaIngreso\":\"2026-02-10\",\"estado\":\"ACTIVO\"}"
```

### Consultar empleado

```bash
curl http://localhost:8080/empleados/E001
```

### Empleado inexistente

```bash
curl http://localhost:8080/empleados/E999
```

Respuesta esperada: `404`

```json
{ "mensaje": "El empleado con id E999 no existe" }
```

## Estructura del proyecto

```
src/main/java/com/microservicios/Reto1/
├── controller/     # Endpoints HTTP
├── service/        # Reglas de negocio
├── repository/     # Acceso a PostgreSQL (JPA)
├── model/          # Entidad Empleado y estados
└── exception/      # Manejo de 400 / 404
```

## Tecnologías

- Java 21
- Spring Boot 4
- Spring Web / Spring Data JPA / Validation
- PostgreSQL 16
- Docker / Docker Compose
