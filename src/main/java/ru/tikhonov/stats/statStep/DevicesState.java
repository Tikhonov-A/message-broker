package ru.tikhonov.stats.statStep;

import ru.tikhonov.objects.Device;
import ru.tikhonov.objects.Request;

import java.util.ArrayList;
import java.util.List;

public class DevicesState {
    List<DeviceState> deviceState = new ArrayList<>();

    public DevicesState(List<Device> devices) {
        for(Device d : devices) {
            if (d.isBusy()) {
                Request request = d.getCurrentRequest();
                String sourceNumber = Integer.toString(request.getSourceNumber());
                String requestNumber = Integer.toString(request.getNumberOfRequestFromSource());
                deviceState.add(new DeviceState(
                        Integer.toString(d.getId()),
                        sourceNumber,
                        requestNumber,
                        "Working",
                        Double.toString(d.getEndTime())
                ));
            }
            else {
                /*if (d.getCurrentRequest() != null) {
                    Request request = d.getCurrentRequest();
                    String sourceNumber = Integer.toString(request.getSourceNumber());
                    String requestNumber = Integer.toString(request.getNumberOfRequestFromSource());
                    deviceState.add(new DeviceState(Integer.toString(d.getId()), sourceNumber, requestNumber, "Waiting"));
                } else {*/
                deviceState.add(new DeviceState(
                        Integer.toString(d.getId()),
                        "null",
                        "null",
                        "No request",
                        Double.toString(d.getEndTime())
                ));
                //}
            }
        }
    }

    public List<DeviceState> getDeviceState() {
        return deviceState;
    }
}
