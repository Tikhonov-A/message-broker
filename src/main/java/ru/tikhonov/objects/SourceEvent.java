package ru.tikhonov.objects;

public class SourceEvent extends Event {
    private final Source source;
    private final Request request;

    public SourceEvent(double time, Source source, Request request) {
        super(time);
        this.source = source;
        this.request = request;
    }

    public Source getSource() {
        return source;
    }

    public Request getRequest() {
        return request;
    }
}
