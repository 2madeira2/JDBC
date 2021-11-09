import org.jetbrains.annotations.NotNull;

public final class Nomenclature {
    private int id;
    private @NotNull String name;
    private int internalCode;

    public Nomenclature(int id, @NotNull String name, int internalCode) {
        this.id = id;
        this.name = name;
        this.internalCode = internalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(int internalCode) {
        this.internalCode = internalCode;
    }
}
