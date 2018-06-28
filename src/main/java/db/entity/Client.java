package db.entity;

public class Client {
    long client_id;
    String token;
    String companyName;
    String uuid;

    public long getId() {
        return client_id;
    }

    public void setId(long id) {
        this.client_id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
