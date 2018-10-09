package db.entity;

public class Client {
    long client_id;
    String token;
    String companyName;
    String uuid;
    boolean isEnabled;
    boolean wasAlert;

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public long getId() {
        return client_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (token != null ? !token.equals(client.token) : client.token != null) return false;
        return uuid != null ? uuid.equals(client.uuid) : client.uuid == null;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
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


    public boolean isWasAlert() {
        return wasAlert;
    }

    public void setWasAlert(boolean wasAlert) {
        this.wasAlert = wasAlert;
    }
}
