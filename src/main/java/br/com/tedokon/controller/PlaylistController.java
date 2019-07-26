package br.com.tedokon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.tedokon.domain.Playlist;
import br.com.tedokon.service.PlaylistService;

@Controller
@RequestMapping("playlists")
public class PlaylistController {

	// insecao dependencia no momento controle criado
    @Autowired 
    private PlaylistService playlistService;

   // dependencia atende por esse metodo
    @GetMapping("/listar") 
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("playlists", playlistService.recuperar());
        return new ModelAndView("/playlist/list", model);
    }

    @GetMapping("/cadastro")
    public String preSalvar(@ModelAttribute("playlist") Playlist playlist) {
        return "/playlist/add";
    }

    // PostMapping => verbo para criar
    // Valid => regra de validacao, validadas nesse momento
    // BindingResult => Caso validacao ocorra algum erro, sera exibido no result
    // RedirectAttributes => parametro desse tipo qdo deseja enviar dados para outra pagina
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("playlist") Playlist playlist, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "/playlist/add";
        }

        playlistService.salvar(playlist);
        attr.addFlashAttribute("mensagem", "Playlist criada com sucesso.");
        return "redirect:/playlists/listar";
        // define o retorno do metodo
    }

    // preAtualizar => direciona usuario para pagina que fara edicao
    // id => variavel passa mesmo valor definido na anotacao id
    @GetMapping("/{id}/atualizar")
    public ModelAndView preAtualizar(@PathVariable("id") long id, ModelMap model) {
        Playlist playlist = playlistService.recuperarPorId(id);
        model.addAttribute("playlist", playlist);
        return new ModelAndView("/playlist/add", model);
    }

    // PutMapping => verbo atualizar
    // Valid => valida os dados 
    @PutMapping("/salvar")
    public String atualizar(@Valid @ModelAttribute("playlist") Playlist playlist, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "/playlist/add";
        }

        playlistService.atualizar(playlist);
        attr.addFlashAttribute("mensagem", "Playlist atualizada com sucesso.");
        return "redirect:/playlists/listar";
    }
    
    // RedirectAttributes => defaul opcao
    @GetMapping("/{id}/remover")
    public String remover(@PathVariable("id") long id, RedirectAttributes attr) {
        playlistService.excluir(id);
        attr.addFlashAttribute("mensagem", "Playlist excluída com sucesso.");
        return "redirect:/playlists/listar";
    }

}
