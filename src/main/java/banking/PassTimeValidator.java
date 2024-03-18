package banking;

public class PassTimeValidator extends CommandValidator {

    public static final String PASS_STRING = "pass";

    public PassTimeValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] args = command.split(" ");
        boolean arg_call;
        boolean arg_time;

        if (args.length != 2) {
            return false;
        }

        arg_call = valid_pass_string(args[0]);
        arg_time = valid_time(args[1]);

        return (arg_call && arg_time);
    }

    private boolean valid_time(String input_time) {
        try {
            int time = Integer.parseInt(input_time);
            return (time >= 1 && time <= 60);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean valid_pass_string(String command) {
        return command.equalsIgnoreCase(PASS_STRING);
    }

}
