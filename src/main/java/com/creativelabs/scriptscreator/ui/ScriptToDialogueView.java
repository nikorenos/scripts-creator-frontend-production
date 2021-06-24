package com.creativelabs.scriptscreator.ui;

import com.creativelabs.scriptscreator.MainView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "script-to-dialogue", layout = MainView.class)
@PageTitle("Script To Dialogue | Scripts Creator")
public class ScriptToDialogueView extends VerticalLayout {


    public ScriptToDialogueView() {
        addClassName("script-to-dialogue-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getContent());
    }

    private VerticalLayout getContent() {
        TextArea textarea = new TextArea("Paste your script:");
        textarea.setMinWidth("800px");
        textarea.setMinHeight("500px");
        VerticalLayout content = new VerticalLayout(textarea);
        content.addClassName("content");
        content.setSizeFull();
        return  content;
    }
}
