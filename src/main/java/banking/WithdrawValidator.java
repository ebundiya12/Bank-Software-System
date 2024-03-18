package banking;

public class WithdrawValidator extends CommandValidator {

    WithdrawValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] args = command.split(" ");

        boolean arg_withdraw;
        boolean arg_id;
        boolean arg_amount;
        if (args.length != 3) {
            return false;
        }

        arg_id = is_existing_id(args[1]);
        if (!arg_id) {
            return false;
        }
        arg_withdraw = withdraw_time(args[1]);
        arg_amount = validate_withdraw_amount(args[1], args[2]);

        return (arg_withdraw && arg_amount);
    }


    public boolean validate_withdraw_amount(String q_id, String amt) {
        double amount = Double.parseDouble(amt);
        return (bank.isWithdrawAmountInRange(q_id, amount));
    }


}
