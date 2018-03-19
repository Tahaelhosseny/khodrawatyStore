package admin.store.com.httpkhodrawaty.khodrawatystore.Modle;

/**
 * Created by Taha on 3/18/2018.
 */

public class ItemModel
{
    String id    ;
    String name  ;
    String image ;
    String ServerName;
    String avilable;
    String Price ;
    String weight;
    String details ;


    public ItemModel(String id, String name, String image , String avilable , String price)
    {
        this.id = id;
        this.name = name;
        this.image = image;
        this.ServerName = image;
        this.avilable = avilable;
        this.Price = price;
       // this.details = details ;
        //this.weight = weight ;
    }


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public String getAvilable() {
        return avilable;
    }

    public void setAvilable(String avilable) {
        this.avilable = avilable;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
