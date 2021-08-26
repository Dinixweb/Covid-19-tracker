package io.codex.dinix.coronavirustracker.model;

public class LocationStat {
    private String state;
    private String country;
    private int latestTotalCases;
    private int diffFromPrevDay;
    private int latestDeathRate;
    private int deathRateDiff;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public int getLatestDeathRate() {
        return latestDeathRate;
    }

    public void setLatestDeathRate(int latestDeathRate) {
        this.latestDeathRate = latestDeathRate;
    }

    public int getDeathRateDiff() {
        return deathRateDiff;
    }

    public void setDeathRateDiff(int deathRateDiff) {
        this.deathRateDiff = deathRateDiff;
    }

    @Override
    public String toString() {
        return "LocationStat{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                ", diffFromPrevDay=" + diffFromPrevDay +
                ", latestDeathRate=" + latestDeathRate +
                ", deathRateDiff=" + deathRateDiff +
                '}';
    }
}
