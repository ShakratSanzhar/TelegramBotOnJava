import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Botik extends TelegramLongPollingBot {

    public static void main(String[] args) {
        try
        {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Botik());
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message,String text)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Long.toString(message.getChatId()));
        sendMessage.setText(text);
        try
        {
            setButtons(sendMessage);
            execute(sendMessage);
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update)
    {
        Model2Gis gis = new Model2Gis();
        Double[] array = new Double[2];
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText())
        {

            switch (message.getText())
            {
                case "/help":
                    sendMsg(message, "Can I help you?");
                break;
                case "/setting":
                    sendMsg(message, "Would you like to set smth?");
                break;
                default:
                    try
                    {
                        array = Map2Gis.getCoordinate(message.getText(), gis);
                        Double lat = array[0];
                        Double lon = array[1];
                        sendMsg(message, Weather.getWeather(lat, lon, model));
                    }
                    catch (IOException e)
                    {
                        sendMsg(message, "City not found!");
                    }
            }
        }
    }

    public void setButtons(SendMessage sendMessage)
    {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/setting"));
        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public String getBotUsername()
    {
        return "rolik_slitiy_bot";
    }

    public String getBotToken()
    {
        return "5145868152:AAHOZjjGVylBK-rOY2uUkcacUWTi-5_Qa3c";
    }



}
