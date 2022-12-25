package ru.tikhonov.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main page")
@Route()
public class MainView extends VerticalLayout {

    public static int requestCountForCharts = 10000;


    public MainView() {
        HorizontalLayout layout = new HorizontalLayout();
        Button buttonAuto = new Button("Auto mode");
        Button buttonStep = new Button("Step mode");
        layout.add(buttonAuto, buttonStep);
        add(layout);
        HorizontalLayout layoutCharts = new HorizontalLayout();
        Button sourceCharts = new Button("Source charts");
        Button bufferCharts = new Button("Buffer charts");
        Button deviceCharts = new Button("Device charts");
        layoutCharts.add(sourceCharts, bufferCharts, deviceCharts);
        add(layoutCharts);
        HorizontalLayout layoutParams = new HorizontalLayout();
        Button optimalParams = new Button("Optimal parameters");
        layoutParams.add(optimalParams);
        add(layoutParams);


        buttonAuto.addClickListener(event -> {
            UI.getCurrent().navigate("auto");
        });
        buttonStep.addClickListener(event -> {
            UI.getCurrent().navigate("step");
        });

        sourceCharts.addClickListener(event -> {
            UI.getCurrent().navigate("source-charts");
        });
        bufferCharts.addClickListener(event -> {
            UI.getCurrent().navigate("buffer-charts");
        });
        deviceCharts.addClickListener(event -> {
            UI.getCurrent().navigate("device-charts");
        });

        optimalParams.addClickListener(event -> {
            UI.getCurrent().navigate("optimal-parameters");
        });
    }

}
