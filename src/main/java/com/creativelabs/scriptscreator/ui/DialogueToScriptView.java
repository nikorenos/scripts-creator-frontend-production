package com.creativelabs.scriptscreator.ui;

import com.creativelabs.scriptscreator.MainView;
import com.creativelabs.scriptscreator.scriptshandle.DialogueToScriptByLine;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "dialogue-to-script", layout = MainView.class)
@PageTitle("Dialogue To Script | Scripts Creator")
public class DialogueToScriptView extends VerticalLayout {

    TextField textFieldFile = new TextField("Select file with quest(.txt or .d):");
    TextField textFieldFolder = new TextField("Select Gothic folder:");
    TextField textQuestCodeName = new TextField("Quest code name:");
    Label emptyLabel = new Label("");
    Label instructionTitle = new Label("Instrukcja:");
    Label instructionDescription = new Label("Należy wybrać plik z zadaniem napisany zgodnie z instrukcją pisania questów. Następnie trzeba podać folder "
            + "Gothic i podać kodową nazwę zadania np: KillMonster. Program tworzy skrypt w folderze Gothic 2/_Work/data/Scripts/Content/Story/Dialoge. "
            + "Aby utworzyć pliki Exit wystarczy podać folder z Gothic");


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
    public String getQuestCodeName() {
        return textQuestCodeName.getValue();
    }
    private VerticalLayout getContent() {

        DialogueToScriptByLine dialogue = new DialogueToScriptByLine();

        Button buttonConvert = new Button("Convert");
        buttonConvert.addClickListener(clickEvent ->
                dialogue.writeScript(getDialoguePath(), getFolderPath(), getQuestCodeName()));
        buttonConvert.addClickListener(clickEvent ->
                Notification.show("File converted and saved into: " + getFolderPath() + getQuestCodeName()));


        buttonConvert.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonConvert.addClickShortcut(Key.ENTER);

        VerticalLayout content = new VerticalLayout(textFieldFile, textFieldFolder, textQuestCodeName, buttonConvert, emptyLabel, instructionTitle, instructionDescription);

        addClassName("content");
        content.setSizeFull();
        return  content;
    }
}
