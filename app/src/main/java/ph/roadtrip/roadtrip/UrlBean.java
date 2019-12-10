package ph.roadtrip.roadtrip;

public class UrlBean {

    private String ROOT = "http://10.117.225.138/api/finascash/";

    private String loginURL = ROOT + "login.php";


    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public String getLoginURL() {
        return loginURL;
    }

    public void setLoginURL(String loginURL) {
        this.loginURL = loginURL;
    }


}
