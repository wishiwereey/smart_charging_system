package abstraction;
import implementor.ChargingBackend;
import implementor.ChargingException;
public class FastCharging extends ChargingMode {
    private static final int FAST_CHARGING_POWER = 100;
    public FastCharging(ChargingBackend backend) {
        super(backend);
    }
    @Override
    public void charge() throws ChargingException {
        int batteryLevel = backend.getBatteryLevel();
        if (batteryLevel >= 90) {
            backend.setChargePower(30);
        } else {
            backend.setChargePower(FAST_CHARGING_POWER);
        }
    }
}
