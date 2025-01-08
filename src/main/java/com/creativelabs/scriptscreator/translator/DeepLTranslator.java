package com.creativelabs.scriptscreator.translator;


import com.deepl.api.Translator;

class DeepLTranslator {

    public static String translateDeepL(String text, String sourceLanguage, String targetLanguage) throws Exception {
        //TODO remove from here
        String authKey = "token";
        Translator translator;
        translator = new Translator(authKey);
        return translator.translateText(text, sourceLanguage, targetLanguage).getText();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(translateDeepL("Roy znowu śpi?!", "pl", "en-GB"));
    }
}