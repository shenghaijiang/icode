package ${model.packageName}.interceptor;

/**
 * Created by Generator on ${(.now?string("yyyy-MM-dd HH:mm:ss"))!}
 */
public class LoginToken {

    private int userId;
    private String userName;
    private  String appToken;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}
