package selector;
import implementor.ChargingBackend;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
public class ChargingBackendSelector {
    private final Map<String, Supplier<ChargingBackend>> backends = new HashMap<>();
    public void register(String deviceType, Supplier<ChargingBackend> backendFactory) {
        backends.put(deviceType.toUpperCase(), backendFactory);
    }
    public ChargingBackend select(String deviceType) {
        Supplier<ChargingBackend> factory = backends.get(deviceType.toUpperCase());
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported device type: " + deviceType);
        }
        return factory.get();
    }
}