package com.creativelabs.scriptscreator.ui;

import com.creativelabs.scriptscreator.MainView;
import com.creativelabs.scriptscreator.scriptshandle.DialogueToScriptByLine;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
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
        TextArea textarea = new TextArea("Paste your dialogue:");
        textarea.setMinWidth("800px");
        textarea.setMinHeight("500px");

        String dialoguePath = "C:/input.d";
        String scriptPath = "C:/dialogue.d";
        DialogueToScriptByLine dialogue = new DialogueToScriptByLine();
        Button button = new Button("Convert");
        button.addClickListener(clickEvent ->
                dialogue.writeScript(dialoguePath, scriptPath));

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        VerticalLayout content = new VerticalLayout(textarea, button);
        addClassName("content");
        content.setSizeFull();
        return  content;
    }
}
