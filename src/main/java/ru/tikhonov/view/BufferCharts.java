package ru.tikhonov.view;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.tikhonov.Main;
import ru.tikhonov.objects.Device;
import ru.tikhonov.objects.Source;
import ru.tikhonov.stats.statAuto.StatisticAuto;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Buffer charts")
@Route("buffer-charts")
public class BufferCharts extends VerticalLayout {

    private int sourcesCount;
    private int devicesCount;
    private int startValue;
    private int endValue;
    private double stepValue;
    private final VerticalLayout charts = new VerticalLayout();

    public BufferCharts() {
        VerticalLayout charts = new VerticalLayout();
        HorizontalLayout layout = new HorizontalLayout();

        TextField sourcesCountField = new TextField();
        sourcesCountField.setLabel("Sources count");
        sourcesCountField.setRequired(true);
        TextField devicesCountField = new TextField();
        devicesCountField.setLabel("Devices count");
        devicesCountField.setRequired(true);
        TextField sourcesStart = new TextField();
        sourcesStart.setLabel("Start value");
        sourcesStart.setRequired(true);
        TextField sourcesEnd = new TextField();
        sourcesEnd.setLabel("End value");
        sourcesEnd.setRequired(true);
        TextField sourcesIter = new TextField();
        sourcesIter.setLabel("Step value");
        sourcesIter.setRequired(true);
        TextField aField = new TextField();
        aField.setLabel("a");
        aField.setRequired(true);
        TextField bField = new TextField();
        bField.setLabel("b");
        bField.setRequired(true);
        TextField lambdaField = new TextField();
        lambdaField.setLabel("λ");
        lambdaField.setRequired(true);
        layout.add(sourcesCountField, devicesCountField, aField, bField, lambdaField);
        add(layout);
        HorizontalLayout layoutForIteration = new HorizontalLayout();
        layoutForIteration.add(sourcesStart, sourcesEnd, sourcesIter);
        add(layoutForIteration);
        HorizontalLayout layoutForButtons = new HorizontalLayout();
        Button buttonStart = new Button("Start");
        Button buttonBack = new Button("Back to main page");
        layoutForButtons.add(buttonStart, buttonBack);
        add(layoutForButtons);

        buttonStart.addClickListener(event -> {
            try {
                Source.a = Double.parseDouble(aField.getValue());
                Source.b = Double.parseDouble(bField.getValue());
                Device.lambda = Double.parseDouble(lambdaField.getValue());
                sourcesCount = Integer.parseInt(sourcesCountField.getValue());
                devicesCount = Integer.parseInt(devicesCountField.getValue());
                startValue = Integer.parseInt(sourcesStart.getValue());
                endValue = Integer.parseInt(sourcesEnd.getValue());
                stepValue = Double.parseDouble(sourcesIter.getValue());
                getBufferCharts();
            } catch(NumberFormatException e) {
                Notification.show("You can write ony numbers", 1000, Notification.Position.TOP_STRETCH);
            }
        });

        buttonBack.addClickListener(event -> {
            UI.getCurrent().navigate("");
        });
    }


    private void getBufferCharts() {
        charts.removeAll();
        List<DataSeriesItem> refusedAvgChart = new ArrayList<>();
        List<DataSeriesItem> allTimeAvgChart = new ArrayList<>();
        List<DataSeriesItem> deviceCoefficientChart = new ArrayList<>();

        for (int i = startValue; i < endValue; i += stepValue) {
            Main.start(sourcesCount, i, devicesCount, MainView.requestCountForCharts, false);
            refusedAvgChart.add(new DataSeriesItem(i, StatisticAuto.refusedAvg));
            allTimeAvgChart.add(new DataSeriesItem(i, StatisticAuto.allTimeAvg));
            deviceCoefficientChart.add(new DataSeriesItem(i, StatisticAuto.deviceCoefficientAvg));
            Main.clearAll();
        }
        getFirstChart(refusedAvgChart);
        getSecondChart(allTimeAvgChart);
        getThirdChart(deviceCoefficientChart);
        add(charts);
    }

    private void getFirstChart(List<DataSeriesItem> refuseProb) {
        Chart chart = new Chart(ChartType.AREA);
        Configuration conf = chart.getConfiguration();
        conf.setTitle(new Title("Dependency of refuse probability and buffer capacity"));
        conf.addSeries(new DataSeries(refuseProb));
        charts.add(chart);
    }

    private void getSecondChart(List<DataSeriesItem> timeInSystem) {
        Chart chart = new Chart(ChartType.AREA);
        Configuration conf = chart.getConfiguration();
        conf.setTitle(new Title("Dependency of all time in system and buffer capacity"));
        conf.addSeries(new DataSeries(timeInSystem));
        charts.add(chart);
    }

    private void getThirdChart(List<DataSeriesItem> usageCoefficient) {
        Chart chart = new Chart(ChartType.AREA);
        Configuration conf = chart.getConfiguration();
        conf.setTitle(new Title("Dependency of devices usage coefficient and buffer capacity"));
        conf.addSeries(new DataSeries(usageCoefficient));
        charts.add(chart);
    }
}
