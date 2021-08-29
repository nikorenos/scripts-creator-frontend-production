package com.creativelabs.scriptscreator.ui;

import com.creativelabs.scriptscreator.MainView;
import com.creativelabs.scriptscreator.scriptshandle.ReadStringByLine;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "dialogue-to-script", layout = MainView.class)
@PageTitle("Dialogue To Script | Scripts Creator")
public class DialogueToScriptFromStringView extends VerticalLayout {

    TextArea dialogueArea = new TextArea("Paste your dialogue:");
    TextField questCodeName = new TextField("Quest code name:");
    TextArea scriptArea = new TextArea("Script:");

    public DialogueToScriptFromStringView() {
        addClassName("dialogue-to-script-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getContent());
    }

    public String getDialogue() {
        return dialogueArea.getValue();
    }

    private VerticalLayout getContent() {

        ReadStringByLine readStringByLine = new ReadStringByLine();


        dialogueArea.setMinWidth("1200px");
        dialogueArea.setMinHeight("200px");
        scriptArea.setMinWidth("1200px");
        scriptArea.setMinHeight("200px");

        Button buttonConvert = new Button("Convert");
        buttonConvert.addClickListener(clickEvent ->
                scriptArea.setValue(readStringByLine.convertDialogueIntoScript(dialogueArea.getValue(), questCodeName.getValue())));
        buttonConvert.addClickListener(clickEvent ->
                Notification.show("Script created!"));

        buttonConvert.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonConvert.addClickShortcut(Key.ENTER);
        VerticalLayout content = new VerticalLayout(dialogueArea, questCodeName, buttonConvert, scriptArea);

        addClassName("content");
        content.setSizeFull();
        return  content;
    }

    public static void main(String[] args) {
        String myString = "H: Oto zioła, o które prosiłeś.\n" +
                "N: Daj mi spojrzeć… rzeczywiście, masz wszystko doskonale. Ależ się mój mały szkrab ucieszy.\n" +
                "H: Kto taki?\n";
        String[] lines = myString.split(System.getProperty("line.separator"));

        for (String line: lines) {
            System.out.println(line);

        }
    }
}
