package ru.tikhonov.stats.optimalParams;

public class OptimalParams {
    int sources;
    int buffer;
    int devices;

    double a;
    double b;
    double lambda;

    double price;

    public OptimalParams(int sources, int buffer, int devices, double a, double b, double lambda, double price) {
        this.sources = sources;
        this.buffer = buffer;
        this.devices = devices;
        this.a = a;
        this.b = b;
        this.lambda = lambda;
        this.price = price;
    }

    public int getSources() {
        return sources;
    }

    public int getBuffer() {
        return buffer;
    }

    public int getDevices() {
        return devices;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getLambda() {
        return lambda;
    }

    public double getPrice() {
        return price;
    }
}
