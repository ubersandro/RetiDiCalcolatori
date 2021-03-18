package it.unical.dimes.reti.restserver.model;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.io.Serializable;

public class Contact implements Serializable {

    private long id;
    private String name;
    private String surname;
    private String address;
    private String phone;

    public Contact() {}

    public Contact(String line) {
        JSONObject json = new JSONObject(line);
        this.id = json.getLong("id");
        this.name = json.getString("name");
        this.surname = json.getString("surname");
        this.phone = json.getString("phone");
        this.address = json.getString("address");
    }

    // Metodi Getter e setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return new Gson().toJson(this).toString();
    }
}