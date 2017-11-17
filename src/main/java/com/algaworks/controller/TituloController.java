package com.algaworks.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.model.StatusTitulo;
import com.algaworks.model.Titulo;
import com.algaworks.service.CandidatoTituloService;
import com.algaworks.titulos.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	private final String CADASTRO_VIEW = "cadastro-titulo";

	@Autowired
	private Titulos titulos;
	
	@Autowired
	private CandidatoTituloService candidatoTituloService;

	@RequestMapping("/novo")
	public ModelAndView novoTitulo() {
		// Nome da página (html, jsp, js)
		// Não coloca-se a extensão porque o Spring acha ela para você.
		ModelAndView modelAndView = new ModelAndView(CADASTRO_VIEW);
		modelAndView.addObject(new Titulo());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		// Caso haja algum erro na validação (Bean validation) ele já retorna
		// para a página:
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}

		try {
			candidatoTituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "Título cadastrado com sucesso!");
			return "redirect:/titulos/novo";
			// Exceções de data com spring mvc:
		} catch (DataIntegrityViolationException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}

	@RequestMapping
	public ModelAndView pesquisar() {
		List<Titulo> todosTitulos = titulos.findAll();

		ModelAndView modelAndView = new ModelAndView("pesquisa-titulos");
		modelAndView.addObject("titulos", todosTitulos);

		return modelAndView;
	}

	@RequestMapping("{id}")
	public ModelAndView editar(@PathVariable("id") Titulo titulo) {
		ModelAndView modelAndView = new ModelAndView(CADASTRO_VIEW);
		modelAndView.addObject(titulo);
		return modelAndView;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		
		candidatoTituloService.excluir(id);
		
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/titulos";
	}

	// Para esse cara ser enxergado pelo thymeleaf: th:each="status :
	// ${statusTituloList}"
	// List vem como sufixo.
	// Ou você dar o nome que quiser conforme o seguinte:
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
}
