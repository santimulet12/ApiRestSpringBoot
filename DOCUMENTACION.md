# Documentación Completa - Tasks API

## Tabla de Contenidos

1. [Introducción](#introducción)
2. [Arquitectura del Proyecto](#arquitectura-del-proyecto)
3. [Modelo de Datos](#modelo-de-datos)
4. [Endpoints de la API](#endpoints-de-la-api)
5. [Ejemplos Detallados de Uso](#ejemplos-detallados-de-uso)
6. [Códigos de Respuesta HTTP](#códigos-de-respuesta-http)
7. [Manejo de Errores](#manejo-de-errores)
8. [Buenas Prácticas](#buenas-prácticas)

---

## Introducción

Esta API REST permite gestionar tareas (tasks) con operaciones completas de CRUD (Create, Read, Update, Delete). Está desarrollada con Spring Boot 3.5.7, Kotlin 1.9.25 y MySQL 8.0, siguiendo las mejores prácticas de arquitectura en capas.

### URL Base

```
http://localhost:8080/api/tasks
```

### Formato de Datos

- **Content-Type**: `application/json`
- **Accept**: `application/json`

---

## Arquitectura del Proyecto

La aplicación sigue el patrón de arquitectura en capas (Layered Architecture):

### 1. Capa de Presentación (Controller)

**Archivo**: `TaskController.kt`

Responsabilidades:
- Recibir peticiones HTTP
- Validar datos de entrada
- Delegar la lógica de negocio al servicio
- Retornar respuestas HTTP apropiadas

```kotlin
@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService)
```

### 2. Capa de Lógica de Negocio (Service)

**Archivo**: `TaskService.kt`

Responsabilidades:
- Implementar la lógica de negocio
- Coordinar operaciones con el repositorio
- Validar reglas de negocio
- Transformar datos cuando sea necesario

```kotlin
@Service
class TaskService(private val taskRepository: TaskRepository)
```

### 3. Capa de Acceso a Datos (Repository)

**Archivo**: `TaskRepository.kt`

Responsabilidades:
- Interactuar con la base de datos
- Proporcionar métodos de consulta personalizados
- Abstracción de la persistencia

```kotlin
@Repository
interface TaskRepository: JpaRepository<Task, Long>
```

### 4. Capa de Modelo (Entity)

**Archivo**: `Task.kt`

Responsabilidades:
- Representar la estructura de datos
- Mapear con la tabla de base de datos
- Definir validaciones y restricciones

---

## Modelo de Datos

### Entidad Task

```kotlin
@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false)
    val title: String,
    
    @Column(columnDefinition = "TEXT")
    val description: String,
    
    @Column(nullable = false)
    val completed: Boolean,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
```

### Campos

| Campo | Tipo | Descripción | Requerido | Default |
|-------|------|-------------|-----------|---------|
| `id` | Long | Identificador único auto-generado | No | Auto-incremento |
| `title` | String | Título de la tarea (max 255 caracteres) | Sí | - |
| `description` | String | Descripción detallada de la tarea | Sí | - |
| `completed` | Boolean | Estado de la tarea (completada o pendiente) | Sí | false |
| `createdAt` | LocalDateTime | Fecha y hora de creación | No | Fecha actual |

### Ejemplo de Objeto JSON

```json
{
  "id": 1,
  "title": "Completar documentación de la API",
  "description": "Escribir la documentación detallada incluyendo todos los endpoints y ejemplos",
  "completed": false,
  "createdAt": "2025-11-18T10:30:00"
}
```

---

## Endpoints de la API

### 1. Obtener Todas las Tareas

Recupera una lista de todas las tareas almacenadas en la base de datos.

**Endpoint**: `GET /api/tasks`

**Parámetros**: Ninguno

**Respuesta Exitosa**:
- **Código**: `200 OK`
- **Tipo**: Array de objetos Task

**Ejemplo de Solicitud**:

```bash
curl -X GET http://localhost:8080/api/tasks
```

**Ejemplo de Respuesta**:

```json
[
  {
    "id": 1,
    "title": "Estudiar Spring Boot",
    "description": "Repasar conceptos de Spring Boot y Kotlin",
    "completed": false,
    "createdAt": "2025-11-18T09:00:00"
  },
  {
    "id": 2,
    "title": "Hacer ejercicio",
    "description": "Rutina de 30 minutos de cardio",
    "completed": true,
    "createdAt": "2025-11-18T07:30:00"
  }
]
```

---

### 2. Obtener Tarea por ID

Recupera una tarea específica mediante su identificador único.

**Endpoint**: `GET /api/tasks/{id}`

**Parámetros de Ruta**:
- `id` (Long) - Identificador de la tarea

**Respuesta Exitosa**:
- **Código**: `200 OK`
- **Tipo**: Objeto Task

**Respuesta de Error**:
- **Código**: `404 NOT FOUND` - Si la tarea no existe

**Ejemplo de Solicitud**:

```bash
curl -X GET http://localhost:8080/api/tasks/1
```

**Ejemplo de Respuesta Exitosa**:

```json
{
  "id": 1,
  "title": "Estudiar Spring Boot",
  "description": "Repasar conceptos de Spring Boot y Kotlin",
  "completed": false,
  "createdAt": "2025-11-18T09:00:00"
}
```

**Ejemplo de Respuesta de Error (404)**:

```
(Cuerpo vacío)
```

---

### 3. Crear Nueva Tarea

Crea una nueva tarea en el sistema.

**Endpoint**: `POST /api/tasks`

**Headers Requeridos**:
- `Content-Type: application/json`

**Cuerpo de la Solicitud**:

```json
{
  "title": "string (requerido)",
  "description": "string (requerido)",
  "completed": boolean (requerido)
}
```

**Notas**:
- El campo `id` no debe enviarse (se genera automáticamente)
- El campo `createdAt` se establece automáticamente

**Respuesta Exitosa**:
- **Código**: `201 CREATED`
- **Tipo**: Objeto Task creado (incluyendo id generado)

**Ejemplo de Solicitud**:

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Preparar presentación",
    "description": "Crear slides para la reunión del viernes",
    "completed": false
  }'
```

**Ejemplo de Respuesta**:

```json
{
  "id": 3,
  "title": "Preparar presentación",
  "description": "Crear slides para la reunión del viernes",
  "completed": false,
  "createdAt": "2025-11-18T14:20:00"
}
```

---

### 4. Actualizar Tarea Existente

Actualiza completamente una tarea existente (todos los campos deben proporcionarse).

**Endpoint**: `PUT /api/tasks/{id}`

**Parámetros de Ruta**:
- `id` (Long) - Identificador de la tarea a actualizar

**Headers Requeridos**:
- `Content-Type: application/json`

**Cuerpo de la Solicitud**:

```json
{
  "title": "string (requerido)",
  "description": "string (requerido)",
  "completed": boolean (requerido)
}
```

**Respuesta Exitosa**:
- **Código**: `200 OK`
- **Tipo**: Objeto Task actualizado

**Respuesta de Error**:
- **Código**: `404 NOT FOUND` - Si la tarea no existe

**Ejemplo de Solicitud**:

```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Estudiar Spring Boot - Actualizado",
    "description": "Repasar conceptos avanzados de Spring Boot y Kotlin",
    "completed": true
  }'
```

**Ejemplo de Respuesta Exitosa**:

```json
{
  "id": 1,
  "title": "Estudiar Spring Boot - Actualizado",
  "description": "Repasar conceptos avanzados de Spring Boot y Kotlin",
  "completed": true,
  "createdAt": "2025-11-18T09:00:00"
}
```

**Notas Importantes**:
- El `id` en el cuerpo de la solicitud se ignora
- El `id` de la URL es el que se utiliza
- El campo `createdAt` se mantiene del registro original
- Es una actualización completa, todos los campos deben enviarse

---

### 5. Eliminar Tarea

Elimina permanentemente una tarea del sistema.

**Endpoint**: `DELETE /api/tasks/{id}`

**Parámetros de Ruta**:
- `id` (Long) - Identificador de la tarea a eliminar

**Respuesta Exitosa**:
- **Código**: `204 NO CONTENT`
- **Cuerpo**: Vacío

**Respuesta de Error**:
- **Código**: `500 INTERNAL SERVER ERROR` - Si la tarea no existe
- **Mensaje**: "Id does not exists."

**Ejemplo de Solicitud**:

```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

**Respuesta Exitosa**: Sin contenido (código 204)

**Nota**: Actualmente hay un bug en el código donde tanto el éxito como el error retornan 204. La implementación correcta debería retornar 404 cuando el ID no existe.

---

### 6. Filtrar Tareas por Estado

Obtiene todas las tareas filtradas por su estado de completitud.

**Endpoint**: `GET /api/tasks/completed/{completed}`

**Parámetros de Ruta**:
- `completed` (Boolean) - `true` para tareas completadas, `false` para pendientes

**Respuesta Exitosa**:
- **Código**: `200 OK`
- **Tipo**: Array de objetos Task

**Ejemplo de Solicitud (Tareas Completadas)**:

```bash
curl -X GET http://localhost:8080/api/tasks/completed/true
```

**Ejemplo de Respuesta**:

```json
[
  {
    "id": 2,
    "title": "Hacer ejercicio",
    "description": "Rutina de 30 minutos de cardio",
    "completed": true,
    "createdAt": "2025-11-18T07:30:00"
  },
  {
    "id": 5,
    "title": "Revisar emails",
    "description": "Responder emails pendientes",
    "completed": true,
    "createdAt": "2025-11-17T16:00:00"
  }
]
```

**Ejemplo de Solicitud (Tareas Pendientes)**:

```bash
curl -X GET http://localhost:8080/api/tasks/completed/false
```

---

## Ejemplos Detallados de Uso

### Flujo Completo: Crear, Consultar, Actualizar y Eliminar

#### Paso 1: Crear una nueva tarea

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Aprender Kotlin",
    "description": "Completar curso de Kotlin en línea",
    "completed": false
  }'
```

**Respuesta**:
```json
{
  "id": 10,
  "title": "Aprender Kotlin",
  "description": "Completar curso de Kotlin en línea",
  "completed": false,
  "createdAt": "2025-11-18T15:00:00"
}
```

#### Paso 2: Consultar la tarea creada

```bash
curl -X GET http://localhost:8080/api/tasks/10
```

**Respuesta**:
```json
{
  "id": 10,
  "title": "Aprender Kotlin",
  "description": "Completar curso de Kotlin en línea",
  "completed": false,
  "createdAt": "2025-11-18T15:00:00"
}
```

#### Paso 3: Marcar la tarea como completada

```bash
curl -X PUT http://localhost:8080/api/tasks/10 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Aprender Kotlin",
    "description": "Completar curso de Kotlin en línea - FINALIZADO",
    "completed": true
  }'
```

**Respuesta**:
```json
{
  "id": 10,
  "title": "Aprender Kotlin",
  "description": "Completar curso de Kotlin en línea - FINALIZADO",
  "completed": true,
  "createdAt": "2025-11-18T15:00:00"
}
```

#### Paso 4: Verificar tareas completadas

```bash
curl -X GET http://localhost:8080/api/tasks/completed/true
```

#### Paso 5: Eliminar la tarea

```bash
curl -X DELETE http://localhost:8080/api/tasks/10
```

**Respuesta**: 204 No Content

---

### Usando Postman

#### Configuración General

1. **Crear una nueva colección**: "Tasks API"
2. **Variables de colección**:
   - `baseUrl`: `http://localhost:8080/api/tasks`

#### Ejemplo: Crear Tarea

**Método**: POST  
**URL**: `{{baseUrl}}`  
**Headers**:
```
Content-Type: application/json
```

**Body** (raw - JSON):
```json
{
  "title": "Tarea desde Postman",
  "description": "Probando la API con Postman",
  "completed": false
}
```

#### Ejemplo: Actualizar Tarea

**Método**: PUT  
**URL**: `{{baseUrl}}/1`  
**Headers**:
```
Content-Type: application/json
```

**Body** (raw - JSON):
```json
{
  "title": "Tarea actualizada",
  "description": "Descripción modificada",
  "completed": true
}
```

---

### Usando JavaScript (Fetch API)

```javascript
// Obtener todas las tareas
async function getAllTasks() {
  try {
    const response = await fetch('http://localhost:8080/api/tasks');
    const tasks = await response.json();
    console.log(tasks);
  } catch (error) {
    console.error('Error:', error);
  }
}

// Crear nueva tarea
async function createTask(taskData) {
  try {
    const response = await fetch('http://localhost:8080/api/tasks', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(taskData)
    });
    const newTask = await response.json();
    console.log('Tarea creada:', newTask);
  } catch (error) {
    console.error('Error:', error);
  }
}

// Actualizar tarea
async function updateTask(id, taskData) {
  try {
    const response = await fetch(`http://localhost:8080/api/tasks/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(taskData)
    });
    if (response.ok) {
      const updatedTask = await response.json();
      console.log('Tarea actualizada:', updatedTask);
    } else {
      console.log('Tarea no encontrada');
    }
  } catch (error) {
    console.error('Error:', error);
  }
}

// Eliminar tarea
async function deleteTask(id) {
  try {
    const response = await fetch(`http://localhost:8080/api/tasks/${id}`, {
      method: 'DELETE'
    });
    if (response.status === 204) {
      console.log('Tarea eliminada exitosamente');
    }
  } catch (error) {
    console.error('Error:', error);
  }
}

// Uso
createTask({
  title: "Nueva tarea",
  description: "Descripción de la tarea",
  completed: false
});
```

---

## Códigos de Respuesta HTTP

La API utiliza los siguientes códigos de estado HTTP:

| Código | Descripción | Cuándo se usa |
|--------|-------------|---------------|
| `200 OK` | Solicitud exitosa | GET, PUT exitosos |
| `201 CREATED` | Recurso creado exitosamente | POST exitoso |
| `204 NO CONTENT` | Solicitud exitosa sin contenido | DELETE exitoso |
| `404 NOT FOUND` | Recurso no encontrado | GET/PUT con ID inexistente |
| `500 INTERNAL SERVER ERROR` | Error del servidor | DELETE con ID inexistente (bug actual) |

---

## Manejo de Errores

### Errores Comunes y Soluciones

#### 1. Error 404 - Tarea No Encontrada

**Causa**: Intentar acceder a una tarea con un ID que no existe.

**Ejemplo**:
```bash
curl -X GET http://localhost:8080/api/tasks/999
```

**Respuesta**: 404 Not Found (cuerpo vacío)

**Solución**: Verificar que el ID exista usando GET /api/tasks antes de realizar operaciones.

---

#### 2. Error 500 - ID No Existe al Eliminar

**Causa**: Intentar eliminar una tarea que no existe.

**Ejemplo**:
```bash
curl -X DELETE http://localhost:8080/api/tasks/999
```

**Respuesta**:
```json
{
  "timestamp": "2025-11-18T15:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Id does not exists.",
  "path": "/api/tasks/999"
}
```

**Solución**: Verificar que el ID exista antes de intentar eliminarlo.

---

#### 3. Error de Validación - Campos Faltantes

**Causa**: No enviar todos los campos requeridos en POST/PUT.

**Ejemplo**:
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Solo título"}'
```

**Solución**: Asegurarse de enviar todos los campos obligatorios (title, description, completed).

---

#### 4. Error de Conexión a Base de Datos

**Síntomas**: La aplicación no inicia o arroja errores al intentar consultas.

**Causa**: MySQL no está ejecutándose o las credenciales son incorrectas.

**Solución**:
```bash
# Verificar estado de MySQL
sudo systemctl status mysql

# Iniciar MySQL si está detenido
sudo systemctl start mysql

# Verificar credenciales en application.properties
```

---
### Documentación Oficial

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Kotlin Reference](https://kotlinlang.org/docs/reference/)

### Herramientas Recomendadas

- **Postman**: Para probar endpoints manualmente
- **Swagger/OpenAPI**: Para documentación interactiva (considerar agregar `springdoc-openapi`)
- **IntelliJ IDEA**: IDE recomendado para desarrollo
- **MySQL Workbench**: Para administrar la base de datos

---

**Versión**: 1.0  
**Última actualización**: 18 de Noviembre, 2025  
**Licencia**: MIT
