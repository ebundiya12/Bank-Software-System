package banking;

public class TransferValidator extends CommandValidator {


    TransferValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] arguments = command.split(" ");

        boolean arg_transfer;
        boolean arg_id_1, arg_id_2, both_ids;
        boolean arg_amount;
        boolean exists, exists_1, exists_2;

        if (arguments.length != 4) {
            return false;
        }

        exists_1 = is_existing_id(arguments[1]);
        exists_2 = is_existing_id(arguments[2]);
        exists = exists_1 && exists_2;

        if (!exists || arguments[1].equalsIgnoreCase(arguments[2])) {
            return false;
        }

        arg_transfer = withdraw_time(arguments[1]) && validate_deposit_type(arguments[2]);
        arg_id_1 = validate_transfer_account_type(arguments[1]);
        arg_id_2 = validate_transfer_account_type(arguments[2]);
        both_ids = arg_id_1 && arg_id_2;
        arg_amount = validate_transfer_amount(arguments[1], arguments[2], arguments[3]);

        return (arg_transfer && both_ids && arg_amount);
    }


    public boolean validate_transfer_amount(String id_1, String id_2, String amt) {
        double amount = Double.parseDouble(amt);
        return (bank.isTransferAmountInRange(id_1, id_2, amount));
    }

    public boolean validate_transfer_account_type(String id) {
        return (bank.accountTypeTransfers(id));
    }

}
