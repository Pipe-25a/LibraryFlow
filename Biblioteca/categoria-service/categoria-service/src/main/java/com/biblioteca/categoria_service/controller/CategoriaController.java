package com.biblioteca.categoria_service.controller;

import com.biblioteca.categoria_service.dto.CategoriaRequest;
import com.biblioteca.categoria_service.model.Categoria;
import com.biblioteca.categoria_service.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    
    //GET
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias(){
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria>buscarCategoria(@PathVariable Long id){
        return ResponseEntity.ok(categoriaService.buscarCategoria(id));
    }

    //POST
    @PostMapping
    public ResponseEntity<Categoria> guardarCategoria(@Valid @RequestBody CategoriaRequest request){
        return ResponseEntity.ok(categoriaService.guardarCategoria(request));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id){
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok("Categoria eliminado");
    }
}
