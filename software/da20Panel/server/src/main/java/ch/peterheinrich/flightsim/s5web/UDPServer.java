package ch.peterheinrich.flightsim.s5web;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class UDPServer {


    @Autowired
    private SerialServer rp2040;

    private DatagramSocket socket;
    private InetAddress address;

    public UDPServer() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("192.168.0.185");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 40001);
        try {
            socket.send(packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ServiceActivator(inputChannel = "udpChannel")
    public void handleMessage(Message<String> message) {
        String line = new String(message.getPayload());
        String[] data = line.split(",");
        if(data.length < 10) {
            System.out.println("Data truncated!");
            return;
        }
    }
}
