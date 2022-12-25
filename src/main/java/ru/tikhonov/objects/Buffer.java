package ru.tikhonov.objects;

import ru.tikhonov.Main;

import java.util.LinkedList;
import java.util.List;

public class Buffer {
    private final List<Request> buffer;
    private int size;
    private final int capacity;

    public Buffer(int capacity) {
        buffer = new LinkedList<>();
        this.capacity = capacity;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(Request request, List<Source> sources) {
        request.setTimeInBuffer(Main.mainTime);
        request.setInBuffer(true);
        if (!isFull()) {
            buffer.add(request);
            size++;
            return true;
        }

        sources.get(request.getSourceNumber()-1).setRefusedRequestsBySource();
        buffer.get(0).setRefused(true);
        buffer.add(request);
        Request oldRequest = buffer.remove(0);
        oldRequest.setTimeInBuffer(Main.mainTime - oldRequest.getTimeInBuffer());
        oldRequest.setInBuffer(false);
        if (Main.stepMode) {
            Main.refusedState.addRefusedRequest(oldRequest);
        }
        return false;
    }

    public Request remove() {
        Request request = buffer.remove(--size);
        request.setInBuffer(false);
        request.setTimeInBuffer(Main.mainTime - request.getTimeInBuffer());
        return request;
    }

    public List<Request> getBuffer() {
        return buffer;
    }
}
