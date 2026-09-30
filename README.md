## Smart Charging System
Smart Charging System project demonstrates the Bridge and Adapter design patterns.
The system supports different charging modes and different charging backends.  
A legacy battery controller with an incompatible API is integrated into the Bridge through an Adapter.

## Design Patterns
## Bridge
The Bridge pattern separates charging modes from charging backends.

Abstraction: ChargingMode
Refined Abstractions: FastCharging, BatteryCareCharging
Implementor:ChargingBackend
Concrete Implementors: LaptopChargingBackend, PowerStationChargingBackend, LegacyChargerAdapter

## Adapter
LegacyBatteryController has an incompatible interface and cannot be used directly as a ChargingBackend.
LegacyChargerAdapter implements ChargingBackend and adapts the legacy controller to the Bridge Implementor interface.

## Dynamic Implementor Selection
The charging backend is selected at runtime based on program input.

Supported devices: LAPTOP, POWER_STATION, LEGACY_BATTERY
Supported charging modes: FAST, CARE

## Project Structure
The project is divided into several parts based on their responsibilities.
- The abstraction package contains ChargingMode and its two implementations: FastCharging and BatteryCareCharging. 
These classes represent different charging modes.
- The target package contains the ChargingBackend interface, which acts as the Implementor in the Bridge pattern.
- The implementor package contains LaptopChargingBackend and PowerStationChargingBackend, 
which provide standard implementations of ChargingBackend.
- The adaptee package contains LegacyBatteryController, an existing class with an incompatible charging API.
- The adapter package contains LegacyChargerAdapter. It wraps LegacyBatteryController and implements ChargingBackend, 
allowing the legacy controller to be used as part of the Bridge.
- The selector package contains ChargingBackendSelector, which selects a charging backend at runtime based on the provided device type.
- The exception package contains ChargingException, which provides a common failure mechanism for charging backends.
- Main is the entry point of the application and connects the charging mode with the selected backend.
- The test directory contains JUnit 5 tests for the charging modes and the legacy adapter.