package ru.tikhonov.stats.statStep;

import ru.tikhonov.objects.Buffer;
import ru.tikhonov.objects.Request;

import java.util.ArrayList;
import java.util.List;

public class BuffersState {
    private List<BufferState> requestsInBuffer = new ArrayList<>();


    public BuffersState(Buffer buffer) {
        for(Request r : buffer.getBuffer()) {
            String sourceNumber = Integer.toString(r.getSourceNumber());
            String requestNumber = Integer.toString(r.getNumberOfRequestFromSource());
            requestsInBuffer.add(new BufferState(sourceNumber + "." + requestNumber));
        }
    }

    public List<BufferState> getRequestsInBuffer() {
        return requestsInBuffer;
    }
}
