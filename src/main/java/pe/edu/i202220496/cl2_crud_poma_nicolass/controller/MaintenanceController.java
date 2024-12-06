package pe.edu.i202220496.cl2_crud_poma_nicolass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.i202220496.cl2_crud_poma_nicolass.dto.FilmDetailDto;
import pe.edu.i202220496.cl2_crud_poma_nicolass.dto.FilmDto;
import pe.edu.i202220496.cl2_crud_poma_nicolass.entity.Film;
import pe.edu.i202220496.cl2_crud_poma_nicolass.entity.Language;
import pe.edu.i202220496.cl2_crud_poma_nicolass.repository.LanguageRepository;
import pe.edu.i202220496.cl2_crud_poma_nicolass.service.impl.MaintenanceService;

import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @Autowired
    private LanguageRepository languageRepository;

    @GetMapping("/start")
    public String start(Model model) {
        List<FilmDto> films = maintenanceService.findAllFilms();
        model.addAttribute("films", films);
        return "maintenance";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findDetailById(id);
        model.addAttribute("film", filmDetailDto);
        return "maintenance-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findDetailById(id);
        model.addAttribute("film", filmDetailDto);
        return "maintenance-edit";
    }

    @PostMapping("/edit-confirm")
    public String edit(@ModelAttribute FilmDetailDto film, Model model) {
        maintenanceService.updateFilm(film);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/delete/{id}")
    public String deleteFilm(@PathVariable Integer id, Model model) {
        boolean deleted = maintenanceService.deleteFilm(id);
        if (deleted) {
            model.addAttribute("message", "Pelicula eliminada correctamente");
        } else {
            model.addAttribute("error", "Pelicula no encontrada");
        }
        return "redirect:/maintenance/start";
    }

    @GetMapping("/new")
    public String showFilmForm(Model model) {
        List<Language> languages = languageRepository.findAll();
        model.addAttribute("languages", languages);
        model.addAttribute("film", new FilmDetailDto());
        return "maintenance-new";
    }

    @PostMapping("/save")
    public String saveFilm(@ModelAttribute FilmDetailDto filmDetailDto, Model model) {
        boolean isSaved = maintenanceService.saveFilm(filmDetailDto);
        if (isSaved) {
            model.addAttribute("message", "Film registered successfully");
        } else {
            model.addAttribute("error", "Error registering film");
        }
        return "redirect:/maintenance/start";
    }

}
