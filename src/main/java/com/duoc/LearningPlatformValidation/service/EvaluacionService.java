package com.duoc.LearningPlatformValidation.service;


import com.duoc.LearningPlatformValidation.dto.CursoDTO;
import com.duoc.LearningPlatformValidation.dto.EvaluacionDTO;
import com.duoc.LearningPlatformValidation.exception.ResourceNotFoundException;
import com.duoc.LearningPlatformValidation.model.Curso;
import com.duoc.LearningPlatformValidation.model.Evaluacion;
import com.duoc.LearningPlatformValidation.model.Usuario;
import com.duoc.LearningPlatformValidation.repository.CursoRepository;
import com.duoc.LearningPlatformValidation.repository.EvaluacionRepository;
import com.duoc.LearningPlatformValidation.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    public List<Evaluacion> findAll(){
        return evaluacionRepository.findAll();
    }

    public Evaluacion findById(Long id){
        return evaluacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluacion no encontrada"));
    }

    public Evaluacion saveEvaluacion(EvaluacionDTO evaluacionDTO){
        Curso curso = cursoRepository.findById(evaluacionDTO.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setCurso(curso);
        evaluacion.setNombre(evaluacionDTO.getNombre());
        evaluacion.setPuntajeMaximo(evaluacionDTO.getPuntajeMaximo());
        evaluacion.setFechaAplicacion(evaluacionDTO.getFechaAplicacion());

        return evaluacionRepository.save(evaluacion);
    }

    public Evaluacion updateEvaluacion(Long id,EvaluacionDTO evaluacionDTO){
        Evaluacion evaluacion = evaluacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evaluacion no encontrada"));

        evaluacion.setNombre(evaluacionDTO.getNombre());
        evaluacion.setPuntajeMaximo(evaluacionDTO.getPuntajeMaximo());
        evaluacion.setFechaAplicacion(evaluacionDTO.getFechaAplicacion());

        Long cursoId = evaluacionDTO.getCursoId();
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
        evaluacion.setCurso(curso);

        return evaluacionRepository.save(evaluacion);
    }

    public void deleteEvaluacion(Long id){
        Evaluacion evaluacion = evaluacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluacion no encontrada"));
        evaluacionRepository.delete(evaluacion);
    }
}
