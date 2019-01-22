package seen.jackiechan.mim.testforadl;

import java.io.Serializable;

public class Config implements Serializable{

    public int id;
    public String name, value;

    public Config() {
    }

    public Config(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Config(int id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        if (this.value == null)
            return "";
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
