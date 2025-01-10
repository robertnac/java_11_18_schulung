package de.trion.trainings.report;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {


    private final TrainingReportService trainingReportService;

    public ReportController(TrainingReportService trainingReportService) {
        this.trainingReportService = trainingReportService;
    }

    @RequestMapping("/report")
    public ModelAndView info() {

        var report = trainingReportService.getReport();

        var mav = new ModelAndView("report");

        mav.addObject("heading", "Report");
        mav.addObject("report", report);

        return mav;
    }
}
