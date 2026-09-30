package adapter;
import adaptee.LegacyBatteryController;
import implementor.ChargingException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class LegacyChargerAdapterTest {
    @Test
    void shouldTranslateWattsToMilliamps() throws Exception {
        LegacyBatteryController legacy = mock(LegacyBatteryController.class);
        when(legacy.configureCurrent("BAT-001", 5000.0)).thenReturn(LegacyBatteryController.SUCCESS);
        LegacyChargerAdapter adapter = new LegacyChargerAdapter(legacy, "BAT-001");
        adapter.setChargePower(100);
        verify(legacy).configureCurrent("BAT-001", 5000.0);
    }
    @Test
    void shouldTranslateBatteryLevel() throws Exception {
        LegacyBatteryController legacy = mock(LegacyBatteryController.class);
        when(legacy.readChargeRatio("BAT-001")).thenReturn(0.75);
        LegacyChargerAdapter adapter = new LegacyChargerAdapter(legacy, "BAT-001");
        int batteryLevel = adapter.getBatteryLevel();
        assertEquals(75, batteryLevel);
    }
    @Test
    void shouldTranslateCurrentTooHighError() {
        LegacyBatteryController legacy = mock(LegacyBatteryController.class);
        when(legacy.configureCurrent("BAT-001", 10000.0)).thenReturn(LegacyBatteryController.ERROR_CURRENT_TOO_HIGH);
        LegacyChargerAdapter adapter = new LegacyChargerAdapter(legacy, "BAT-001");
        ChargingException exception = assertThrows(ChargingException.class, () -> adapter.setChargePower(200));
        assertEquals("Requested charging power is too high for the legacy battery", exception.getMessage());
    }
    @Test
    void shouldTranslateBatteryNotFoundError() {
        LegacyBatteryController legacy = mock(LegacyBatteryController.class);
        when(legacy.configureCurrent("UNKNOWN", 5000.0)).thenReturn(LegacyBatteryController.ERROR_BATTERY_NOT_FOUND);
        LegacyChargerAdapter adapter = new LegacyChargerAdapter(legacy, "UNKNOWN");
        ChargingException exception = assertThrows(ChargingException.class, () -> adapter.setChargePower(100));
        assertEquals("Battery was not found by the legacy charging system", exception.getMessage());
    }
    @Test
    void shouldTranslateBatteryLevelReadFailure() {
        LegacyBatteryController legacy = mock(LegacyBatteryController.class);
        when(legacy.readChargeRatio("BAT-001")).thenReturn(-1.0);
        LegacyChargerAdapter adapter = new LegacyChargerAdapter(legacy, "BAT-001");
        ChargingException exception = assertThrows(ChargingException.class, adapter::getBatteryLevel);
        assertEquals("Battery level could not be read from the legacy charging system", exception.getMessage());
    }
}