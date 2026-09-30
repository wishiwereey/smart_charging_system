package implementor;
public class PowerStationChargingBackend implements ChargingBackend {
    private int chargePower;
    private int batteryLevel = 70;
    @Override
    public void setChargePower(int watts) throws ChargingException {
        if (watts < 0 || watts > 500) {
            throw new ChargingException(
                    "Power station charging power must be between 0 and 500 watts"
            );
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
