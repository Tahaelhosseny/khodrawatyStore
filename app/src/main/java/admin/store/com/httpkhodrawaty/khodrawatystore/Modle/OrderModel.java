package admin.store.com.httpkhodrawaty.khodrawatystore.Modle;

/**
 * Created by Taha on 3/25/2018.
 */

public class OrderModel
{
    String id;
    String time;
    String name;
    String phone;
    String email;
    String price;
    String product;

    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderModel()
    {
    }

    public OrderModel(String id, String time, String name, String phone, String email, String price, String product , String status)
    {
        this.id = id;
        this.time = time;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.price = price;
        this.product = product;
        this.status = status ;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getProduct()
    {
        return product;
    }

    public void setProduct(String product)
    {
        this.product = product;
    }
}
