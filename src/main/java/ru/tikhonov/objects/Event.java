package ru.tikhonov.objects;

public abstract class Event implements Comparable<Event>{
    private final double time;

    public Event(double time) {
        this.time = time;
    }

    @Override
    public int compareTo(Event event) {
        if (event.time - this.time > 0) {
            return -1;
        }
        return 1;
    }

    public double getTime() {
        return time;
    }
}
