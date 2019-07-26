package br.com.tedokon.service;

import java.util.List;

import br.com.tedokon.domain.Playlist;

public interface PlaylistService {

    void salvar(Playlist playlist);
    List<Playlist> recuperar();
    Playlist recuperarPorId(long id);
    void atualizar(Playlist playlist);
    void excluir(long id);

}
