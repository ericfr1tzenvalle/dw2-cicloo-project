/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ericfr1tzenvalle.cicloo.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author Éric
 */

@Entity
@Table(name = "Registro_conclusao")
public class RegistroConclusao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_conclusao", nullable = false)
    private LocalDate dataConclusao;
    /**
     * Relacionamento Muitos-Para-Um com Habito.
     * Cada registro pertence a um e somente um hábito.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habito_id", nullable = false)
    private Habito habito;
    
    public RegistroConclusao(LocalDate dataConclusao, Habito habito) {
        this.dataConclusao = dataConclusao;
        this.habito = habito;
    }
    
    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDate dataConclusao) { this.dataConclusao = dataConclusao; }
    public Habito getHabito() { return habito; }
    public void setHabito(Habito habito) { this.habito = habito; }
    
    
    
    
}
