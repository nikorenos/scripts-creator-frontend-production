package com.creativelabs.scriptscreator.ui;

import com.creativelabs.scriptscreator.MainView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "dialogue-to-script", layout = MainView.class)
@PageTitle("Dialogue To Script | Scripts Creator")
public class DialogueToScriptView extends VerticalLayout {


    public DialogueToScriptView() {
        addClassName("dialogue-to-script-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getContent());
    }

    private VerticalLayout getContent() {
        TextField textField = new TextField("Paste your dialogue:");
        textField.setMinWidth("800px");
        textField.setMinHeight("500px");
        VerticalLayout content = new VerticalLayout(textField);
        content.addClassName("content");
        content.setSizeFull();
        return  content;
    }
}
