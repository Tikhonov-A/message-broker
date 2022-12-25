package ru.tikhonov.objects;

import ru.tikhonov.Main;

public class Source {
    public static double a = 1;
    public static double b = 1.9;
    private static int generatedRequestsByAll;
    private static int generator;

    private final int id = ++generator;
    private int generatedRequestsBySource;
    private int refusedRequestsBySource;
    private double timeInBufferOfGeneratedRequests;
    private double timeOnDeviceOfGeneratedRequests;
    private boolean isBusy;

    private double genTime;

    public int getId() {
        return id;
    }

    public static int getGeneratedRequests() {
        return generatedRequestsByAll;
    }

    public double getGenTime() {
        return genTime;
    }

    public Request generate() {
        genTime += (b - a) * Math.random() + a;
        generatedRequestsByAll++;
        generatedRequestsBySource++;
        return new Request(id, genTime, generatedRequestsBySource);
    }

    public static int getGeneratedRequestsByAll() {
        return generatedRequestsByAll;
    }

    public static void setGeneratedRequestsByAll(int generatedRequestsByAll) {
        Source.generatedRequestsByAll = generatedRequestsByAll;
    }

    public static int getGenerator() {
        return generator;
    }

    public static void setGenerator(int generator) {
        Source.generator = generator;
    }

    public int getGeneratedRequestsBySource() {
        return generatedRequestsBySource;
    }

    public void setGeneratedRequestsBySource(int generatedRequestsBySource) {
        this.generatedRequestsBySource = generatedRequestsBySource;
    }

    public int getRefusedRequestsBySource() {
        return refusedRequestsBySource;
    }

    public void setRefusedRequestsBySource() {
        this.refusedRequestsBySource++;
    }

    public void setGenTime(double genTime) {
        this.genTime = genTime;
    }

    public double getTimeInBufferOfGeneratedRequests() {
        return timeInBufferOfGeneratedRequests;
    }

    public void setTimeInBufferOfGeneratedRequests(double timeInBufferOfGeneratedRequests) {
        this.timeInBufferOfGeneratedRequests += timeInBufferOfGeneratedRequests;
    }

    public double getTimeOnDeviceOfGeneratedRequests() {
        return timeOnDeviceOfGeneratedRequests;
    }

    public void setTimeOnDeviceOfGeneratedRequests(double timeOnDeviceOfGeneratedRequests) {
        this.timeOnDeviceOfGeneratedRequests += timeOnDeviceOfGeneratedRequests;
    }

    public static void renew() {
        generator = 0;
        generatedRequestsByAll = 0;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }
}
