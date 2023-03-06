package ch.peterheinrich.flightsim.s5web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class UDPServer {


    @Autowired
    private S5ViewModel viewModel;


    @ServiceActivator(inputChannel = "udpChannel")
    public void handleMessage(Message<String> message) {
        String line = new String(message.getPayload());
        String[] data = line.split(",");
        if(data.length < 8) {
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
    }
}
