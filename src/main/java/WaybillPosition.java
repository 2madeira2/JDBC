public final class WaybillPosition {
    private int position;
    private int price;
    private int nomenclature;
    private int amount;
    private int waybill;

    public WaybillPosition(int position, int price, int nomenclature, int amount, int waybill) {
        this.position = position;
        this.price = price;
        this.nomenclature = nomenclature;
        this.amount = amount;
        this.waybill = waybill;
    }

    public int getWaybill() {
        return waybill;
    }

    public void setWaybill(int waybill) {
        this.waybill = waybill;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public int getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(int nomenclature) {
        this.nomenclature = nomenclature;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
