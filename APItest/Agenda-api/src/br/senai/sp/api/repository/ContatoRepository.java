package br.senai.sp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sp.api.model.Contato;
//Faz a fun��o do DAO
public interface ContatoRepository extends JpaRepository<Contato, Long>{

}
