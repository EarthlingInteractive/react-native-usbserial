
class UsbSerialDevice {

    constructor(UsbSerialModule, UsbSerialDevNativeObject) {
        this.UsbSerialModule = UsbSerialModule;
        this.id = UsbSerialDevNativeObject.id;
    }

    writeAsync(value = "") {
        return this.UsbSerialModule.writeInDeviceAsync(this.id, value);
    }

    readAsync() {
        return this.UsbSerialModule.readFromDeviceAsync(this.id);
    }
}

module.exports = UsbSerialDevice;
