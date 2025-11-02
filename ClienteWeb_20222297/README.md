# Cliente Web - Aplicación de Productos

## Descripción
Aplicación web cliente desarrollada con Spring Boot y Thymeleaf que consume una API REST para mostrar productos del inventario Northwind.

## Requisitos Previos
- Java 17 o superior
- Maven 3.6+
- La API REST (Parte 1) debe estar ejecutándose en `http://localhost:8080`

## Estructura del Proyecto
```
src/main/java/
├── entity/          # Entidades (POJOs)
│   └── Producto.java
├── client/          # Cliente REST para consumir API
│   └── ProductoClient.java
├── controller/      # Controladores web
│   └── ProductoController.java
└── config/          # Configuración
    └── RestConfig.java

src/main/resources/
├── templates/       # Vistas Thymeleaf
│   └── index.html
└── application.properties
```

## Configuración

### application.properties
- **server.port**: Puerto donde corre la aplicación cliente (8081 por defecto)
- **api.base-url**: URL de la API REST (http://localhost:8080 por defecto)

## Funcionalidades Implementadas

### 1. Listar Productos (2 puntos)
- **Vista**: Tabla HTML con todos los productos
- **Endpoint consumido**: `GET /product`
- **Datos mostrados**: ID, Nombre, Descripción, Precio

### 2. Buscar un Producto (2 puntos)
- **Formulario**: Campo de texto para ingresar ID + botón "Buscar"
- **Endpoint consumido**: `GET /product/{id}`
- **Funcionalidad**:
  - Muestra detalles del producto encontrado
  - Muestra mensaje de error si el producto no existe
  - Valida que el ID sea un número válido

### Mensajes de Error Manejados
- ID vacío o no proporcionado
- ID no numérico (formato inválido)
- Producto no encontrado
- API no disponible

## Cómo Ejecutar

### Paso 1: Asegurar que la API esté corriendo
Antes de ejecutar el cliente, verifica que la API REST (Parte 1) esté ejecutándose en el puerto 8080.

### Paso 2: Compilar el proyecto
```powershell
cd 'C:\Users\FABRICIO\Documents\7mo_ciclo\gtics\lab08_20222297\ClienteWeb_20222297'
mvn clean package -DskipTests
```

### Paso 3: Ejecutar la aplicación
```powershell
mvn spring-boot:run
```

O ejecutar el JAR generado:
```powershell
java -jar target\ClienteWeb_20222297-0.0.1-SNAPSHOT.jar
```

### Paso 4: Abrir en el navegador
```
http://localhost:8081
```

## Uso de la Aplicación

### Página Principal
1. Al ingresar, verás una tabla con todos los productos disponibles
2. Cada fila muestra: ID, Nombre, Descripción y Precio

### Buscar Producto
1. En el formulario superior, ingresa el ID del producto que deseas buscar
2. Presiona el botón "Buscar"
3. Si existe, se mostrará una sección con los detalles del producto
4. Si no existe o el ID es inválido, se mostrará un mensaje de error

## Tecnologías Utilizadas
- **Spring Boot 3.5.7**: Framework principal
- **Spring Web**: Para crear el servidor web y cliente REST
- **Thymeleaf**: Motor de plantillas para las vistas HTML
- **RestTemplate**: Cliente HTTP para consumir la API REST
- **Jackson**: Deserialización JSON automática

## Conexión con la API
La aplicación cliente NO se conecta directamente a la base de datos. En su lugar, consume los endpoints REST proporcionados por la API (Parte 1), la cual sí tiene acceso a la base de datos Northwind.

### Flujo de Datos
```
[Cliente Web] --HTTP--> [API REST] --JDBC--> [Base de Datos Northwind]
```

## Notas de Implementación
- **Mapeo JSON a Español**: Los campos del modelo `Producto` usan nombres en español (`nombre`, `descripcion`, `precio`) pero el JSON de la API usa inglés (`name`, `description`, `price`). Se utilizan anotaciones `@JsonProperty` para el mapeo automático.
- **Manejo de Errores**: Se capturan excepciones de conexión, parsing y respuestas 404 para mostrar mensajes amigables al usuario.
- **Puerto Diferente**: El cliente corre en 8081 para no entrar en conflicto con la API que corre en 8080.

## Solución de Problemas

### La tabla aparece vacía
- Verifica que la API esté corriendo en `http://localhost:8080`
- Verifica que la API tenga productos en la base de datos
- Revisa la consola para ver errores de conexión

### No encuentra productos al buscar
- Verifica que el ID exista en la base de datos
- Asegúrate de ingresar un número válido

### Error al iniciar (puerto en uso)
Si el puerto 8081 está en uso, cámbialo en `application.properties`:
```properties
server.port=8082
```
