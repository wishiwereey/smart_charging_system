package abstraction;
import implementor.ChargingBackend;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
class FastChargingTest {
    @Test
    void shouldDelegateFastChargingToBackend() throws Exception {
        ChargingBackend backend = mock(ChargingBackend.class);
        when(backend.getBatteryLevel()).thenReturn(50);
        FastCharging charging = new FastCharging(backend);
        charging.charge();
        verify(backend).getBatteryLevel();
        verify(backend).setChargePower(100);
    }
}