package ru.tikhonov.stats.statStep;

import ru.tikhonov.objects.Request;

import java.util.ArrayList;
import java.util.List;

public class RefusedsState {
    private List<RefusedState> refusedRequests = new ArrayList<>();

    public void addRefusedRequest(Request request) {
        String sourceNumber = Integer.toString(request.getSourceNumber());
        String requestNumber = Integer.toString(request.getNumberOfRequestFromSource());
        refusedRequests.add(new RefusedState(sourceNumber + "." + requestNumber));
    }

    public List<RefusedState> getRefusedRequests() {
        return refusedRequests;
    }
}
