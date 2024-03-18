package banking;

import java.util.List;

public class MasterControl {

    Bank bank;
    CommandValidator commandValidator;
    CommandProcessor commandProcessor;
    MainStorage mainStorage;

    public MasterControl(Bank bank, CommandValidator commandValidator,
                         CommandProcessor commandProcessor, MainStorage mainStorage) {
        this.bank = bank;
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.mainStorage = mainStorage;
    }


    public List<String> start(List<String> input) {

        for (String command : input) {
            if (commandValidator.validate(command)) {
                commandProcessor.process(command);
            } else {
                mainStorage.store_invalid_command(command);
            }
        }

        return mainStorage.get_stored_output_list();
    }
}
