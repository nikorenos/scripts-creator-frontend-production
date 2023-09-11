package com.creativelabs.scriptscreator.ui;

import com.creativelabs.scriptscreator.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "guide", layout = MainView.class)
@PageTitle("Guide | Scripts Creator")
public class GuideView extends VerticalLayout {


    public GuideView() {
        addClassName("contact-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getContent());
    }

    private VerticalLayout getContent() {
        H1 logo = new H1("How to write dialogues");

        Label contact = new Label("Guide in Polish: https://docs.google.com/document/d/1EZSue6T-lx67iA2Y5QSJUJaEC0L7I9Jc_ZrkJp_zG3Q/edit#heading=h.c8sgy13ein0m");
        VerticalLayout content = new VerticalLayout(logo,contact);
        content.addClassName("content");
        content.setSizeFull();
        return  content;
    }
}
