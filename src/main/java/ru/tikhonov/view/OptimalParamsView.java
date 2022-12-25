package ru.tikhonov.view;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.tikhonov.Main;
import ru.tikhonov.objects.Device;
import ru.tikhonov.objects.Source;
import ru.tikhonov.stats.optimalParams.OptimalParams;
import ru.tikhonov.stats.statAuto.SourceStat;
import ru.tikhonov.stats.statAuto.StatisticAuto;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Main page")
@Route("optimal-parameters")
public class OptimalParamsView extends VerticalLayout {

    private static final Grid<OptimalParams> grid = new Grid<>(OptimalParams.class);
    public static int sourcesMin = 5;
    public static int sourcesMax = 10;
    public static int devicesMin = 3;
    public static int devicesMax = 5;
    public static int bufferMin = 2;
    public static int bufferMax = 5;

    public static double[][] ab = {{1, 3}, {3, 5}, {5, 7}};
    public static double[] sourcePrices = {6000, 4000, 2000};
    public static double[] lambda = {0.1, 0.5, 1};
    public static double[] devicePrices = {2000, 4000, 6000};

    public static List<OptimalParams> optionalTable = new ArrayList<>();

    public OptimalParamsView() {
        HorizontalLayout layout = new HorizontalLayout();
        TextField pRefusedField = new TextField();
        pRefusedField.setLabel("Refused probability max");
        pRefusedField.setRequired(true);
        TextField timeInSystemField = new TextField();
        timeInSystemField.setLabel("System time max");
        timeInSystemField.setRequired(true);
        TextField devicesUsageField = new TextField();
        devicesUsageField.setLabel("Devices usage min");
        devicesUsageField.setRequired(true);
        layout.add(pRefusedField, timeInSystemField, devicesUsageField);
        add(layout);

        HorizontalLayout layoutForButtons = new HorizontalLayout();
        Button buttonStart = new Button("Start");
        Button buttonBack = new Button("Back to main page");
        layoutForButtons.add(buttonStart, buttonBack);
        add(layoutForButtons);

        grid.setColumns("sources", "buffer", "devices", "a", "b", "lambda", "price");
        add(grid);

        buttonStart.addClickListener(event -> {
            try {
                optionalTable = new ArrayList<>();
                double refusedProbMax = Double.parseDouble(pRefusedField.getValue());
                double systemTimeMax = Double.parseDouble(timeInSystemField.getValue());
                double devicesUsageMin = Double.parseDouble(devicesUsageField.getValue());
                getParams(refusedProbMax, systemTimeMax, devicesUsageMin);
            } catch (NumberFormatException e) {
                Notification.show("You can write ony numbers", 1000, Notification.Position.TOP_STRETCH);
            }
        });

        buttonBack.addClickListener(event -> {
            UI.getCurrent().navigate("");
        });


    }

    public static void getParams(double refusedProbMax, double systemTimeMax, double devicesUsageMin) {
        for(int source = sourcesMin; source <= sourcesMax; source++) {
            for(int buffer = bufferMin; buffer <= bufferMax; buffer++) {
                for(int device = devicesMin; device <= devicesMax; device++) {
                    for(int z = 0; z < ab.length; z++ ) {
                        for(int y = 0; y < lambda.length; y++ ) {
                            Source.a = ab[z][0];
                            Source.b = ab[z][1];
                            Device.lambda = lambda[y];
                            Main.start(source, buffer, device, 10000, false);
                            double price = source * sourcePrices[z] + buffer * 1000 + device * devicePrices[y];
                            if (StatisticAuto.refusedAvg < refusedProbMax &&
                                    StatisticAuto.allTimeAvg < systemTimeMax &&
                                    StatisticAuto.deviceCoefficientAvg > devicesUsageMin) {
                                optionalTable.add(new OptimalParams(source, buffer, device, Source.a, Source.b, Device.lambda, price));
                            }
                            Main.clearAll();
                            System.out.println(source);
                        }
                    }
                }
            }
        }
        grid.setItems(optionalTable);
    }
}
