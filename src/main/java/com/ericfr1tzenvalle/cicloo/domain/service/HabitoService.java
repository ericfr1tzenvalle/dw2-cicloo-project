/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ericfr1tzenvalle.cicloo.domain.service;

import com.ericfr1tzenvalle.cicloo.domain.entity.Habito;
import com.ericfr1tzenvalle.cicloo.domain.entity.RegistroConclusao;
import com.ericfr1tzenvalle.cicloo.domain.entity.Usuario;
import com.ericfr1tzenvalle.cicloo.domain.repository.HabitoRepository;
import com.ericfr1tzenvalle.cicloo.domain.repository.RegistroRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luísa
 */
@Service
public class HabitoService {
    
    private final RegistroRepository registroRepository;
    private final HabitoRepository habitoRepository;
   
    // Injeção de dependência via construtor (melhor prática!)
    public HabitoService(HabitoRepository habitoRepository, RegistroRepository registroRepository) {
        this.habitoRepository = habitoRepository;
        this.registroRepository = registroRepository;
    }
    /**
     * Orquestra a lógica de negócio para concluir um hábito na data de hoje.
     * A anotação @Transactional garante que todas as operações com o banco de dados
     * dentro deste método sejam atômicas. Ou tudo funciona, ou tudo é desfeito (rollback).
     * Isso é CRUCIAL para manter a consistência dos dados.
     */
    @Transactional
    public Habito conclurHabito(Long habitoId){
        LocalDate hoje = LocalDate.now();
        
         // 1. Buscar o hábito. Se não existir, lança uma exceção.
        Habito habito = habitoRepository.findById(habitoId)
                .orElseThrow(() -> new EntityNotFoundException("Hábito não encontrado com o ID: " + habitoId));
        
         // 2. REGRA DE NEGÓCIO: Impedir conclusão duplicada no mesmo dia.
        if(registroRepository.existsByHabitoAndDataConclusao(habito, hoje)){
            // Lançar uma exceção customizada seria o ideal, mas por enquanto vamos apenas logar e retornar.
            System.out.println("Hábito já concluído hoje. Nenhuma alteração foi feita.");
            return habito;
        }
        
        // 3. REGRA DE NEGÓCIO: Calcular e atualizar o streak.
        atualizarStreak(habito, hoje);

        // 4. REGRA DE NEGÓCIO: Adicionar XP ao usuário.
        Usuario usuario = habito.getUsuario();
        usuario.adicionarXp(habito.getXpPorConclusao());

        // 5. Criar o registro histórico da conclusão.
        RegistroConclusao novoRegistro = new RegistroConclusao(hoje, habito);
        registroRepository.save(novoRegistro);

        // 6. Salvar as alterações no hábito.
        // O usuário associado também será atualizado graças ao gerenciamento de entidades do JPA (estado "managed").
        return habitoRepository.save(habito);
        
        
    }
     /**
     * Método privado auxiliar para encapsular a lógica de cálculo do streak.
     */
    public void atualizarStreak(Habito habito, LocalDate hoje){
        Optional<RegistroConclusao> ultimoRegistroOpt = registroRepository.findTopByHabitoOrderByDataConclusaoDesc(habito);
        
        if(ultimoRegistroOpt.isPresent()){
            LocalDate dataUltimaConclusao = ultimoRegistroOpt.get().getDataConclusao();
             // Verifica se a última conclusão foi ontem.
            if (dataUltimaConclusao.equals(hoje.minusDays(1))) {
                // Continua o streak.
                habito.setStreakAtual(habito.getStreakAtual() + 1);
            } else if (!dataUltimaConclusao.equals(hoje)) {
                // Se não foi ontem nem hoje (caso de erro), o streak quebra e volta para 1.
                habito.setStreakAtual(1);
            }
        } else {
            // Se nunca foi concluído antes, este é o primeiro dia do streak.
            habito.setStreakAtual(1);
        }
            
    }
    
}
