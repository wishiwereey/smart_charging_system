package abstraction;
import implementor.ChargingBackend;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
class BatteryCareChargingTest {
    @Test
    void shouldDelegateBatteryCareChargingToBackend() throws Exception {
        ChargingBackend backend = mock(ChargingBackend.class);
        when(backend.getBatteryLevel()).thenReturn(50);
        BatteryCareCharging charging =
                new BatteryCareCharging(backend);
        charging.charge();
        verify(backend).getBatteryLevel();
        verify(backend).setChargePower(45);
    }
}