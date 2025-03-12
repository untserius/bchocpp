package com.axxera.ocpp.message;

public class ChargePointErrorCode {
	
	public static final String ConnectorLockFailure="Failure to lock or unlock connector";
	public static final String EVCommunicationError="Communication failure with the vehicle, might be" + 
			"Mode 3 or other communication protocol" + 
			"problem. This is not a real error in the sense that" + 
			"the Charge Point doesn’t need to go to the faulted" + 
			"state. Instead, it should go to the SuspendedEVSE" + 
			"state";
	public static final String GroundFailure="Ground fault circuit interrupter has been" + 
			"activated";
	public static final String HighTemperature="Temperature inside Charge Point is too high";
	public static final String InternalError="Error in internal hard- or software component";
	public static final String LocalListConflict="The authorization information received from the" + 
			"Central System is in conflict with the" + 
			"LocalAuthorizationList";
	public static final String NoError="No error to report";
	public static final String OtherError="Other type of error. More information in" + 
			"vendorErrorCode";
	public static final String OverCurrentFailure="Over current protection device has tripped";
	public static final String OverVoltage="Voltage has risen above an acceptable level";
	public static final String PowerMeterFailure="Failure to read power meter";
	public static final String PowerSwitchFailure="Failure to control power switch";
	public static final String ReaderFailure="Failure with idTag reader";
	public static final String ResetFailure="Unable to perform a reset";
	public static final String UnderVoltage="Voltage has dropped below an acceptable level";
	public static final String WeakSignal="Wireless communication device reports a weak" + 
			"signal";

}
