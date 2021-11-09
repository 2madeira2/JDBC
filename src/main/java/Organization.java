import org.jetbrains.annotations.NotNull;

public final class Organization {
    private @NotNull
    String name;
    private int INN;
    private int checkingAccount;

    public Organization(@NotNull String name, int INN, int checkingAccount) {
        this.name = name;
        this.INN = INN;
        this.checkingAccount = checkingAccount;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getINN() {
        return INN;
    }

    public void setINN(int INN) {
        this.INN = INN;
    }

    public int getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(int checkingAccount) {
        this.checkingAccount = checkingAccount;
    }
}
