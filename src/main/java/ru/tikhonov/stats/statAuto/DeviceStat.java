package ru.tikhonov.stats.statAuto;

public class DeviceStat {
    String deviceId;
    String usageCoefficient;

    public DeviceStat(String deviceId, String usageCoefficient) {
        this.deviceId = deviceId;
        this.usageCoefficient = usageCoefficient;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getUsageCoefficient() {
        return usageCoefficient;
    }
}
