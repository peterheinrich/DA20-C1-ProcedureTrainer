package ch.peterheinrich.flightsim.s5web;

import org.springframework.stereotype.Service;

import com.fazecast.jSerialComm.SerialPort;

@Service
public class SerialServer {
    private SerialPort port;

    public void openConnection() {
        port = SerialPort.getCommPort("/dev/ttyS0");
		port.setBaudRate(9600);
		port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
		port.setNumDataBits(8);
		port.setParity(SerialPort.NO_PARITY);
		port.setNumStopBits(0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 100, 0);

		if(!port.openPort()) {
			System.err.println("Could not open COM port!");
			System.exit(-1);
		}
    }

    public String read() {
        StringBuffer sb = new StringBuffer();
        Character lastchar = 0x0;
        while(lastchar != '\n') {
            byte[] readBuffer = new byte[port.bytesAvailable()];
            int numRead = port.readBytes(readBuffer, readBuffer.length);
            if(numRead > 0) {
                lastchar = (char) readBuffer[numRead - 1];
                for (byte b : readBuffer) {
                    sb.append(b);
                }
            }
            else {
                return sb.toString();
            }
        }
        return sb.toString();
    }
    
    public void write(String s) {
        port.writeBytes(s.getBytes(), s.getBytes().length);
    }

    public void closeConnection() {
        port.closePort();
        port = null;
    }
}
