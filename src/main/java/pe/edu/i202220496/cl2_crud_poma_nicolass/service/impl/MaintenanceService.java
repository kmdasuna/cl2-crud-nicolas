package pe.edu.i202220496.cl2_crud_poma_nicolass.service.impl;

import pe.edu.i202220496.cl2_crud_poma_nicolass.dto.FilmDetailDto;
import pe.edu.i202220496.cl2_crud_poma_nicolass.dto.FilmDto;

import java.util.List;

public interface MaintenanceService {

    List<FilmDto> findAllFilms();

    FilmDetailDto findDetailById(Integer id);

    Boolean updateFilm(FilmDetailDto filmDetailDto);

    Boolean deleteFilm(Integer id);

    Boolean saveFilm(FilmDetailDto filmDetailDto);
}
