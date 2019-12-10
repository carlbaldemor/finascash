package ph.roadtrip.roadtrip;

public class UrlBean {

    private String ROOT = "http://10.117.225.138/api/finascash/";

    private String loginURL = ROOT + "login.php";

    private String registerURL = ROOT + "register.php";


    /**
     *
     * GETTERS AND SETTERS
     *
     */

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
