package ph.roadtrip.roadtrip;

public class UrlBean {

    private String ROOT = "http://10.117.225.138/api/finascash/";

    private String loginURL = ROOT + "login.php";

    private String registerURL = ROOT + "register.php";

    private String scanQRSend = ROOT + "scan_qr_receive.php?userID=";


    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public String getScanQRSend() {
        return scanQRSend;
    }

    public void setScanQRSend(String scanQRSend) {
        this.scanQRSend = scanQRSend;
    }

    public String getRegisterURL() {
        return registerURL;
    }

    public void setRegisterURL(String registerURL) {
        this.registerURL = registerURL;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public void setLoginURL(String loginURL) {
        this.loginURL = loginURL;
    }


}
