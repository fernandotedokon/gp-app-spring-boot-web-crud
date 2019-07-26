package br.com.tedokon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tedokon.dao.MusicaDao;
import br.com.tedokon.domain.Musica;

@Service
@Transactional
public class MusicaServiceImpl implements MusicaService {

    @Autowired
    private MusicaDao musicaDao;

    @Autowired
    private PlaylistService playlistService;

    @Override
    public void salvar(Musica musica, long playlistId) {
        musica.setPlaylist(playlistService.recuperarPorId(playlistId));
        musicaDao.salvar(musica);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Musica> recuperarPorPlaylist(long playlistId) {
        return musicaDao.recuperarPorPlaylist(playlistId);
    }

    @Override
    @Transactional(readOnly = true)
    public Musica recuperarPorPlaylistIdEMusicaId(long playlistId, long musicaId) {
        return musicaDao.recuperarPorPlaylistIdEMusicaId(playlistId, musicaId);
    }

    @Override
    public void atualizar(Musica musica, long playlistId) {
        musica.setPlaylist(playlistService.recuperarPorId(playlistId));
        musicaDao.atualizar(musica);
    }

    @Override
    public void excluir(long playlistId, long musicaId) {
        musicaDao.excluir(recuperarPorPlaylistIdEMusicaId(playlistId, musicaId).getId());
    }

}
