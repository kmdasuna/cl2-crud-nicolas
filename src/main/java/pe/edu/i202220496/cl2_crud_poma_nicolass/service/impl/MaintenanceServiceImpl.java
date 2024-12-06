package pe.edu.i202220496.cl2_crud_poma_nicolass.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pe.edu.i202220496.cl2_crud_poma_nicolass.dto.FilmDetailDto;
import pe.edu.i202220496.cl2_crud_poma_nicolass.dto.FilmDto;
import pe.edu.i202220496.cl2_crud_poma_nicolass.entity.Film;
import pe.edu.i202220496.cl2_crud_poma_nicolass.entity.Language;
import pe.edu.i202220496.cl2_crud_poma_nicolass.repository.FilmRepository;
import pe.edu.i202220496.cl2_crud_poma_nicolass.repository.LanguageRepository;
import pe.edu.i202220496.cl2_crud_poma_nicolass.service.impl.MaintenanceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Override
    @Cacheable(value = "films")
    public List<FilmDto> findAllFilms() {
        List<FilmDto> films = new ArrayList<>();
        Iterable<Film> iterable = filmRepository.findAll();
        iterable.forEach(film -> {
            FilmDto filmDto = new FilmDto(
                    film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalDuration(),
                    film.getRentalRate()
            );
            films.add(filmDto);
        });
        return films;
    }

    @Override
    @Cacheable(value = "films", key = "#id")
    public FilmDetailDto findDetailById(Integer id) {
        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(
                film -> new FilmDetailDto(
                        film.getFilmId(),
                        film.getTitle(),
                        film.getDescription(),
                        film.getReleaseYear(),
                        film.getRentalDuration(),
                        film.getRentalRate(),
                        film.getLength(),
                        film.getReplacementCost(),
                        film.getRating(),
                        film.getSpecialFeatures(),
                        film.getLastUpdate(),
                        film.getLanguage()
                )
        ).orElse(null);
    }

    @Override
    @CacheEvict(value = "films", allEntries = true)
    public Boolean updateFilm(FilmDetailDto filmDetailDto) {
        Optional<Film> optional = filmRepository.findById(filmDetailDto.filmId());
        return optional.map(
                film -> {
                    film.setTitle(filmDetailDto.title());
                    film.setDescription(filmDetailDto.description());
                    film.setReleaseYear(filmDetailDto.releaseYear());
                    film.setRentalDuration(filmDetailDto.rentalDuration());
                    film.setRentalRate(filmDetailDto.rentalRate());
                    film.setLength(filmDetailDto.length());
                    film.setReplacementCost(filmDetailDto.replacementCost());
                    film.setRating(filmDetailDto.rating());
                    film.setSpecialFeatures(filmDetailDto.specialFeatures());
                    film.setLastUpdate(new Date());
                    filmRepository.save(film);
                    return true;
                }
        ).orElse(false);
    }

    @Override
    @CacheEvict(value = "films", allEntries = true)
    public Boolean deleteFilm(Integer id) {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if (optionalFilm.isPresent()) {
            filmRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @CacheEvict(value = "films", allEntries = true)
    public Boolean saveFilm(FilmDetailDto filmDetailDto) {
        Language language = filmDetailDto.language();
        if (language == null) {
            return false;
        }

        Film newFilm = new Film(
                null,
                filmDetailDto.title(),
                filmDetailDto.description(),
                filmDetailDto.releaseYear(),
                filmDetailDto.language().getLanguageId(),
                filmDetailDto.rentalDuration(),
                filmDetailDto.rentalRate(),
                filmDetailDto.length(),
                filmDetailDto.replacementCost(),
                filmDetailDto.rating(),
                filmDetailDto.specialFeatures(),
                new Date(),
                language
        );

        filmRepository.save(newFilm);
        return true;
    }
}

