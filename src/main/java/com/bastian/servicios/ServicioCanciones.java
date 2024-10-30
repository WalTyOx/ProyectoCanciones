
package com.bastian.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bastian.modelos.Cancion;
import com.bastian.repositorios.RepositorioCanciones;

@Service
public class ServicioCanciones {
	
	@Autowired
	private final RepositorioCanciones repositorioCanciones;
	
	public ServicioCanciones(RepositorioCanciones repositorioCanciones) {
		this.repositorioCanciones = repositorioCanciones;
	}
	
	public List<Cancion> obtenerTodasLasCanciones(){
		return this.repositorioCanciones.findAll();
	}
	
	public Cancion obtenerCancionPorId(Long id) {
        return this.repositorioCanciones.findById(id).orElse(null);
    }
	
	public Cancion agregarCancion(Cancion nuevaCancion) {
		return this.repositorioCanciones.save(nuevaCancion);
	}
	
	public void eliminaCancion(Long id) {
		this.repositorioCanciones.deleteById(id);
	}
	
	public Cancion actualizaCancion(Cancion actualizarCancion) {
		return this.repositorioCanciones.save(actualizarCancion);
	}
	
}
