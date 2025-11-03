# Lab 08 - Sistema de Gestión de Productos con API REST

Proyecto: Servidor API REST y Cliente Web para gestionar inventario de productos (Northwind - tabla `Products`).

**Nota**: El documento `API_DOCUMENTACION.pdf` contiene capturas de pantalla con más detalles relacionados a cada punto solicitado (Capturas de pantalla en Postman).

---

## Cómo ejecutar el proyecto

Este proyecto consta de dos aplicaciones Spring Boot que deben ejecutarse en el siguiente orden:

### 1. Iniciar el Servidor API (ServidorAPI_20222297)

El servidor API expone los endpoints REST para gestionar productos.

**Desde el IntelliJ IDEA**
- Abra el proyecto `ServidorAPI_20222297` en IntelliJ IDEA y ejecute el proyecto

**Verificación**: El servidor estará disponible en `http://localhost:8080`

Puede verificar que funciona accediendo a: `http://localhost:8080/product`

---

### 2. Iniciar el Cliente Web (ClienteWeb_20222297)

El cliente web consume la API del servidor y proporciona una interfaz visual.

**IMPORTANTE**: Debe iniciar primero el ServidorAPI antes de ejecutar el cliente.

**Desde el IntelliJ IDEA**
- Abra el proyecto `ClienteWeb_20222297` en IntelliJ IDEA y ejecute el proyecto

**Verificación**: El cliente web estará disponible en `http://localhost:8081`

Abra su navegador y acceda a: `http://localhost:8081`

---

## Configuración de Base de Datos

El proyecto está configurado para conectarse a una base de datos MySQL remota:

- **URL**: `jdbc:mysql://lewisrp.dev:3306/Northwind`
- **Usuario**: `root`
- **Contraseña**: `root`
- **Base de datos**: Northwind (tabla `Products`)

**Nota**: No es necesario configurar una base de datos local. La conexión está pre-configurada en los archivos `application.properties` de cada proyecto.


---

## Estructura del Proyecto

- **ServidorAPI_20222297**: Servidor REST API que expone endpoints para gestionar productos
  - Puerto: 8080
  - Base de datos: MySQL (Northwind)
  
- **ClienteWeb_20222297**: Cliente web que consume la API
  - Puerto: 8081
  - Interfaz web para visualizar y gestionar productos









