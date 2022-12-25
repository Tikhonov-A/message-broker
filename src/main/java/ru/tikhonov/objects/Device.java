package ru.tikhonov.objects;

import ru.tikhonov.Main;

import java.util.List;

public class Device {

    public static  double lambda = 0.5;
    private static int servedRequestsByAll;
    private static int generator;
    public static int lastDevice;


    private Request currentRequest;
    private int servedRequestsByDevice;
    private final int id = ++generator;
    private boolean isBusy;

    private double timeOnDevice;
    private double endTime;

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public int getId() {
        return id;
    }

    public void submitRequest(Request request, List<Event> events) {
        currentRequest = request;
        double startTime = Main.mainTime;
        double endTime = startTime + getServiceTime();
        this.endTime = endTime;
        events.add(new DeviceEvent(endTime, this));
        setBusy(true);
        request.setDeviceNumber(this.getId());
        servedRequestsByAll++;
        servedRequestsByDevice++;
        request.setTimeOnDevice(endTime - startTime);
    }

    private double getServiceTime() {
        return Math.log(1 - Math.random()) / (- lambda);
    }

    public double getTimeOnDevice() {
        return timeOnDevice;
    }

    public void setTimeOnDevice(double timeOnDevice) {
        this.timeOnDevice += timeOnDevice;
    }

    public static void renew() {
        servedRequestsByAll = 0;
        generator = 0;
        lastDevice = 0;
    }

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(Request currentRequest) {
        this.currentRequest = currentRequest;
    }

    public double getEndTime() {
        return endTime;
    }
}
