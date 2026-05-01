package com.duoc.LearningPlatformValidation.service;


import com.duoc.LearningPlatformValidation.dto.InscripcionDTO;
import com.duoc.LearningPlatformValidation.exception.ResourceNotFoundException;
import com.duoc.LearningPlatformValidation.model.Curso;
import com.duoc.LearningPlatformValidation.model.Inscripcion;
import com.duoc.LearningPlatformValidation.model.Usuario;
import com.duoc.LearningPlatformValidation.repository.CursoRepository;
import com.duoc.LearningPlatformValidation.repository.InscripcionRepository;
import com.duoc.LearningPlatformValidation.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Inscripcion> findByCurso(Long cursoId){
        return inscripcionRepository.findByCursoId(cursoId);
    }

    public Inscripcion saveInscripcion(InscripcionDTO inscripcionDTO){
        Curso curso = cursoRepository.findById(inscripcionDTO.getCursoId()).
                orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        Usuario estudiante = usuarioRepository.findById(inscripcionDTO.getEstudianteId()).
                orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));

        Inscripcion inscripcion = new Inscripcion();

        inscripcion.setCurso(curso);
        inscripcion.setEstudiante(estudiante);
        inscripcion.setFechaInscripcion(inscripcionDTO.getFechaInscripcion());

        return inscripcionRepository.save(inscripcion);
    }

    public void deleteInscripcion(Long id){
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripcion no encontrada"));
        inscripcionRepository.delete(inscripcion);
    }
}
