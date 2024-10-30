package com.bastian.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bastian.modelos.Artista;
import com.bastian.modelos.Cancion;
import com.bastian.servicios.ServicioArtistas;
import com.bastian.servicios.ServicioCanciones;

import jakarta.validation.Valid;

@Controller
public class ControladorCanciones {
    
    @Autowired
    private final ServicioCanciones servicioCanciones;

    @Autowired
    private ServicioArtistas servicioArtistas;

    public ControladorCanciones(ServicioCanciones servicioCanciones) {
        this.servicioCanciones = servicioCanciones;
    }

    @GetMapping("/canciones")
    public String desplegarCanciones(Model modelo) {
        List<Cancion> canciones = this.servicioCanciones.obtenerTodasLasCanciones();
        modelo.addAttribute("Canciones", canciones);
        return "canciones.jsp";
    }

    @GetMapping("/canciones/detalles/{idCancion}")
    public String desplegarDetalleCancionId(@PathVariable("idCancion") Long id, Model modelo) {
        Cancion cancion = servicioCanciones.obtenerCancionPorId(id);
        if (cancion != null) {
            modelo.addAttribute("cancion", cancion);
            return "detalleCancion.jsp";
        } else {
            modelo.addAttribute("error", "Ocurrió un error");
            return "detalleCancion.jsp";
        }
    }

    @GetMapping("/agregar/cancion")
    public String desplegarFormularioAgregarCancion(@ModelAttribute Cancion cancion, Model modelo) {
        List<Artista> artistas = (List<Artista>) this.servicioArtistas.obtenerTodosLosArtistas();
        modelo.addAttribute("artistas", artistas);
        return "agregarCancion.jsp";
    }

    @PostMapping("/agregar/cancion")
    public String procesarAgregarCancion(@Valid @ModelAttribute Cancion cancion, 
                                          BindingResult validaciones,
                                          @RequestParam Long id_artista, Model modelo) {
        if (validaciones.hasErrors()) {
            List<Artista> artistas = this.servicioArtistas.obtenerTodosLosArtistas();
            modelo.addAttribute("artistas", artistas);
            return "agregarCancion.jsp";
        }

        Artista artista = this.servicioArtistas.obtenerArtistaPorId(id_artista);
        cancion.setArtista(artista);
        this.servicioCanciones.agregarCancion(cancion);
        return "redirect:/canciones";
    }

    @GetMapping("/formulario/editar/cancion/{id}")
    public String formularioEditarCancion(@ModelAttribute Cancion cancion,
                                          @PathVariable Long id,
                                          Model modelo) {
        Cancion editarCancion = servicioCanciones.obtenerCancionPorId(id);
        modelo.addAttribute("cancion", editarCancion);
        return "editarCancion.jsp";
    }

    @PutMapping("/actualizar/cancion/{id}")
    public String actualizarCancion(@Valid @ModelAttribute Cancion cancion,
                                     BindingResult validaciones,
                                     @PathVariable Long id) {
        if (validaciones.hasErrors()) {
            return "editarCancion.jsp";
        }
        this.servicioCanciones.actualizaCancion(cancion);
        return "redirect:/canciones";
    }

    @DeleteMapping("/eliminar/cancion/{id}")
    public String procesarEliminarCancion(@PathVariable Long id) {
        this.servicioCanciones.eliminaCancion(id);
        return "redirect:/canciones";
    }
}