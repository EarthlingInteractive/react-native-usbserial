package com.bmateam.reactnativeusbserial;

import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Promise;

import com.hoho.android.usbserial.driver.UsbSerialPort;

import java.io.IOException;

public class UsbSerialDevice {
    private UsbSerialPort port;
    private static final int SERIAL_TIMEOUT = 60*1000;

    public UsbSerialDevice(UsbSerialPort port) {
        this.port = port;
    }

    public void writeAsync(String value, Promise promise) {

        if (port != null) {

            try {
                port.write(value.getBytes(), SERIAL_TIMEOUT);

                promise.resolve(null);
            } catch (IOException e) {
                promise.reject(e);
            }

        } else {
            promise.reject(getNoPortErrorMessage());
        }
    }

    public void readAsync(Promise promise) {

        if (port != null) {
            try {
                byte buffer[] = new byte[16*1024]; // same as CommonUsbSerialPort
                int numBytesRead = port.read(buffer, SERIAL_TIMEOUT);

                WritableMap map = Arguments.createMap();
                map.putString("result", new String(buffer));

                promise.resolve(map);
            } catch (IOException e) {
                String stackTrace = Log.getStackTraceString(e);
                promise.reject(stackTrace, e);
            }
        } else {
            promise.reject("No port present for the UsbSerialDevice instance", getNoPortErrorMessage());
        }
    }

    private Exception getNoPortErrorMessage() {
        return new Exception("No port present for the UsbSerialDevice instance");
    }
}
