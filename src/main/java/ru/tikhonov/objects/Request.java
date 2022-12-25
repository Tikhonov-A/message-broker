package ru.tikhonov.objects;

public class Request {

    private static int generator;

    private boolean inBuffer;
    private boolean onDevice;
    private boolean refused; //+

    private final int id = ++generator;
    private double genTime;
    private double timeInBuffer; //+
    private double timeOnDevice;
    private int sourceNumber;
    private int deviceNumber = 1; // =1 for refused requests
    private int numberOfRequestFromSource;

    public Request(int sourceNumber, double genTime, int numberOfRequestFromSource) {
        this.sourceNumber = sourceNumber;
        this.genTime = genTime;
        this.numberOfRequestFromSource = numberOfRequestFromSource;
    }

    public double getGenTime() {
        return genTime;
    }

    public void setGenTime(double genTime) {
        this.genTime = genTime;
    }

    public boolean isInBuffer() {
        return inBuffer;
    }

    public boolean isOnDevice() {
        return onDevice;
    }

    public void setInBuffer(boolean inBuffer) {
        this.inBuffer = inBuffer;
    }

    public void setOnDevice(boolean onDevice) {
        this.onDevice = onDevice;
    }

    public double getTimeInBuffer() {
        return timeInBuffer;
    }

    public void setTimeInBuffer(double timeInBuffer) {
        this.timeInBuffer = timeInBuffer;
    }

    public double getTimeOnDevice() {
        return timeOnDevice;
    }

    public void setTimeOnDevice(double timeOnDevice) {
        this.timeOnDevice = timeOnDevice;
    }

    public int getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(int sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public boolean isRefused() {
        return refused;
    }

    public void setRefused(boolean refused) {
        this.refused = refused;
    }

    public int getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public static void renew() {
        generator = 0;
    }

    public int getNumberOfRequestFromSource() {
        return numberOfRequestFromSource;
    }
}
