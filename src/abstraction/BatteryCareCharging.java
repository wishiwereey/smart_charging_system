package abstraction;
import implementor.ChargingBackend;
import implementor.ChargingException;
public class BatteryCareCharging extends ChargingMode {
    private static final int CARE_CHARGING_POWER = 45;
    public BatteryCareCharging(ChargingBackend backend) {
        super(backend);
    }
    @Override
    public void charge() throws ChargingException {
        int batteryLevel = backend.getBatteryLevel();
        if (batteryLevel >= 80) {
            backend.setChargePower(20);
        } else {
            backend.setChargePower(CARE_CHARGING_POWER);
        }
    }
}
