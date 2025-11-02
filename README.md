# Documentación de la API - ServidorAPI_20222297

Proyecto: Servidor API para gestionar inventario de productos (Northwind - tabla `products`).

Base de datos:
- URL: `jdbc:mysql://lewisrp.dev:3306/Northwind`
- Usuario: `root`
- Contraseña: `root`

Contenido: descripción de los endpoints expuestos por el servicio REST.\
Nota: El documento API_DOCUMENTACION.pdf contiene capturas de pantalla con más detalles relacionados a cada punto indicado (Capturas de pantalla en Postman).  

---

## Endpoints

### 1) Listar productos
- Método: GET
- URL: `localhost:8080/product`
- Descripción: Devuelve la lista completa de productos.

Entrada:
- Ninguna

Salida (200 OK):
- Array JSON con objetos `Producto`.
- Ejemplo (considerando los dos primeros productos):

```json
[
  {
    "productoId": 1,
    "nombreProducto": "Chai",
    "proveedorId": 1,
    "categoriaId": 1,
    "cantidadPorUnidad": "10 boxes x 20 bags",
    "precioUnidad": 18.00
  },
  {
    "productoId": 2,
    "nombreProducto": "Chang",
    "proveedorId": 1,
    "categoriaId": 1,
    "cantidadPorUnidad": "24 - 12 oz bottles",
    "precioUnidad": 19.00
  }
  ...
]
```

Códigos HTTP posibles:
- 200 OK: listado devuelto correctamente.
- 500 Internal Server Error: error en el servidor (BD, etc.).

---

### 2) Obtener producto por ID
- Método: GET
- URL: `localhost:8080/product/{id}`
- Descripción: Devuelve el producto con el ID especificado.

Entrada:
- Parámetro de ruta: `id` (entero)

Salida:
- 200 OK: objeto JSON con los detalles del producto.
  - Ejemplo:

```json
{
  "productoId": 1,
  "nombreProducto": "Chai",
  "proveedorId": 1,
  "categoriaId": 1,
  "cantidadPorUnidad": "10 boxes x 20 bags",
  "precioUnidad": 18.00
}
```

- 400 Bad Request: si el `id` no es numérico válido o si el producto con ese ID no existe. Respuesta JSON con mensaje:

```json
{ 
  "mensaje": "Producto con id X no encontrado" 
}
```
o
```json
{
  "mensaje": "ID de producto inválido" 
}
```

---

### 3) Crear un producto
- Método: POST
- URL: `localhost:8080/product`
- Descripción: Crea un nuevo producto en la base de datos.

Entrada (body JSON): objeto `Producto` (los campos que no se envíen se dejarán null si la BD lo permite).
- Ejemplo de body:

```json
{
  "productoId": 100,
  "nombreProducto": "Nuevo Producto",
  "proveedorId": 1,
  "categoriaId": 1,
  "cantidadPorUnidad": "10 cajas",
  "precioUnidad": 25.50
}
```

Salida:
- 201 Created: JSON indicando estado y el producto creado.
  - Ejemplo:

```json
{
  "mensaje": "Producto creado exitosamente",
  "producto": {
    "productoId": 100,
    "nombreProducto": "Nuevo Producto",
    "proveedorId": 1,
    "categoriaId": 1,
    "cantidadPorUnidad": "10 cajas",
    "precioUnidad": 25.50
  }
}
```

- 400 Bad Request: en caso de error al crear (por ejemplo violation de constraints). Ejemplo:

```json
{ 
  "mensaje": "Error al crear el producto: [detalle]" 
}
```

---

### 4) Actualizar producto 
- Método: PUT
- URL: `localhost:8080/product`
- Descripción: Actualiza parcialmente un producto, con los campos especificados. En esta implementación el `productoId` se envía en el body y el servidor actualiza solo los campos que no son `null` en el objeto recibido.

Entrada (body JSON): objeto `Producto` con `productoId` obligatorio y los campos a actualizar (campos no enviados o `null` no se modifican).
- Ejemplo (actualizar precio y cantidad):

```json
{
  "productoId": 1,
  "precioUnidad": 22.50,
  "cantidadPorUnidad": "15 cajas"
}
```

Salida:
- 200 OK: JSON indicando resultado:

```json
{
  "resultado": "ok"
}
```

- 400 Bad Request: si no se envía `productoId` o si el `productoId` no existe.
  - Ejemplo (sin ID):

```json
{
  "resultado": "error", 
  "mensaje": "Debe enviar un producto con ID"
}
```

  - Ejemplo (no existe):

```json
{
  "resultado": "error",
  "mensaje": "El ID del producto enviado no existe"
}
```

Observaciones:
- Este endpoint realiza una actualización parcial comprobando cada campo con `!= null`.
- Se recomienda documentar claramente que `null` significa "no cambiar" en esta API o ajustar la semántica según necesidad.

---

### 5) Eliminar producto
- Método: DELETE
- URL: `localhost:8080/product/{id}`
- Descripción: Elimina el producto por ID junto con todas sus referencias en pedidos.

**ADVERTENCIA IMPORTANTE**: Este endpoint elimina en cascada:
  1. **Primero**: Todos los registros de `order details` asociados al producto (pérdida permanente de historial de ventas)
  2. **Luego**: El producto mismo

  Esta operación es **irreversible** y elimina información histórica de pedidos. 


Entrada:
- Parámetro de ruta: `id` (entero)

Salida:
- 200 OK: si la eliminación fue exitosa.
  - Ejemplo:

```json
{
  "mensaje": "Producto eliminado exitosamente",
  "orderDetailsEliminados": 12
}
```

El campo `orderDetailsEliminados` indica cuántos registros de pedidos fueron eliminados junto con el producto.

- 400 Bad Request: si el producto no existe.
  - Ejemplo:

```json
{
  "mensaje": "Producto con id X no encontrado"
}
```

- 500 Internal Server Error: si ocurre un error durante la transacción.
  - Ejemplo:

```json
{
  "mensaje": "Error al eliminar el producto: [detalle]"
}
```

---

## Estructura del objeto Producto (campos manejados por la API)

**Nota**: La tabla `Products` en la base de datos tiene una estructura simplificada con solo 6 campos.

- `productoId` (Integer) — clave primaria, corresponde a columna `ProductID`
- `nombreProducto` (String) — corresponde a columna `ProductName`
- `proveedorId` (Integer) — corresponde a columna `SupplierID`
- `categoriaId` (Integer) — corresponde a columna `CategoryID`
- `cantidadPorUnidad` (String) — corresponde a columna `Unit`
- `precioUnidad` (Decimal / BigDecimal) — corresponde a columna `Price`

---

## Errores comunes y mensajes
- `ID de producto inválido` — cuando un ID no numérico es pasado en la ruta.
- `Producto con id X no encontrado` — cuando no existe registro con ese ID.
- `Debe enviar un producto con ID` — cuando el body de actualización no incluye `productoId`.
- `Error al crear/actualizar/eliminar el producto: [detalle]` — error interno o constraint violado.

## Consideraciones de eliminación en cascada
El endpoint DELETE implementa eliminación en cascada manual mediante transacción:
1. Elimina primero todos los registros de `OrderDetails` relacionados
2. Luego elimina el producto
3. Si cualquier paso falla, la transacción completa se revierte

**Impacto**: Pérdida permanente de historial de ventas. Los registros de pedidos asociados al producto se eliminan sin posibilidad de recuperación.

### Por qué se borra explícitamente `OrderDetails`
La razón técnica por la que el controlador elimina primero los registros de la tabla `OrderDetails` es que la base de datos impone una restricción de clave foránea (FK) entre `OrderDetails` y `Products`. MySQL no permite borrar una fila padre (un `Product`) si existen filas hijas (`OrderDetails`) que la referencian; en ese caso la operación DELETE falla con un error de restricción.

En resumen:
- Si intentáramos ejecutar `DELETE FROM products WHERE ProductID = ?` mientras existan `OrderDetails` apuntando a ese producto, la base de datos rechazará la operación por integridad referencial.
- Para poder eliminar el producto desde la API sin cambiar la estructura de la BD , el controlador borra primero las filas hijas y luego la fila padre dentro de la misma transacción.

---




