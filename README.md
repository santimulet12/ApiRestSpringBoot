# Tasks API - Spring Boot + Kotlin + MySQL

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple?logo=kotlin)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![License](https://img.shields.io/badge/license-MIT-blue)

API REST para gestión de tareas (Tasks) desarrollada con Spring Boot, Kotlin y MySQL.

## 📋 Descripción

Esta es una API REST completa que permite realizar operaciones CRUD (Create, Read, Update, Delete) sobre tareas. Desarrollada como proyecto educativo para aprender Spring Boot con Kotlin.

## Características

- ✅ CRUD completo de tareas
- ✅ Filtrado de tareas por estado (completadas/pendientes)
- ✅ Persistencia en MySQL
- ✅ Arquitectura en capas (Controller-Service-Repository)
- ✅ Respuestas HTTP apropiadas
- ✅ Manejo de errores

## 🛠 Tecnologías

- **Kotlin** 1.9.x - Lenguaje de programación
- **Spring Boot** 3.2.x - Framework backend
- **Spring Data JPA** - Capa de persistencia
- **MySQL** 8.0+ - Base de datos
- **Gradle** - Gestor de dependencias

## 📦 Requisitos Previos

- JDK 17 o superior
- MySQL Server 8.0+
- Gradle 8.x (incluido en el proyecto con Wrapper)
- IntelliJ IDEA Community Edition (recomendado)

## 🚀 Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/tasks-api.git
cd tasks-api
```

### 2. Configurar la base de datos

Conecta a MySQL y ejecuta:

```sql
CREATE DATABASE tasks_db;

USE tasks_db;

CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. Configurar las credenciales

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tasks_db
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA_AQUI
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

server.port=8080
```

### 4. Ejecutar la aplicación

```bash
# Usando Gradle Wrapper
./gradlew bootRun

# O desde IntelliJ IDEA
# Click derecho en TasksApiApplication.kt → Run
```

La API estará disponible en: `http://localhost:8080`

## 📁 Estructura del Proyecto

```
tasks-api/
├── src/
│   ├── main/
│   │   ├── kotlin/com/example/tasksapi/
│   │   │   ├── TasksApiApplication.kt
│   │   │   ├── controller/
│   │   │   │   └── TaskController.kt
│   │   │   ├── service/
│   │   │   │   └── TaskService.kt
│   │   │   ├── repository/
│   │   │   │   └── TaskRepository.kt
│   │   │   └── model/
│   │   │       └── Task.kt
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── build.gradle.kts
└── README.md
```

## 🔌 Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/tasks` | Obtener todas las tareas |
| GET | `/api/tasks/{id}` | Obtener tarea por ID |
| POST | `/api/tasks` | Crear nueva tarea |
| PUT | `/api/tasks/{id}` | Actualizar tarea |
| DELETE | `/api/tasks/{id}` | Eliminar tarea |
| GET | `/api/tasks/completed/{true\|false}` | Filtrar por estado |

**Consulta la [documentación completa](DOCUMENTACION.md) para ver ejemplos detallados de uso.**

## 💡 Ejemplo de Uso Rápido

```bash
# Crear una tarea
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Mi primera tarea","description":"Descripción","completed":false}'

# Obtener todas las tareas
curl http://localhost:8080/api/tasks

# Actualizar una tarea
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Tarea actualizada","description":"Nueva descripción","completed":true}'

# Eliminar una tarea
curl -X DELETE http://localhost:8080/api/tasks/1
```

## 🧪 Testing

```bash
# Ejecutar todos los tests
./gradlew test

# Ver reporte de tests
./gradlew test --info
```

## 🔧 Solución de Problemas

### Error de conexión a MySQL
```bash
sudo systemctl status mysql
sudo systemctl start mysql
```

### Puerto 8080 ocupado
Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

### Problemas con credenciales
Verifica tu contraseña de MySQL:
```bash
mysql -u root -p
```


---

⭐ Si este proyecto te fue útil, ¡dale una estrella en GitHub!
