package pe.edu.i202220496.cl2_crud_poma_nicolass.dto;

import pe.edu.i202220496.cl2_crud_poma_nicolass.entity.Language;

import java.util.Date;

public record FilmDetailDto(Integer filmId,
                            String title,
                            String description,
                            Integer releaseYear,
                            Integer rentalDuration,
                            Double rentalRate,
                            Integer length,
                            Double replacementCost,
                            String rating,
                            String specialFeatures,
                            Date lastUpdate,
                            Language language) {

}
