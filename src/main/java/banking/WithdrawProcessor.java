package banking;

public class WithdrawProcessor extends CommandProcessor {


    public WithdrawProcessor(Bank bank) {
        super(bank);
    }

    public void withdraw_process(String command) {
        String[] commands = command.split(" ");
        bank.accountWithdrawal(commands[1], commands[2]);

        update_account_history(commands[1], command);
    }
}
