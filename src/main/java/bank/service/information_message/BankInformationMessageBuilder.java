package bank.service.information_message;

import java.util.List;
import java.util.Map;
import java.util.Set;

import bank.models.CurrencyInfoDTO;
import bank.service.information_message.renderer.DefaultBankInfoMessageRenderer;
import bank.service.information_message.renderer.NBUInfoMessageRenderer;
import bank.service.information_message.renderer.RenderableInterface;
import keyboard.comands.CommandBank;
import user.User;

public class BankInformationMessageBuilder implements InformationMessageBuilderInterface {
    private final Map<CommandBank, List<CurrencyInfoDTO>> usersCurrenciesMapByBank;
    private final User user;

    public BankInformationMessageBuilder(Map<CommandBank, List<CurrencyInfoDTO>> usersCurrenciesMapByBank, User user) {
        this.usersCurrenciesMapByBank = usersCurrenciesMapByBank;
        this.user = user;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        Set<CommandBank> banks = usersCurrenciesMapByBank.keySet();
        int count = 0;

        for (CommandBank bank : banks) {
            RenderableInterface renderer;

            if (bank.equals(CommandBank.NBU)) {
                renderer = new NBUInfoMessageRenderer(bank, usersCurrenciesMapByBank.get(bank), user);
            } else {
                renderer = new DefaultBankInfoMessageRenderer(bank, usersCurrenciesMapByBank.get(bank), user);
            }
            stringBuilder.append(renderer.render());

            if (count < banks.size() - 1) {
                stringBuilder.append("\n\n\n");
            }

            count++;
        }

        return stringBuilder.toString();
    }
}
