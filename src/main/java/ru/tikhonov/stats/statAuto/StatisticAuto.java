package ru.tikhonov.stats.statAuto;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import ru.tikhonov.Main;
import ru.tikhonov.objects.Device;
import ru.tikhonov.objects.Request;
import ru.tikhonov.objects.Source;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StatisticAuto {

    public static  List<SourceStat> sourcesTable = new ArrayList<>();
    public static  List<DeviceStat> devicesTable = new ArrayList<>();
    private static final DecimalFormat decimalFormat = new DecimalFormat("#0.000");
    public static double refusedAvg;
    public static double allTimeAvg;
    public static double deviceCoefficientAvg;


    public static void printSourceTable(List<Source> sources, List<Request> requests) {
        //printAttributesForSource();

        List<Double> sumTime = new ArrayList<>(4);
        for (int i = 0; i < sources.size(); i++) {
            sumTime.add(0.0);
        }
        for (Request r : requests) {
            sources.get(r.getSourceNumber()-1).setTimeInBufferOfGeneratedRequests(r.getTimeInBuffer());
            sources.get(r.getSourceNumber()-1).setTimeOnDeviceOfGeneratedRequests(r.getTimeOnDevice());
            sumTime.set(r.getSourceNumber()-1, sumTime.get(r.getSourceNumber()-1) +
                    r.getTimeInBuffer() + r.getTimeOnDevice());
        }

        double allTimeOnDevice = 0;
        double allTimeInBuffer = 0;
        for (Source s : sources) {
            allTimeOnDevice += s.getTimeOnDeviceOfGeneratedRequests();
            allTimeInBuffer += s.getTimeInBufferOfGeneratedRequests();
        }
        allTimeInBuffer /= sources.size();
        allTimeOnDevice /= sources.size();
        List<Number> refuseProbs = new ArrayList<>();
        List<Number> allTimes = new ArrayList<>();

        for (Source s : sources) {
            double refuseProb = (double)s.getRefusedRequestsBySource()/s.getGeneratedRequestsBySource();
            refuseProbs.add(refuseProb);
            double waitTime = s.getTimeInBufferOfGeneratedRequests()/s.getGeneratedRequestsBySource();
            double serveTime = s.getTimeOnDeviceOfGeneratedRequests()/s.getGeneratedRequestsBySource();

            double sumTimeItog = sumTime.get(s.getId() - 1) /s.getGeneratedRequestsBySource();
            allTimes.add(sumTimeItog);
            double waitDispersion = Math.sqrt(
                    Math.pow(s.getTimeInBufferOfGeneratedRequests()/s.getGeneratedRequestsBySource() - allTimeInBuffer, 2)
                    /(sources.size()-1));
            double serveDispersion = Math.sqrt(
                    Math.pow(s.getTimeOnDeviceOfGeneratedRequests()/s.getGeneratedRequestsBySource() - allTimeOnDevice, 2)
                    /(sources.size()-1));

           /* System.out.println("|   " +
                    s.getId() + "    |       " +
                    s.getGeneratedRequestsBySource() + "        |    " +
                    decimalFormat.format(refuseProb) + "    |  " +
                    decimalFormat.format(sumTimeItog) + "   |   " +
                    decimalFormat.format(waitTime) + "   |    " +
                    decimalFormat.format(serveTime) + "   |   " +
                    decimalFormat.format(waitDispersion) + "   |    " +
                    decimalFormat.format(serveDispersion) + "   |"

            );*/
            sourcesTable.add(new SourceStat(
                    Integer.toString(s.getId()),
                    Integer.toString(s.getGeneratedRequestsBySource()),
                    decimalFormat.format(refuseProb),
                    decimalFormat.format(sumTimeItog),
                    decimalFormat.format(waitTime),
                    decimalFormat.format(serveTime),
                    decimalFormat.format(waitDispersion),
                    decimalFormat.format(serveDispersion)
                    )
            );
            /*System.out.println("______________________________________________________________________________________________________");*/
        }
        refusedAvg = refuseProbs.stream().mapToDouble(Number::doubleValue).average().orElse(0);
        allTimeAvg = allTimes.stream().mapToDouble(Number::doubleValue).average().orElse(0);
    }

    private static void printAttributesForSource() {
        /*System.out.println(
        """
        ______________________________________________________________________________________________________
        |                                              Results                                               |
        ______________________________________________________________________________________________________
        | Source | Requests count | Refuse Prob | Sum time | Wait time | Serve time | Wait disp | Serve disp |
        ______________________________________________________________________________________________________"""
        );*/
    }

    public static void printDeviceTable(List<Device> devices, List<Request> requests) {
        /*System.out.println(
        """
        ________________________________
        |  Device |  Usage coefficient |
        ________________________________"""
        );*/
        for (Request r : requests) {
            devices.get(r.getDeviceNumber() - 1).setTimeOnDevice(r.getTimeOnDevice());
        }
        List<Number> usageCoefficients = new ArrayList<>();
        for (Device d : devices) {
            double usageCoefficient = d.getTimeOnDevice() / Main.mainTime;
            usageCoefficients.add(usageCoefficient);
            /*System.out.println("|    " +
                    d.getId() + "    |        " +
                    decimalFormat.format(usageCoefficient) + "       |"
            );
            System.out.println("________________________________");*/
            devicesTable.add(new DeviceStat(
                    Integer.toString(d.getId()),
                    decimalFormat.format(usageCoefficient)
            ));
        }
        deviceCoefficientAvg = usageCoefficients.stream().mapToDouble(Number::doubleValue).average().orElse(0);
    }

}
