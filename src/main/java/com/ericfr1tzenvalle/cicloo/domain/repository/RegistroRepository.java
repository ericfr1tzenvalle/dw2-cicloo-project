/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ericfr1tzenvalle.cicloo.domain.repository;

import com.ericfr1tzenvalle.cicloo.domain.entity.Habito;
import com.ericfr1tzenvalle.cicloo.domain.entity.RegistroConclusao;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Luísa
 */
@Repository
public interface RegistroRepository extends JpaRepository<RegistroConclusao, Long>{
    
    //A magia do JPA tá ai, usando nomenclaturas ele ente que tipo de consulta sql queremos fazer.
    //Segue a base prefixo + criterio + clausula_opcional
    //prefixos: findby getby queryby, exists by retorna boolean
    //count by retorna um long com quantidade de registros
    //deleteby remove by(deve ser usado dentro de um @Transactional) e serve pra remover
    /**
     * Verifica se já existe um registro de conclusão para um hábito específico em uma data específica.
     * O Spring Data JPA traduz isso para: "SELECT COUNT(*) > 0 FROM registros_conclusao WHERE habito_id = ? AND data_conclusao = ?"
     * É extremamente eficiente, pois não precisa trafegar a entidade inteira.
     */
    boolean existsByHabitoAndDataConclusao(Habito habito, LocalDate data);
    
    /**
     * Encontra o último registro de conclusão de um hábito, ordenando pela data em ordem decrescente e pegando o primeiro (Top 1).
     * Útil para calcular o streak.
     */
    Optional<RegistroConclusao> findTopByHabitoOrderByDataConclusaoDesc(Habito habito);
    
    }

