import abstraction.BatteryCareCharging;
import abstraction.ChargingMode;
import abstraction.FastCharging;
import adaptee.LegacyBatteryController;
import adapter.LegacyChargerAdapter;
import implementor.ChargingBackend;
import implementor.ChargingException;
import implementor.LaptopChargingBackend;
import implementor.PowerStationChargingBackend;
import selector.ChargingBackendSelector;
public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Main <device> <mode>");
            System.out.println("Devices: LAPTOP, POWER_STATION, LEGACY_BATTERY");
            System.out.println("Modes: FAST, CARE");
            return;
        }
        String deviceType = args[0];
        String requestedMode = args[1];
        ChargingBackendSelector selector = new ChargingBackendSelector();
        registerBackends(selector);
        try {
            ChargingBackend backend = selector.select(deviceType);
            ChargingMode chargingMode = createChargingMode(requestedMode, backend);
            System.out.println("Device: " + deviceType.toUpperCase());
            System.out.println("Mode: " + requestedMode.toUpperCase());
            System.out.println("Battery level: " + chargingMode.getBatteryLevel() + "%");
            chargingMode.charge();
            System.out.println("Charging started successfully.");
        } catch (ChargingException | IllegalArgumentException e) {
            System.out.println("Charging failed: " + e.getMessage());
        }
    }
    private static void registerBackends(
            ChargingBackendSelector selector
    ) {
        selector.register("LAPTOP", LaptopChargingBackend::new);
        selector.register("POWER_STATION", PowerStationChargingBackend::new);
        selector.register("LEGACY_BATTERY", () -> new LegacyChargerAdapter(new LegacyBatteryController(), "LEGACY-BATTERY-001"));
    }
    private static ChargingMode createChargingMode(String mode, ChargingBackend backend) {
        if ("FAST".equalsIgnoreCase(mode)) {
            return new FastCharging(backend);
        }
        if ("CARE".equalsIgnoreCase(mode)) {
            return new BatteryCareCharging(backend);
        }
        throw new IllegalArgumentException("Unsupported charging mode: " + mode);
    }
}