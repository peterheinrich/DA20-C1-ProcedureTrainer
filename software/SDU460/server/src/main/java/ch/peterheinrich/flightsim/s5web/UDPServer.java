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
    private SDU460ViewModel viewModel;

    private DatagramSocket socket;
    private InetAddress address;

    public UDPServer() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("172.16.0.255");
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
        if(data.length < 22) {
            System.out.println("Data truncated!");
            return;
        }
        viewModel.setIndicated_speed_kt(Float.parseFloat(data[0]));
        viewModel.setTrue_speed_kt(Float.parseFloat(data[1]));
        viewModel.setGroundspeed_kt(Float.parseFloat(data[2]));
        viewModel.setIndicated_altitude_ft(Float.parseFloat(data[3]));
        viewModel.setSetting_hpa(Float.parseFloat(data[4]));
        viewModel.setIndicated_pitch_deg(Float.parseFloat(data[5]));
        viewModel.setIndicated_roll_deg(Float.parseFloat(data[6]));
        viewModel.setTemperature_degc(Float.parseFloat(data[7]));
        viewModel.setIndicated_slip_skid(Float.parseFloat(data[8]));
        viewModel.setIndicated_heading_deg(Float.parseFloat(data[9]));

        viewModel.setIndicated_track_true_deg(Float.parseFloat(data[10]));
        viewModel.setLatitude_deg(Float.parseFloat(data[11]));
        viewModel.setLongitude_deg(Float.parseFloat(data[12]));
        viewModel.setRpm(Float.parseFloat(data[13]));
        viewModel.setOil_pressure_psi(Float.parseFloat(data[14]));
        viewModel.setOil_temperature_degf(Float.parseFloat(data[15]));
        viewModel.setCht_degf(Float.parseFloat(data[16]));
        viewModel.setEgt_degf(Float.parseFloat(data[17]));
        viewModel.setAmps(Float.parseFloat(data[18]));
        viewModel.setVolts(Float.parseFloat(data[19]));
        viewModel.setIndicated_speed_fpm(Float.parseFloat(data[20]));
        try {
            System.out.println(data[21]);

            viewModel.setGmt_string(data[21]);
        }
        catch (Exception e) {

        }
    }
}
