/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ericfr1tzenvalle.cicloo.domain.repository;

import com.ericfr1tzenvalle.cicloo.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Luísa
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
