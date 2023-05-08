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

    private int flapsOut;
    private int trimOut;

    public String toMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(epuIn ? "1" : "0");
        sb.append(",");
        sb.append(landingLightsIn ? "1" : "0");
        sb.append(",");
        sb.append(taxiLightsIn ? "1" : "0");
        sb.append(",");
        sb.append(navLightsIn ? "1" : "0");
        sb.append(",");
        sb.append(strobeIn ? "1" : "0");
        sb.append(",");
        sb.append(magnetosIn);
        sb.append(",");
        sb.append(starterIn ? "1" : "0");
        sb.append(",");
        sb.append(avionicsIn ? "1" : "0");
        sb.append(",");
        sb.append(pumpIn ? "1" : "0");
        sb.append(",");
        sb.append(generatorIn ? "1" : "0");
        sb.append(",");
        sb.append(batteryIn ? "1" : "0");
        sb.append(",");
        sb.append(flapsIn);
        sb.append("\n");
        return sb.toString();
    }

    public void fromBytes(int d0_7, int d8_15, int d16_23, int d24_31) {
        d0_7 = d0_7 & 0xFF;
        d8_15 = d8_15 & 0xFF;
        d16_23 = d16_23 & 0xFF;
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

        if (mag0)
            magnetosIn = 0;
        else if (mag1)
            magnetosIn = 1;
        else if (mag2)
            magnetosIn = 2;
        else if (mag3)
            magnetosIn = 3;
        else
            magnetosIn = 3;

        starterIn = !(((d8_15 >> 3) & 0x01) == 1);
        avionicsIn = !(((d8_15 >> 5) & 0x01) == 1);
        pumpIn = !(((d8_15 >> 6) & 0x01) == 1);
        generatorIn = !(((d8_15 >> 7) & 0x01) == 1);
        batteryIn = generatorIn;

        boolean flaps_down = !(((d16_23 >> 1) & 0x01) == 1);
        boolean flaps_up = !(((d16_23 >> 0) & 0x01) == 1);

        if (!flaps_up && !flaps_down)
            flapsIn = (float) 0.375;
        else if (flaps_down)
            flapsIn = (float) 1.0;
        else if (flaps_up)
            flapsIn = (float) 0;
        else
            flapsIn = (float) 0.375;

    }

    public void fromMessage(String message) {
        String line = new String(message);
        line = line.trim();
        String[] data = line.split(",");
        if (data.length < 2) {
            System.out.println("Data truncated!");
            return;
        }

        flapsOut = Integer.parseInt(data[0]);
        trimOut = Integer.parseInt(data[1]);

        System.out.println("Flaps: " + flapsOut);
        System.out.println("Trim: " + trimOut);
    }

    public String toPanelBytes() {
        Integer b1 = 0;
        Integer b2 = 0;
        Integer b3 = 0;

        if (flapsOut == 0)
            b1 = 0x1;
        if (flapsOut > 0 && flapsOut < 375)
            b1 = 0x3;
        if (flapsOut == 375)
            b1 = 0x2;
        if (flapsOut > 375 && flapsOut < 1000)
            b1 = 0x6;
        if (flapsOut == 1000)
            b1 = 0x4;

        switch (trimOut) {
            case 0:
                b2 = 0x02;
                break;
            case 1:
                b2 = 0x01;
                break;
            case 2:
                b3 = 0x80;
                break;
            case 3:
                b3 = 0x40;
                break;
            case 4:
                b3 = 0x20;
                break;
            case 5:
                b3 = 0x10;
                break;
            case 6:
                b3 = 0x08;
                break;
            case 7:
                b3 = 0x04;
                break;
            case 8:
                b3 = 0x02;
                break;
            case 9:
                b3 = 0x01;
                break;
        }

        return (((b3 < 16) ? "0" : "") + Integer.toString(b3,16) + "," + ((b2 < 16) ? "0" : "") + Integer.toString(b2,16) + "," + ((b1 < 16) ? "0" : "") + Integer.toString(b1,16) + "\n");
    }

}
