package com.dev.sig.repository;
import com.dev.sig.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("SELECT f FROM Funcionario f JOIN FETCH f.cargo WHERE f.cpf = :cpf")
    Optional<Funcionario> findByCpfIncludingCargo(@Param("cpf") String cpf);

}