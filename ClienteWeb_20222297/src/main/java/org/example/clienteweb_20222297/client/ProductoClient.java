package org.example.clienteweb_20222297.client;

import org.example.clienteweb_20222297.entity.Producto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoClient {

    private final RestTemplate restTemplate;
    private final String urlBase;

    public ProductoClient(RestTemplate restTemplate, @Value("${api.base-url:http://localhost:8080}") String urlBase) {
        this.restTemplate = restTemplate;
        this.urlBase = urlBase;
    }

    /**
     * Obtiene todos los productos consumiendo el endpoint GET /product
     * @return Lista de productos
     */
    public List<Producto> obtenerTodosLosProductos() {
        try {
            ResponseEntity<List<Producto>> respuesta = restTemplate.exchange(
                    urlBase + "/product",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Producto>>() {
                    }
            );
            return respuesta.getBody() != null ? respuesta.getBody() : new ArrayList<>();
        } catch (Exception e) {
            // En caso de que la API esté caída o devuelva datos inesperados, retorna lista vacía
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene un producto por ID consumiendo el endpoint GET /product/{id}
     * @param id ID del producto a buscar
     * @return Optional con el producto encontrado o vacío si no existe
     */
    public Optional<Producto> obtenerProductoPorId(Long id) {
        try {
            Producto producto = restTemplate.getForObject(urlBase + "/product/" + id, Producto.class);
            return Optional.ofNullable(producto);
        } catch (HttpClientErrorException.NotFound notFound) {
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
