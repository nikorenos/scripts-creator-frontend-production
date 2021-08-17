package com.creativelabs.scriptscreator.ui;

import com.creativelabs.scriptscreator.MainView;
import com.creativelabs.scriptscreator.scriptshandle.ExitDialogue;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.IOException;


@Route(value = "exit-dialogues", layout = MainView.class)
@PageTitle("Exit dialogues | Scripts Creator")
public class ExitDialogueView extends VerticalLayout {

    TextField textFieldFolder = new TextField("Select Gothic folder:");
    Label emptyLabel = new Label("");
    Label instructionTitle = new Label("Instrukcja:");
    Label instructionDescription = new Label("Pliki exit tworzone są...");


    public ExitDialogueView() {
        addClassName("dialogue-to-script-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getContent());
    }
    public String getFolderPath() {
        return textFieldFolder.getValue();
    }

    private VerticalLayout getContent() {
        ExitDialogue exitDialogue = new ExitDialogue();

        Button buttonExitDialogue = new Button("Create exit dialogues");
        buttonExitDialogue.addClickListener(clickEvent ->
        {
            try {
                exitDialogue.saveExitDialogues(getFolderPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        buttonExitDialogue.addClickListener(clickEvent ->
                Notification.show("Exit dialogues created!"));

        buttonExitDialogue.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonExitDialogue.addClickShortcut(Key.ENTER);

        VerticalLayout content = new VerticalLayout(textFieldFolder, buttonExitDialogue, emptyLabel, instructionTitle, instructionDescription);

        addClassName("content");
        content.setSizeFull();
        return  content;
    }
}
