package controllers;

import java.util.List;

public class Group extends Good {

    private String uuid;

    private String code;

    private String name;

    private String parentUuid;

    private Boolean group;

    public Group() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Good{" +
                "uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", parentUuid='" + parentUuid + '\'' +
                ", group=" + group +
                '}';
    }
}
