package io.codex.dinix.coronavirustracker;

import io.codex.dinix.coronavirustracker.model.LocationStat;
import io.codex.dinix.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public  String home(Model model){
        List<LocationStat> allStat = coronaVirusDataService.getAllStat();
        List<LocationStat> deathStat = coronaVirusDataService.getDeathStat();
        int totalReportedCases = allStat.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
        int totalNewCase = allStat.stream().mapToInt(stat->stat.getDiffFromPrevDay()).sum();
                model.addAttribute("locationStat", allStat);
               /* for(LocationStat deathState: deathStat){
                    model.addAttribute("locationStatDeathRate", deathState.getLatestDeathRate());
                }*/
                NumberFormat nf = NumberFormat.getNumberInstance();
                model.addAttribute("totalReportedCases", nf.format(totalReportedCases));
                model.addAttribute("totalNewCase", nf.format(totalNewCase)+"+");
                model.addAttribute("title", "Corona Virus Tracker");

        return "home";
    }
}
