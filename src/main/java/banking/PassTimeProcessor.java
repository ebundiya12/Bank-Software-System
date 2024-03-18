package banking;

public class PassTimeProcessor extends CommandProcessor {

    public PassTimeProcessor(Bank bank) {
        super(bank);
    }

    public void pass_time_process(String[] command) {
        int time_pass = Integer.parseInt(command[1]);
        bank.passAccountsTime(time_pass);
    }
}
