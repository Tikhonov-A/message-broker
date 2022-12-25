package ru.tikhonov.view;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Title;
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
import ru.tikhonov.stats.statAuto.SourceStat;
import ru.tikhonov.stats.statAuto.DeviceStat;
import ru.tikhonov.stats.statAuto.StatisticAuto;


@PageTitle("Auto mode")
@Route("auto")
public class AutoView extends VerticalLayout {

    private final Grid<SourceStat> grid1 = new Grid<>(SourceStat.class);
    private final Grid<DeviceStat> grid2 = new Grid<>(DeviceStat.class);
    public static int sourcesCount;
    public static int bufferCapacity;
    public static int devicesCount;
    public static int requestsCount;

    public AutoView()  {
        HorizontalLayout layout = new HorizontalLayout();
        TextField sourcesCountField = new TextField();
        sourcesCountField.setLabel("Sources count");
        sourcesCountField.setRequired(true);
        TextField bufferCapacityField = new TextField();
        bufferCapacityField.setLabel("Buffer Capacity");
        bufferCapacityField.setRequired(true);
        TextField devicesCountField = new TextField();
        devicesCountField.setLabel("Devices count");
        devicesCountField.setRequired(true);
        TextField requestsCountField = new TextField();
        requestsCountField.setLabel("Request count");
        requestsCountField.setRequired(true);
        TextField aField = new TextField();
        aField.setLabel("a");
        aField.setRequired(true);
        TextField bField = new TextField();
        bField.setLabel("b");
        bField.setRequired(true);
        TextField lambdaField = new TextField();
        lambdaField.setLabel("λ");
        lambdaField.setRequired(true);
        layout.add(sourcesCountField, bufferCapacityField, devicesCountField,
                   requestsCountField, aField, bField, lambdaField);
        add(layout);
        HorizontalLayout layoutForButtons = new HorizontalLayout();
        Button buttonStart = new Button("Start tests");
        Button buttonBack = new Button("Back to main page");
        layoutForButtons.add(buttonStart, buttonBack);
        add(layoutForButtons);
        buttonBack.addClickListener(event -> {
            UI.getCurrent().navigate("");
        });
        grid1.setColumns("sourceId", "requestsCount", "refuseProb", "sumTime",
                         "waitTime", "serveTime", "waitDisp", "serveDisp");
        add(grid1);
        grid2.setColumns("deviceId", "usageCoefficient");
        add(grid2);

        buttonStart.addClickListener(event -> {
            try {
                Source.a = Double.parseDouble(aField.getValue());
                Source.b = Double.parseDouble(bField.getValue());
                Device.lambda = Double.parseDouble(lambdaField.getValue());
                sourcesCount = Integer.parseInt(sourcesCountField.getValue());
                bufferCapacity = Integer.parseInt(bufferCapacityField.getValue());
                devicesCount = Integer.parseInt(devicesCountField.getValue());
                requestsCount = Integer.parseInt(requestsCountField.getValue());
                getStats(sourcesCount, bufferCapacity, devicesCount, requestsCount);
            } catch(NumberFormatException e) {
                Notification.show("You can write ony numbers", 1000, Notification.Position.TOP_STRETCH);
            }
        });
    }

    void getStats(int sourcesCount, int bufferCapacity, int devicesCount, int requestsCount) {
        Main.start(sourcesCount, bufferCapacity, devicesCount, requestsCount, false);
        grid1.setItems(StatisticAuto.sourcesTable);
        grid2.setItems(StatisticAuto.devicesTable);
        Main.clearAll();
    }

}