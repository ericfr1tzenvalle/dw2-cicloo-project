/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ericfr1tzenvalle.cicloo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Éric
 */
@Entity // Diz que é uma tabela do banco de dados.
@Table(name = "usuarios") // O nome da tabela do banco é essa. (Evita conflitos)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Diz que o id vai ser
    // Auto incrementado.
    private Long id;
    @Column(name = "nome", nullable = false, length = 100) // É como um varchar(100)
    private String nome;
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    @Column(name = "xpTotal", nullable = false)
    private Long pontosXp = 0L; // Inicia em zero por padrão; 
     /**
     * Relacionamento Um-Para-Muitos: Um usuário pode ter muitos hábitos.
     * mappedBy = "usuario": Indica que o lado 'dono' do relacionamento é a classe Habito, no campo 'usuario'.
     * Isso evita a criação de uma tabela de junção desnecessária.
     * cascade = CascadeType.ALL: Operações (salvar, deletar) em Usuario serão propagadas para seus Habitos.
     * fetch = FetchType.LAZY: Os hábitos só serão carregados do banco quando forem explicitamente solicitados. É uma otimização importante.
     */
    @OneToMany(mappedBy = "usuarios", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Habito> habitos = new ArrayList<>();
    
    
    // Construtores, Getters e Setters são essenciais para o JPA.
    // O JPA exige um construtor sem argumentos.
    public Usuario(){
        
    }
    public Usuario(String nome, String email){
        this.nome = nome;
        this.email = email;
    }
    
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public long getPontosXp() { return pontosXp; }
    public void setPontosXp(long pontosXp) { this.pontosXp = pontosXp; }
    public List<Habito> getHabitos() { return habitos; }
    public void setHabitos(List<Habito> habitos) { this.habitos = habitos; }

    public void adicionarXp(long pontos){
        this.pontosXp += pontos;
    }
}
