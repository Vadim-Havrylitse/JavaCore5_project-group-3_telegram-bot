package bank.service.information_message.renderer;

import java.util.List;

import bank.models.CurrencyInfoDTO;
import keyboard.comands.CommandBank;
import user.User;

public class DefaultBankInfoMessageRenderer extends BankInfoMessageRenderer {
    private static final String MESSAGE_PATTERN =
            "<i>%s/UAH</i>\n" +
                    "Покупка: %s\n" +
                    "Продажа: %s";

    public DefaultBankInfoMessageRenderer(CommandBank bank, List<CurrencyInfoDTO> currencies, User user) {
        super(bank, currencies, user);
    }

    @Override
    public String render() {
        StringBuilder stringBuilder = new StringBuilder(
                String.format(TITLE_PATTERN, bank.getTitle())
        );

        for (int i = 0; i < currencies.size(); i++) {
            CurrencyInfoDTO currencyInfo = currencies.get(i);

            stringBuilder.append(
                    String.format(
                            MESSAGE_PATTERN,
                            currencyInfo.getCurrency().getTitle(),
                            formatDouble(currencyInfo.getValueBuy()),
                            formatDouble(currencyInfo.getValueSale())
                    )
            );

            if (i < currencies.size() - 1) {
                stringBuilder.append("\n-------------\n");
            }
        }

        return stringBuilder.toString();
    }
}
