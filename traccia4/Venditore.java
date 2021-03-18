package it.unical.dimes.reti.traccia4;

public class Venditore {

    private String gAddress = "230.0.0.1";
    private static final int gPort = 6789;

    public Venditore(String gAddress) {
        this.gAddress = gAddress;
    }

    public String getAddress() {
        return gAddress;
    }

    public void setAddress(String gAddress) {
        this.gAddress = gAddress;
    }

    public static int getPort() {
        return gPort;
    }


}
