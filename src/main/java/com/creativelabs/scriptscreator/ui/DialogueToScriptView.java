package com.creativelabs.scriptscreator.ui;

import com.creativelabs.scriptscreator.MainView;
import com.creativelabs.scriptscreator.scriptshandle.DialogueToScriptByLine;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "dialogue-to-script", layout = MainView.class)
@PageTitle("Dialogue To Script | Scripts Creator")
public class DialogueToScriptView extends VerticalLayout {

    TextField textFieldFile = new TextField("Select file:");
    TextField textFieldFolder = new TextField("Select folder:");
    TextField textFieldScriptName = new TextField("Script name:");

    public DialogueToScriptView() {
        addClassName("dialogue-to-script-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getContent());
    }

    public String getDialoguePath() {
        return textFieldFile.getValue();
    }
    public String getFolderPath() {
        return textFieldFolder.getValue();
    }
    public String getScriptPath() {
        return textFieldScriptName.getValue();
    }
    private VerticalLayout getContent() {

        //C:/input.d
        //C:/dialogue.d
        DialogueToScriptByLine dialogue = new DialogueToScriptByLine();

        Button button = new Button("Convert");
        button.addClickListener(clickEvent ->
                dialogue.writeScript(getDialoguePath(), getFolderPath() + getScriptPath()));
        button.addClickListener(clickEvent ->
                Notification.show("File converted and saved into: " + getFolderPath() + getScriptPath()));
        //button.addClickListener(clickEvent -> fileChooser.chooseFile());


        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        VerticalLayout content = new VerticalLayout(textFieldFile, textFieldFolder, textFieldScriptName, button);
        addClassName("content");
        content.setSizeFull();
        return  content;
    }
}
