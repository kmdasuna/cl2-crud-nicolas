package pe.edu.i202220496.cl2_crud_poma_nicolass.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.i202220496.cl2_crud_poma_nicolass.entity.Film;
import org.springframework.transaction.annotation.Transactional;

public interface FilmRepository extends CrudRepository<Film, Integer> {

    @Transactional
    void deleteById(Integer id);
}
