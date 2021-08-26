package io.codex.dinix.coronavirustracker.services;

import io.codex.dinix.coronavirustracker.model.LocationStat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static  String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private static String VIRUS_DATA_DEALTH_RATE_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    List<LocationStat> allStat= new ArrayList<LocationStat>();

    List<LocationStat> deathStat= new ArrayList<LocationStat>();


    public List<LocationStat> getDeathStat() {
        return deathStat;
    }

    public List<LocationStat> getAllStat() {
        return allStat;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStat> newStat= new ArrayList<LocationStat>();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest =HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        StringReader stringReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        for (CSVRecord record : records) {
            LocationStat locationStat = new LocationStat();

            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            locationStat.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));

            int latestCases = Integer.parseInt(record.get(record.size()-1));
            int prevDayCase = Integer.parseInt(record.get(record.size()-2));

            locationStat.setDiffFromPrevDay(latestCases-prevDayCase);
            newStat.add(locationStat);
        }

        this.allStat=newStat;

    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void deathRateData() throws IOException, InterruptedException {
        List<LocationStat> dStat= new ArrayList<LocationStat>();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest =HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_DEALTH_RATE_URL))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        StringReader death_rate_csv = new StringReader(httpResponse.body());
        Iterable<CSVRecord> death_records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(death_rate_csv);
        for (CSVRecord record : death_records) {
            LocationStat locationStat = new LocationStat();
            locationStat.setLatestDeathRate(Integer.parseInt(record.get(record.size()-1)));
//            int latestCases = Integer.parseInt(record.get(record.size()-1));
//            int prevDayCase = Integer.parseInt(record.get(record.size()-2));
//
//            locationStat.setDiffFromPrevDay(latestCases-prevDayCase);
            dStat.add(locationStat);
        }

        this.deathStat=dStat;
    }
}
