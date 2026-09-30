package adapter;
import adaptee.LegacyBatteryController;
import implementor.ChargingBackend;
import implementor.ChargingException;
public class LegacyChargerAdapter implements ChargingBackend {
    private static final double BATTERY_VOLTAGE = 20.0;
    private final LegacyBatteryController legacyController;
    private final String batteryId;
    public LegacyChargerAdapter(
            LegacyBatteryController legacyController,
            String batteryId
    ) {
        this.legacyController = legacyController;
        this.batteryId = batteryId;
    }
    @Override
    public void setChargePower(int watts) throws ChargingException {
        if (watts < 0) {
            throw new ChargingException(
                    "Charging power cannot be negative"
            );
        }
        double milliamps = (watts / BATTERY_VOLTAGE) * 1000.0;
        int result = legacyController.configureCurrent(
                batteryId,
                milliamps
        );
        if (result == LegacyBatteryController.ERROR_BATTERY_NOT_FOUND) {
            throw new ChargingException(
                    "Battery was not found by the legacy charging system"
            );
        }
        if (result == LegacyBatteryController.ERROR_CURRENT_TOO_HIGH) {
            throw new ChargingException(
                    "Requested charging power is too high for the legacy battery"
            );
        }
        if (result != LegacyBatteryController.SUCCESS) {
            throw new ChargingException(
                    "Unknown legacy charging system failure"
            );
        }
    }
    @Override
    public int getBatteryLevel() throws ChargingException {
        double ratio = legacyController.readChargeRatio(batteryId);
        if (ratio < 0.0) {
            throw new ChargingException(
                    "Battery level could not be read from the legacy charging system"
            );
        }
        return (int) Math.round(ratio * 100);
    }
}
