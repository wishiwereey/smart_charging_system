package abstraction;
import implementor.ChargingBackend;
import implementor.ChargingException;
public abstract class ChargingMode {
    protected final ChargingBackend backend;

    protected ChargingMode(ChargingBackend backend) {
        this.backend = backend;
    }

    public abstract void charge() throws ChargingException;

    public int getBatteryLevel() throws ChargingException {
        return backend.getBatteryLevel();
    }
}
