package ru.tikhonov.stats.statAuto;

public class SourceStat {
    public String sourceId;
    public String requestsCount;
    public String refuseProb;
    public String sumTime;
    public String waitTime;
    public String serveTime;
    public String waitDisp;
    public String serveDisp;

    public SourceStat(String sourceId, String requestsCount, String refuseProb, String sumTime, String waitTime, String serveTime, String waitDisp, String serveDisp) {
        this.sourceId = sourceId;
        this.requestsCount = requestsCount;
        this.refuseProb = refuseProb;
        this.sumTime = sumTime;
        this.waitTime = waitTime;
        this.serveTime = serveTime;
        this.waitDisp = waitDisp;
        this.serveDisp = serveDisp;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getRequestsCount() {
        return requestsCount;
    }

    public String getRefuseProb() {
        return refuseProb;
    }

    public String getSumTime() {
        return sumTime;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public String getServeTime() {
        return serveTime;
    }

    public String getWaitDisp() {
        return waitDisp;
    }

    public String getServeDisp() {
        return serveDisp;
    }
}
