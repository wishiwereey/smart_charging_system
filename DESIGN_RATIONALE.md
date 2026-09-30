## Smart Charging System
## Problem
The project represents a smart charging system for different types of battery-powered devices.
The system supports different charging modes, such as Fast Charging and Battery Care Charging. These modes should work with different charging backends without creating a separate charging-mode class for every device type.

The system currently supports:
- Laptop charging
- Power station charging
- A legacy battery controller

The legacy battery controller has an incompatible API, so it cannot be used directly by the charging modes.

## Bridge Pattern
The Bridge pattern separates charging modes from charging implementations.
ChargingMode is the Abstraction. Its refined abstractions are:
- FastCharging
- BatteryCareCharging

ChargingBackend is the Implementor interface. Its implementations are:
- LaptopChargingBackend
- PowerStationChargingBackend
- LegacyChargerAdapter

ChargingMode depends only on ChargingBackend. Because of this, the charging modes do not need to know which concrete backend is being used.
For example, FastCharging can work with a laptop, power station, or legacy battery through the same ChargingBackend interface.

## Adapter Pattern
LegacyBatteryController cannot implement the ChargingBackend contract directly because its API is different.
The expected interface contains:
setChargePower(int watts)
getBatteryLevel()

The legacy controller instead provides:
configureCurrent(String batteryId, double milliamps)
readChargeRatio(String batteryId)

The incompatibility is not only in method names. The methods use different parameters and data representations. Charging power is provided in watts in ChargingBackend, while the legacy controller expects current in milliamps. Battery level is represented as an integer percentage by ChargingBackend, while the legacy controller returns a double ratio.
The failure mechanisms are also different. LegacyBatteryController uses error codes and a sentinel value, while ChargingBackend uses ChargingException.
LegacyChargerAdapter solves these differences. It implements ChargingBackend, converts watts to milliamps, converts the battery ratio to a percentage, and translates legacy failures into ChargingException.
Therefore, the Adapter plugs directly into the Implementor side of the Bridge.

## Why Bridge Alone Is Not Enough
Bridge separates charging modes from charging backends, but it does not solve an incompatible API.
LegacyBatteryController cannot be used as a ChargingBackend because its method signatures, parameters, units, return values, and failure mechanism are different.
The Adapter is required to make the legacy controller compatible with the Bridge Implementor interface.

## Why Adapter Alone Is Not Enough
Adapter can make LegacyBatteryController compatible with another interface, but it does not solve the problem of independently varying charging modes and charging implementations.
Without Bridge, the system could require combinations such as laptop fast charging, laptop battery-care charging, power-station fast charging, and so on.
Bridge allows charging modes and charging backends to vary independently.

## Required Complexity Module
The selected complexity module is Dynamic Implementor Selection.
The device type is received at runtime from the program input. ChargingBackendSelector uses this value to select the appropriate ChargingBackend.
For example:
- LAPTOP selects LaptopChargingBackend.
- POWER_STATION selects PowerStationChargingBackend.
- LEGACY_BATTERY selects LegacyChargerAdapter.

The charging mode receives the selected implementation through the ChargingBackend interface and does not need to know its concrete class.

## Open/Closed Principle
The main Bridge structure is open for extension.
A new charging mode can be added by creating another subclass of ChargingMode without changing the existing charging mode classes.
A new charging backend can implement ChargingBackend without changing ChargingMode, FastCharging, or BatteryCareCharging.
The ChargingBackendSelector uses registered backend factories instead of containing a switch statement for concrete backend classes.
This keeps the charging logic separated from implementation selection.

## Limitation
One limitation is that LegacyChargerAdapter uses a fixed battery voltage of 20 volts when converting watts to milliamps.
In a real charging system, different batteries could use different voltages. A future version could receive voltage from configuration or battery information instead of using a fixed value.