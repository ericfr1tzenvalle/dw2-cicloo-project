/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ericfr1tzenvalle.cicloo.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hibernate.annotations.ManyToAny;

/**
 *
 * @author Éric
 */
@Getter
@Entity
@Table(name = "Habitos")
public class Habito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name= "xp_por_conclusao", nullable = false)
    private int xpPorConclusao;
    @Column(name = "streak_atual", nullable = false)
    private int streakAtual = 0;
    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao = LocalDate.now();
    
     /**
     * Relacionamento Muitos-Para-Um: Muitos hábitos podem pertencer a um usuário.
     * Esta é a ponta 'dona' do relacionamento.
     * @JoinColumn: Define a coluna de chave estrangeira (FK) na tabela 'habitos'.
     * O JPA criará uma coluna chamada 'usuario_id' que referenciará a chave primária da tabela 'usuarios'.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "habitos",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RegistroConclusao> registros = new ArrayList<>();
    
    public Habito(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Habito() {
    }
    
    
    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getXpPorConclusao() { return xpPorConclusao; }
    public void setXpPorConclusao(int xpPorConclusao) { this.xpPorConclusao = xpPorConclusao; }
    public int getStreakAtual() { return streakAtual; }
    public void setStreakAtual(int streakAtual) { this.streakAtual = streakAtual; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<RegistroConclusao> getRegistros() { return registros; }
    public void setRegistros(List<RegistroConclusao> registros) { this.registros = registros; }
    
    
}
