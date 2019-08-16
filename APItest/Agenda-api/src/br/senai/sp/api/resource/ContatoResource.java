package br.senai.sp.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.senai.sp.api.model.Contato;
import br.senai.sp.api.repository.ContatoRepository;

@RestController
@RequestMapping("/contato")
public class ContatoResource {
	@Autowired
	private ContatoRepository contatoRepository;
	
	@GetMapping
	public List<Contato> listar(){
		return contatoRepository.findAll();
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) //Essa linha retorna o codigo 201 criado
	public ResponseEntity<Contato> gravar(@RequestBody Contato contato, HttpServletResponse response){
		//Estamos falando que ele está vindo no corpo de uma aquisição
		
		Contato contatoSalvo = contatoRepository.save(contato);
		System.out.println(contatoSalvo.toString());
		//Construindo uma URI
		
		URI uri = ServletUriComponentsBuilder
		.fromCurrentRequestUri()
		.path("/{id}")
		.buildAndExpand(contatoSalvo.getId())
		.toUri();
		
		response.addHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(contatoSalvo);
		
	}
	
	@GetMapping("/{id}")
	public Optional<Contato> getContato(@PathVariable Long id) {//Opcional
		
		return contatoRepository.findById(id); 
		
	}
	
	//DELETE
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)//Muda status para 204
	public void excluir(@PathVariable Long id) {
		
		contatoRepository.deleteById(id);
		
	}
	
	//PUT ATUALIZAR
	
	@PutMapping("/{id}")
	public ResponseEntity<Contato>	atualizar(@RequestBody Contato contato, @PathVariable Long id) {
		//Service
		//Ignora o id 
		Contato contatoSalvo = contatoRepository.findById(id).get();
		BeanUtils.copyProperties(contato, contatoSalvo, "id");
		
		contatoRepository.save(contato);
		
		//Para saber q foi alterado temos q devolver o banco;
		return ResponseEntity.ok(contatoSalvo);
		
	}
	
}
