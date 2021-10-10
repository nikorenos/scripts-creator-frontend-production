package com.creativelabs.scriptscreator.ui;

import com.creativelabs.scriptscreator.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "contact", layout = MainView.class)
@PageTitle("Contact | Scripts Creator")
public class ContactView extends VerticalLayout {


    public ContactView() {
        addClassName("contact-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getContent());
    }

    private VerticalLayout getContent() {
        H1 logo = new H1("Contact");

        Label contact = new Label("If you have any questions, suggestions, feedback please let us know: goldengatebox@gmail.com");
        VerticalLayout content = new VerticalLayout(logo,contact);
        content.addClassName("content");
        content.setSizeFull();
        return  content;
    }
}
