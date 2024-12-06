package pe.edu.i202220496.cl2_crud_poma_nicolass.dto;

public record FilmDto(Integer filmId,
                      String title,
                      String language,
                      Integer rentalDuration,
                      Double rentalRate) {
}
