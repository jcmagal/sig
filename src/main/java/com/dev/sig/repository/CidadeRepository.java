package com.dev.sig.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dev.sig.models.Cidade;


import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    List<Cidade> findByEstadoId(Long estadoId);
}