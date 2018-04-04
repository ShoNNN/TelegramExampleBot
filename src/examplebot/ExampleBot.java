package examplebot;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.api.objects.replykeyboard.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class ExampleBot extends TelegramLongPollingBot {
    
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new ExampleBot());
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
    
    public String getClientName(String clientName) {
        //Message msg = update.getMessage();
        //String clientName = msg.getText();
        return clientName;
    }

    @Override
    public String getBotToken() {
        return "580139465:AAHkBhEAxrQ2BJ5H-oulRNBNxNsdkRfWpQ8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage(); // Это нам понадобится
	String txt = msg.getText();
        if (txt.equals("/start")) {
            sendMsg(msg, "Привет! Меня зовут " + getBotUsername() + " А как твоё имя?");
          
        }
        String name = msg.getText();
        if (!name.isEmpty() && !name.equals("/start")
                && !txt.equals("Хорошо")
                && !txt.equals("Отлично")
                && !txt.equals("Плохо")){
            sendMsg(msg, "Привет! "+ getClientName(name) +". теперь я могу немного больше!");
            sendMsg(msg, "как у тебя дела?");
        }
        switch (msg.getText()){
            case "Хорошо":
                sendMsg(msg, "Молодец, у меня тоже");
                break;
            case "Отлично":
                sendMsg(msg, "Так держать!");
                break;
            case "Плохо":
                sendMsg(msg, "и такое бывает :D");
                break;
        }             
    }

    @Override
    public String getBotUsername() {
        return "TheVladbot";
    }
    
    @SuppressWarnings("deprecation") // Означает то, что в новых версиях метод уберут или заменят
    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.enableMarkdown(true);
             
        //создаём клавиатуру
        ReplyKeyboardMarkup replyKeyBoadrdMarkup = new ReplyKeyboardMarkup();
        s.setReplyMarkup(replyKeyBoadrdMarkup);
        replyKeyBoadrdMarkup.setSelective(true);
        replyKeyBoadrdMarkup.setResizeKeyboard(true);
        replyKeyBoadrdMarkup.setOneTimeKeyboard(false);
               
        //создайм список клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        
        //первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        
        //добавляем клавиши 
        keyboardFirstRow.add("Отлично");
        keyboardSecondRow.add("Хорошо");
        keyboardThirdRow.add("Плохо");
        
        //добавляем спислк в клавиатуру
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        
        //устанавливаем этот список на нашу клавиатуру
        replyKeyBoadrdMarkup.setKeyboard(keyboard);
             
        s.setChatId(msg.getChatId().toString()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        s.setText(text);
        try { //Чтобы не крашнулась программа при вылете Exception 
            sendMessage(s);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }
}
