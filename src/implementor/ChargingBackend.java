package implementor;
public interface ChargingBackend {
    void setChargePower(int watts) throws ChargingException;
    int getBatteryLevel() throws ChargingException;
}
