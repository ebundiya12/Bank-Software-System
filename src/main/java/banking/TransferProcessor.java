package banking;

public class TransferProcessor extends CommandProcessor {

    public TransferProcessor(Bank bank) {
        super(bank);
    }

    public void transfer_process(String command) {
        String[] commands = command.split(" ");
        bank.accountTransfer(commands[1], commands[2], commands[3]);

        update_account_history(commands[1], command);
        update_account_history(commands[2], command);
    }
}
