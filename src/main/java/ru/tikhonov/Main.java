package ru.tikhonov;

import ru.tikhonov.objects.*;
import ru.tikhonov.stats.statAuto.StatisticAuto;
import ru.tikhonov.stats.statStep.*;

import java.util.*;

public class Main {

    public static double mainTime;
    public static SourcesState sourcesState;
    public static BuffersState buffersState;
    public static DevicesState devicesState;
    public static RefusedsState refusedState;
    public static List<StatisticOnStep> statisticAll = new ArrayList<>();
    public static List<Event> events = new ArrayList<>();
    public static boolean stepMode;

    public static void start(int sourcesCount, int bufferCapacity, int devicesCount, int requestsCount, boolean stepMode) {
        Main.stepMode = stepMode;
        List<Source> sources = new ArrayList<>(sourcesCount);
        List<Device> devices = new ArrayList<>(sourcesCount);
        List<Request> requests = new ArrayList<>(requestsCount);
        Buffer buffer = new Buffer(bufferCapacity);


        for(int i = 0; i < sourcesCount; i++) {
            sources.add(new Source());
        }
        for(int i = 0; i < devicesCount; i++) {
            devices.add(new Device());
        }

        for (Source s : sources) {
            requests.add(s.generate());
        }

        for (Request r : requests) {
            events.add(new SourceEvent(
                    r.getGenTime(),
                    sources.get(r.getSourceNumber() - 1),
                    r
            ));
        }

        for(int i = 0; i < events.size(); i++) {
            Collections.sort(events);
            Event currentEvent = events.get(i);
            mainTime = currentEvent.getTime();
            if(currentEvent.getClass().equals(SourceEvent.class)) {
                SourceEvent event = (SourceEvent)currentEvent;
                Source source = event.getSource();
                source.setBusy(true);
                if (Source.getGeneratedRequestsByAll() < requestsCount) {
                    Request newRequest = source.generate();
                    requests.add(newRequest);
                    events.add(new SourceEvent(newRequest.getGenTime(), source, newRequest));
                }
                Request request = event.getRequest();
                if (allDevicesBusy(devices)) {
                    buffer.add(request, sources);
                } else {
                    findCurrentDevice(devices).submitRequest(request, events);
                }
                sourcesState = new SourcesState(sources);
                devicesState = new DevicesState(devices);
                source.setBusy(false);
                if (stepMode) {
                    buffersState = new BuffersState(buffer);
                    statisticAll.add(new StatisticOnStep(sourcesState, buffersState, devicesState, refusedState));
                }
            } else if(currentEvent.getClass().equals(DeviceEvent.class)) {
                DeviceEvent event = (DeviceEvent) currentEvent;
                Device device = event.getDevice();
                device.setBusy(false);
                if (!buffer.isEmpty()) {
                    device.submitRequest(buffer.remove(), events);
                } else {
                    //простой
                }

                sourcesState = new SourcesState(sources);
                devicesState = new DevicesState(devices);
                if (stepMode) {
                    buffersState = new BuffersState(buffer);
                    statisticAll.add(new StatisticOnStep(sourcesState, buffersState, devicesState, refusedState));
                }
            }
        }
        StatisticAuto.printSourceTable(sources, requests);
        StatisticAuto.printDeviceTable(devices, requests);
    }

    public static void clearAll() {
        Main.mainTime=0;
        Source.renew();
        Request.renew();
        Device.renew();
        StatisticAuto.sourcesTable = new ArrayList<>();
        StatisticAuto.devicesTable = new ArrayList<>();
        statisticAll = new ArrayList<>();
        sourcesState = null;
        buffersState = null;
        devicesState = null;
        refusedState = new RefusedsState();
        events = new ArrayList<>();
    }

    public static boolean allDevicesBusy(List<Device> devices) {
        boolean flag = true;
        for (Device d : devices) {
            if (!d.isBusy()) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static Device findCurrentDevice(List<Device> devices) {
        for(int i = Device.lastDevice; i < devices.size(); i++) {
            if (!devices.get(i).isBusy()) {
                Device.lastDevice = i;
                return devices.get(i);
            }
        }
        for(int i = 0; i < Device.lastDevice; i++) {
            if (!devices.get(i).isBusy()) {
                Device.lastDevice = i;
                return devices.get(i);
            }
        }
        return null;
    }
}
