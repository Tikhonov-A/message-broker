package ru.tikhonov.view;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.tikhonov.Main;
import ru.tikhonov.objects.Device;
import ru.tikhonov.objects.Source;
import ru.tikhonov.stats.statStep.*;


@PageTitle("Step mode")
@Route("step")
public class StepView extends VerticalLayout {
    public static int sourcesCount;
    public static int bufferCapacity;
    public static int devicesCount;
    public static int requestsCount;
    private static  int step = -1;

    private final Grid<SourceState> grid1 = new Grid<>(SourceState.class);
    private final Grid<BufferState> grid2 = new Grid<>(BufferState.class);
    private final Grid<DeviceState> grid3 = new Grid<>(DeviceState.class);
    private final Grid<RefusedState> grid4 = new Grid<>(RefusedState.class);

    public StepView() {
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
        Button buttonStart = new Button("Start");
        Button buttonNext = new Button("Next");
        Button buttonBack = new Button("Back to main page");
        layoutForButtons.add(buttonStart, buttonNext, buttonBack);
        add(layoutForButtons);
        Label mainTime = new Label("Current time is 0.0");
        add(mainTime);
        HorizontalLayout layoutForGrid = new HorizontalLayout();
        VerticalLayout layout2 = new VerticalLayout();
        grid1.setWidth("20vw");
        grid1.setHeight("20em");
        grid2.setWidth("10vw");
        grid2.setHeight("20em");
        grid4.setWidth("10vw");
        grid4.setHeight("20em");
        grid3.setWidth("40vw");
        grid3.setHeight("20em");
        grid3.setColumns("id", "state", "requestId", "endTime");
        layoutForGrid.add(grid1, grid2, grid4, grid3);
        add(layoutForGrid);



        buttonStart.addClickListener(event -> {

            try {
                Main.clearAll();
                step = -1;
                Source.a = Double.parseDouble(aField.getValue());
                Source.b = Double.parseDouble(bField.getValue());
                Device.lambda = Double.parseDouble(lambdaField.getValue());
                mainTime.setText("Current time is " + 0.0);
                sourcesCount = Integer.parseInt(sourcesCountField.getValue());
                bufferCapacity = Integer.parseInt(bufferCapacityField.getValue());
                devicesCount = Integer.parseInt(devicesCountField.getValue());
                requestsCount = Integer.parseInt(requestsCountField.getValue());
                Main.start(sourcesCount, bufferCapacity, devicesCount, requestsCount, true);
                Notification.show("The system started. Press next to see new step", 1000, Notification.Position.TOP_STRETCH);
            } catch(NumberFormatException e) {
                Notification.show("You can write ony numbers", 1000, Notification.Position.TOP_STRETCH);
            }
        });

        buttonNext.addClickListener(event -> {
            if(++step < Main.events.size()) {
                grid1.setItems(Main.statisticAll.get(step).getsTable());
                grid2.setItems(Main.statisticAll.get(step).getbTable());
                grid3.setItems(Main.statisticAll.get(step).getdTable());
                grid4.setItems(Main.statisticAll.get(step).getrTable());
                mainTime.setText("Current time is " + Main.statisticAll.get(step).getCurrentTime());
            } else {
                Notification.show("The step mode is over", 1000, Notification.Position.TOP_STRETCH);
            }
        });

        buttonBack.addClickListener(event -> {
            Main.clearAll();
            step = -1;
            UI.getCurrent().navigate("");
        });
    }
}
