package com.creativelabs.scriptscreator.ui;

import com.creativelabs.scriptscreator.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "stats", layout = MainView.class)
@PageTitle("Stats | Scripts Creator")
public class StatsView extends VerticalLayout {


    public StatsView() {
        addClassName("stats-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getContent());
    }

    private VerticalLayout getContent() {
        H1 logo = new H1("Stats:");

        Label labelAllNpcs = new Label("Amount of NPCs: ");
        Label labelAmountOfLocations = new Label("Amount of camps: ");
        Label labelLocations = new Label("Created camps with NPCs: ");
        VerticalLayout content = new VerticalLayout(logo,labelAllNpcs, labelAmountOfLocations, labelLocations);
        content.addClassName("content");
        content.setSizeFull();
        return  content;
    }
}
