package adaptee;
public class LegacyBatteryController {
    public static final int SUCCESS = 0;
    public static final int ERROR_BATTERY_NOT_FOUND = -1;
    public static final int ERROR_CURRENT_TOO_HIGH = -2;
    private double chargeRatio = 0.60;
    public int configureCurrent(String batteryId, double milliamps) {
        if (batteryId == null || batteryId.isBlank()) {
            return ERROR_BATTERY_NOT_FOUND;
        }
        if (milliamps < 0 || milliamps > 10000) {
            return ERROR_CURRENT_TOO_HIGH;
        }
        return SUCCESS;
    }
    public double readChargeRatio(String batteryId) {
        if (batteryId == null || batteryId.isBlank()) {
            return -1.0;
        }
        return chargeRatio;
    }
}