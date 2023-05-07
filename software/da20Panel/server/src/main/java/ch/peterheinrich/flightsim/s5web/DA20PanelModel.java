package ch.peterheinrich.flightsim.s5web;

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
        sb.append(pumpIn?"1":"0");
        sb.append(",");
        sb.append(generatorIn?"1":"0");
        sb.append(",");
        sb.append(batteryIn?"1":"0");
        sb.append(",");
        sb.append(flapsIn);
        sb.append("\n");
        return sb.toString();
    }

    public void fromBytes(int d0_7, int d8_15, int d16_23, int d24_31) {
        d0_7 = d0_7 & 0xFF;
        d8_15 =d8_15 & 0xFF;
        d16_23 =d16_23 & 0xFF;
        d24_31 = d24_31 & 0xFF;

        epuIn = !(((d0_7 >> 1) & 0x01) == 1);
        landingLightsIn = !(((d0_7 >> 2) & 0x01) == 1);
        taxiLightsIn = !(((d0_7 >> 3) & 0x01) == 1);
        navLightsIn = !(((d0_7 >> 4) & 0x01) == 1);
        strobeIn = !(((d0_7 >> 5) & 0x01) == 1);
        boolean mag0 = !(((d0_7 >> 7) & 0x01) == 1);
        boolean mag1 = !(((d8_15 >> 0) & 0x01) == 1);
        boolean mag2 = !(((d8_15 >> 1) & 0x01) == 1);
        boolean mag3 = !(((d8_15 >> 2) & 0x01) == 1);

        if(mag0) magnetosIn = 0;
        else if(mag1) magnetosIn = 1;
        else if(mag2) magnetosIn = 2;
        else if(mag3) magnetosIn = 3;
        else magnetosIn = 3;

        starterIn = !(((d8_15 >> 3) & 0x01) == 1);
        avionicsIn = !(((d8_15 >> 5) & 0x01) == 1);
        pumpIn = !(((d8_15 >> 6) & 0x01) == 1);
        generatorIn = !(((d8_15 >> 7) & 0x01) == 1);
        batteryIn = generatorIn;

        boolean flaps_down = !(((d16_23 >> 1) & 0x01) == 1);
        boolean flaps_up = !(((d16_23 >> 0) & 0x01) == 1);

        if(!flaps_up && ! flaps_down) flapsIn = (float)0.375;
        else if(flaps_down) flapsIn = (float)1.0;
        else if(flaps_up) flapsIn = (float)0;
        else flapsIn = (float)0.375;

    }
    
    

}
