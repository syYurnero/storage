package top.quhailong.pan.request;

/**
 * 忘记密码修改密码请求实体
 *
 * @author: quhailong
 * @date: 2019/9/26
 */
public class ModifyPassRequest {
    private String username;
    private String password;
    private String verifyCode;
    private String publicKey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "ModifyPassRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
