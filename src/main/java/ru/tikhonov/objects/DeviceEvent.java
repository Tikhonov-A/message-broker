package ru.tikhonov.objects;

public class DeviceEvent extends Event {
    private final Device device;

    public DeviceEvent(double time, Device device) {
        super(time);
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }
}
