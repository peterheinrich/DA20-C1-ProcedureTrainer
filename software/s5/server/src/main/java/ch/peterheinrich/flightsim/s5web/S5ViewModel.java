package ch.peterheinrich.flightsim.s5web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class S5ViewModel {
    private float groundspeed_kt;
    private float true_speed_kt;
    private float indicated_speed_kt;
    private float indicated_altitude_ft;
    private float setting_hpa;
    private float indicated_pitch_deg;
    private float indicated_roll_deg;
    private float temperature_degc;
    private float indicated_slip_skid;
    private float indicated_heading_deg;

    @Autowired
	private WebSocketController socket;


    public void setGroundspeed_kt(float groundspeed_kt) {
        if(this.groundspeed_kt == groundspeed_kt) return;
        this.groundspeed_kt = groundspeed_kt;
        socket.send(new DataDTO("groundspeed-kt",this.groundspeed_kt));

    }
    public void setTrue_speed_kt(float true_speed_kt) {
        if(this.true_speed_kt == true_speed_kt) return;
        this.true_speed_kt = true_speed_kt;
        socket.send(new DataDTO("true-speed-kt",this.true_speed_kt));

    }
    public void setIndicated_speed_kt(float indicated_speed_kt) {
        if(this.indicated_speed_kt == indicated_speed_kt) return;
        this.indicated_speed_kt = indicated_speed_kt;
        socket.send(new DataDTO("indicated-speed-kt",this.indicated_speed_kt));
    }
    public void setIndicated_altitude_ft(float indicated_altitude_ft) {
        if(this.indicated_altitude_ft == indicated_altitude_ft) return;
        this.indicated_altitude_ft = indicated_altitude_ft;
        socket.send(new DataDTO("indicated-altitude-ft",this.indicated_altitude_ft));
    }
    public void setSetting_hpa(float setting_hpa) {
        if(this.setting_hpa == setting_hpa) return;
        this.setting_hpa = setting_hpa;
        socket.send(new DataDTO("setting-hpa",this.setting_hpa));
    }
    public void setIndicated_pitch_deg(float indicated_pitch_deg) {
        if(this.indicated_pitch_deg == indicated_pitch_deg) return;
        this.indicated_pitch_deg = indicated_pitch_deg;
        socket.send(new DataDTO("indicated-pitch-deg",this.indicated_pitch_deg));

    }
    public void setIndicated_roll_deg(float indicated_roll_deg) {
        if(this.indicated_roll_deg == indicated_roll_deg) return;
        this.indicated_roll_deg = indicated_roll_deg;
        socket.send(new DataDTO("indicated-roll-deg",this.indicated_roll_deg));

    }
    public void setTemperature_degc(float temperature_degc) {
        if(this.temperature_degc == temperature_degc) return;
        this.temperature_degc = temperature_degc;
        socket.send(new DataDTO("temperature-degc",this.temperature_degc));
    }

    public void setIndicated_slip_skid(float indicated_slip_skid) {
        if(this.indicated_slip_skid == indicated_slip_skid) return;
        this.indicated_slip_skid = indicated_slip_skid;
        socket.send(new DataDTO("indicated-slip-skid",this.indicated_slip_skid));
    }
    public void setIndicated_heading_deg(float indicated_heading_deg) {
        if(this.indicated_heading_deg == indicated_heading_deg) return;
        this.indicated_heading_deg = indicated_heading_deg;
        socket.send(new DataDTO("indicated-heading-deg",this.indicated_heading_deg));    }
    
    
}
