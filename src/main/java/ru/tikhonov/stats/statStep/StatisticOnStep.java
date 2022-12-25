package ru.tikhonov.stats.statStep;

import ru.tikhonov.Main;

import java.util.ArrayList;
import java.util.List;

public class StatisticOnStep {
    private final List<SourceState> sTable;
    private final List<DeviceState> dTable;
    private final List<BufferState> bTable;
    private final List<RefusedState> rTable;
    private double currentTime;

    public StatisticOnStep(SourcesState sourcesState, BuffersState buffersState, DevicesState devicesState, RefusedsState refusedsState) {
        this.sTable = sourcesState.getSourcesState();
        this.dTable = devicesState.getDeviceState();
        this.bTable = buffersState.getRequestsInBuffer();

        this.rTable = new ArrayList<>(refusedsState.getRefusedRequests());
        this.currentTime = Main.mainTime;
    }

    public List<SourceState> getsTable() {
        return sTable;
    }

    public List<DeviceState> getdTable() {
        return dTable;
    }

    public List<BufferState> getbTable() {
        return bTable;
    }

    public List<RefusedState> getrTable() {
        return rTable;
    }

    public double getCurrentTime() {
        return currentTime;
    }
}
