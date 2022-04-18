package bank.service.information_message.renderer;

import java.util.List;

import bank.models.CurrencyInfoDTO;
import keyboard.comands.CommandBank;
import user.User;

public abstract class BankInfoMessageRenderer implements RenderableInterface {
    protected static final String TITLE_PATTERN = "Курс в <b>%s</b>:\n";

    protected CommandBank bank;
    protected List<CurrencyInfoDTO> currencies;
    protected User user;

    protected BankInfoMessageRenderer(CommandBank bank, List<CurrencyInfoDTO> currencies, User user) {
        this.bank = bank;
        this.currencies = currencies;
        this.user = user;
    }

    protected String formatDouble(Double value) {
        String format = "%." + user.getAccuracy().getTitle() + "f";

        return String.format(format, value);
    }
}
