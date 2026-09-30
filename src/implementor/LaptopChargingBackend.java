package implementor;
public class LaptopChargingBackend implements ChargingBackend {
    private int chargePower;
    private int batteryLevel = 50;
    @Override
    public void setChargePower(int watts) throws ChargingException {
        if (watts < 0 || watts > 140) {
            throw new ChargingException("Laptop charging power must be between 0 and 140 watts");
        }
        this.chargePower = watts;
    }
    @Override
    public int getBatteryLevel() {
        return batteryLevel;
    }
    public int getChargePower() {
        return chargePower;
    }
}
