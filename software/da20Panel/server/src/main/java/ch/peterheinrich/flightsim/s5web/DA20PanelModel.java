package ch.peterheinrich.flightsim.s5web;

import javax.swing.text.Position;

import lombok.Data;

@Data
public class DA20PanelModel {
    private boolean epuIn;
    private boolean landingLightsIn;
    private boolean taxiLightsIn;
    private boolean navLightsIn;
    private boolean strobeIn;
    private boolean mapIn;
    private int magnetosIn;
    private boolean starterIn;
    private boolean avionicsIn;
    private boolean pumpIn;
    private boolean generatorIn;
    private boolean batteryIn;
    private float flapsIn;

    private float flapsOut;
    private int 


    public String toMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(epuIn?"1":"0");
        sb.append(",");
        sb.append(landingLightsIn?"1":"0");
        sb.append(",");
        sb.append(taxiLightsIn?"1":"0");
        sb.append(",");
        sb.append(navLightsIn?"1":"0");
        sb.append(",");
        sb.append(strobeIn?"1":"0");
        sb.append(",");
        sb.append(magnetosIn);
        sb.append(",");
        sb.append(starterIn?"1":"0");
        sb.append(",");
        sb.append(avionicsIn?"1":"0");
        sb.append(",");
        sb.append(generatorIn?"1":"0");
        sb.append(",");
        sb.append(batteryIn?"1":"0");
        sb.append(",");
        sb.append(flapsIn);
        return sb.toString();
    }

    public void fromBytes(byte d0_7, byte d8_15, byte d16_23, byte d24_31) {
        epuIn = ((d0_7 & 0x01 << 1) >> 1) == 1;
        landingLightsIn = ((d0_7 & 0x01 << 2) >> 2) == 1;
        taxiLightsIn = ((d0_7 & 0x01 << 3) >> 3) == 1;
        navLightsIn = ((d0_7 & 0x01 << 4) >> 4) == 1;
        strobeIn = ((d0_7 & 0x01 << 5) >> 5) == 1;
        boolean mag0 = ((d0_7 & 0x01 << 7) >> 7) == 1;
        boolean mag1 = ((d8_15 & 0x01 << 0) >> 0) == 1;
        boolean mag2 = ((d8_15 & 0x01 << 1) >> 1) == 1;
        boolean mag3 = ((d8_15 & 0x01 << 2) >> 2) == 1;

        if(mag0) magnetosIn = 0;
        else if(mag1) magnetosIn = 1;
        else if(mag2) magnetosIn = 2;
        else if(mag3) magnetosIn = 3;
        else magnetosIn = 3;

        starterIn = ((d8_15 & 0x01 << 3) >> 3) == 1;
        avionicsIn = ((d8_15 & 0x01 << 5) >> 5) == 1;
        pumpIn = ((d8_15 & 0x01 << 6) >> 6) == 1;
        generatorIn = ((d8_15 & 0x01 << 7) >> 7) == 1;
        batteryIn = ((d8_15 & 0x01 << 7) >> 7) == 1;

        boolean flaps_down = ((d16_23 & 0x01 << 1) >> 1) == 1;
        boolean flaps_up = ((d16_23 & 0x01 << 0) >> 0) == 1;

        if(!flaps_up && ! flaps_down) flapsIn = (float)0.375;
        else if(flaps_down) flapsIn = (float)1.0;
        else if(flaps_up) flapsIn = (float)0;
        else flapsIn = (float)0.375;
    }
    
    

}
