package de.trion.trainings.training;

import de.trion.trainings.config.TrainingConfig;
import de.trion.trainings.description.DescriptionService;
import de.trion.trainings.training.filter.TrainingFilterService;
import de.trion.trainings.training.search.TrainingSearchResult;
import de.trion.trainings.training.search.TrainingSearchService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/trainings")
public class TrainingWebController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TrainingService trainingService;
    private final TrainingFilterService trainingFilterService;
    private final TrainingSearchService trainingSearchService;
    private final TrainingConfig trainingConfig;
    private final DescriptionService descriptionService;

    public TrainingWebController(TrainingService trainingService, TrainingFilterService trainingFilterService, TrainingSearchService trainingSearchService, TrainingConfig trainingConfig, DescriptionService descriptionService) {
        this.trainingService = trainingService;
        this.trainingFilterService = trainingFilterService;
        this.trainingSearchService = trainingSearchService;
        this.trainingConfig = trainingConfig;
        this.descriptionService = descriptionService;
    }

    @GetMapping("")
    public ModelAndView trainings(@RequestParam(value = "filter", required = false) String filter) {
        var mav = new ModelAndView("/trainings/list");
        var trainings = trainingFilterService.findTrainingsForFilter(filter);

        mav.addObject("heading", trainingConfig.startPageHeading());
        mav.addObject("trainings", trainings);

        mav.addObject("training", new Training());

        return mav;
    }

    @GetMapping("today")
    public ModelAndView today() {
        var mav = new ModelAndView("/trainings/today");
        var trainings = trainingService.findAll();

        mav.addObject("heading", "Trainings Heute");
        mav.addObject("trainings", trainings);

        return mav;
    }

    @GetMapping("search")
    public ModelAndView searchTrainings(@RequestParam(name = "name", required = false) String name) {
        var mav = new ModelAndView("/trainings/search");
        List<TrainingSearchResult> searchResults = trainingSearchService.findByName(name);

        mav.addObject("heading", "Suchergebnis");
        mav.addObject("training", new Training());
        mav.addObject("searchResults", searchResults);

        return mav;
    }

    @GetMapping("{id}")
    public ModelAndView training(@PathVariable String id) {
        var mav = new ModelAndView("/trainings/detail");
        var training = trainingService.getById(id);
        var description = descriptionService.getDescription(training);

        mav.addObject("training", training);
        mav.addObject("description", description);
        return mav;
    }

    @GetMapping("{id}/edit")
    public ModelAndView editTraining(@PathVariable String id) {
        var mav = new ModelAndView("/trainings/edit");
        var training = trainingService.getById(id);

        mav.addObject("training", training);
        return mav;
    }

    @PostMapping("{id}")
    public String saveEdit(@PathVariable String id, @ModelAttribute Training training,
                           BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/trainings/edit";
        }

        request.setAttribute("training", training);
        trainingService.save(training);
        return "redirect:/trainings/%s".formatted(id);
    }

    @GetMapping("create")
    public ModelAndView createTraining() {
        var mav = new ModelAndView("/trainings/create");
        var training = new Training();
        mav.addObject("training", training);
        mav.addObject("heading", "Training anlegen");
        return mav;
    }


    @Transactional
    @PostMapping("")
    public String addTraining(@ModelAttribute("training") Training training, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("trainings", trainingService.findAll());
            model.addAttribute("heading", "Alle Trainings");
            return "/trainings/list";
        }
        var savedTraining = trainingService.save(training);
        logger.info("Saved training {}: {}", savedTraining.getId(), savedTraining.getTopic());
        return "redirect:/trainings";
    }
}
