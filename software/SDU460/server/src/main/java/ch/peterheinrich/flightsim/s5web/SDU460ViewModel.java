package ch.peterheinrich.flightsim.s5web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SDU460ViewModel {
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
    private float indicated_track_true_deg;
    private float latitude_deg;
    private float longitude_deg;
    private float rpm;
    private float oil_pressure_psi;
    private float oil_temperature_degf;
    private float cht_degf;
    private float egt_degf;
    private float amps;
    private float volts;
    private float indicated_speed_fpm;
    private String gmt_string = "";

    @Autowired
    private WebSocketController socket;

    public void setGroundspeed_kt(float groundspeed_kt) {
        if (this.groundspeed_kt == groundspeed_kt)
            return;
        this.groundspeed_kt = groundspeed_kt;
        socket.send(new DataDTO("groundspeed-kt", this.groundspeed_kt));
    }

    public void setTrue_speed_kt(float true_speed_kt) {
        if (this.true_speed_kt == true_speed_kt)
            return;
        this.true_speed_kt = true_speed_kt;
        socket.send(new DataDTO("true-speed-kt", this.true_speed_kt));
    }

    public void setIndicated_speed_kt(float indicated_speed_kt) {
        if (this.indicated_speed_kt == indicated_speed_kt)
            return;
        this.indicated_speed_kt = indicated_speed_kt;
        socket.send(new DataDTO("indicated-speed-kt", this.indicated_speed_kt));
    }

    public void setIndicated_altitude_ft(float indicated_altitude_ft) {
        if (this.indicated_altitude_ft == indicated_altitude_ft)
            return;
        this.indicated_altitude_ft = indicated_altitude_ft;
        socket.send(new DataDTO("indicated-altitude-ft", this.indicated_altitude_ft));
    }

    public void setSetting_hpa(float setting_hpa) {
        if (this.setting_hpa == setting_hpa)
            return;
        this.setting_hpa = setting_hpa;
        socket.send(new DataDTO("setting-hpa", this.setting_hpa));
    }

    public void setIndicated_pitch_deg(float indicated_pitch_deg) {
        if (this.indicated_pitch_deg == indicated_pitch_deg)
            return;
        this.indicated_pitch_deg = indicated_pitch_deg;
        socket.send(new DataDTO("indicated-pitch-deg", this.indicated_pitch_deg));

    }

    public void setIndicated_roll_deg(float indicated_roll_deg) {
        if (this.indicated_roll_deg == indicated_roll_deg)
            return;
        this.indicated_roll_deg = indicated_roll_deg;
        socket.send(new DataDTO("indicated-roll-deg", this.indicated_roll_deg));

    }

    public void setTemperature_degc(float temperature_degc) {
        if (this.temperature_degc == temperature_degc)
            return;
        this.temperature_degc = temperature_degc;
        socket.send(new DataDTO("temperature-degc", this.temperature_degc));
    }

    public void setIndicated_slip_skid(float indicated_slip_skid) {
        if (this.indicated_slip_skid == indicated_slip_skid)
            return;
        this.indicated_slip_skid = indicated_slip_skid;
        socket.send(new DataDTO("indicated-slip-skid", this.indicated_slip_skid));
    }

    public void setIndicated_heading_deg(float indicated_heading_deg) {
        if (this.indicated_heading_deg == indicated_heading_deg)
            return;
        this.indicated_heading_deg = indicated_heading_deg;
        socket.send(new DataDTO("indicated-heading-deg", this.indicated_heading_deg));
    }

    public void setIndicated_track_true_deg(float indicated_track_true_deg) {
        if (this.indicated_track_true_deg == indicated_track_true_deg)
            return;
        this.indicated_track_true_deg = indicated_track_true_deg;
        socket.send(new DataDTO("indicated-track-true-deg", this.indicated_track_true_deg));
    }

    public void setLatitude_deg(float latitude_deg) {
        if (this.latitude_deg == latitude_deg)
            return;

        this.latitude_deg = latitude_deg;
        socket.send(new DataDTO("latitude-deg", this.latitude_deg));

    }

    public void setLongitude_deg(float longitude_deg) {
        if (this.longitude_deg == longitude_deg)
            return;

        this.longitude_deg = longitude_deg;
        socket.send(new DataDTO("longitude-deg", this.longitude_deg));

    }

    public void setRpm(float rpm) {
        if (this.rpm == rpm)
            return;

        this.rpm = rpm;
        socket.send(new DataDTO("rpm", this.rpm));

    }

    public void setOil_pressure_psi(float oil_pressure_psi) {
        if (this.oil_pressure_psi == oil_pressure_psi)
            return;

        this.oil_pressure_psi = oil_pressure_psi;
        socket.send(new DataDTO("oil-pressure-psi", this.oil_pressure_psi));

    }

    public void setOil_temperature_degf(float oil_temperature_degf) {
        if (this.oil_temperature_degf == oil_temperature_degf)
            return;

        this.oil_temperature_degf = oil_temperature_degf;
        socket.send(new DataDTO("oil-temperature-degf", this.oil_temperature_degf));

    }

    public void setCht_degf(float cht_degf) {
        if (this.cht_degf == cht_degf)
            return;

        this.cht_degf = cht_degf;
        socket.send(new DataDTO("cht-degf", this.cht_degf));

    }

    public void setEgt_degf(float egt_degf) {
        if (this.egt_degf == egt_degf)
            return;

        this.egt_degf = egt_degf;
        socket.send(new DataDTO("egt-degf", this.egt_degf));

    }

    public void setAmps(float amps) {
        if (this.amps == amps)
            return;

        this.amps = amps;
        socket.send(new DataDTO("amps", this.amps));

    }

    public void setVolts(float volts) {
        if (this.volts == volts)
            return;

        this.volts = volts;
        socket.send(new DataDTO("volts", this.volts));

    }

    public void setIndicated_speed_fpm(float indicated_speed_fpm) {
        if (this.indicated_speed_fpm == indicated_speed_fpm)
            return;

        this.indicated_speed_fpm = indicated_speed_fpm;
        socket.send(new DataDTO("indicated-speed-fpm", this.indicated_speed_fpm));

    }

    public void setGmt_string(String gmt_string) {
        if (this.gmt_string.equals(gmt_string))
            return;
        this.gmt_string = gmt_string;
        socket.send(new DataDTO("gmt-string", this.gmt_string));
    }

}
