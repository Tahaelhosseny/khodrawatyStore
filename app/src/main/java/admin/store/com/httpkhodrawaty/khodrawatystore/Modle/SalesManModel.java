package admin.store.com.httpkhodrawaty.khodrawatystore.Modle;

/**
 * Created by Taha on 3/23/2018.
 */

public class SalesManModel
{
    String name ;
    String city;
    String city_id;
    String email;
    String password;
    String phone;
    String id;

    CityModel cityModel ;


    public SalesManModel(String name, String city, String city_id, String email, String password, String phone, String id)
    {
        this.name = name;
        this.city = city;
        this.city_id = city_id;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.id = id;

        cityModel = new CityModel();
        cityModel.setId(city_id);
        cityModel.setName(city);
    }


    public SalesManModel()
    {
    }


    public String getName()
    {
        return name;
    }


    public CityModel getCityModel()
    {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel)
    {
        this.cityModel = cityModel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
