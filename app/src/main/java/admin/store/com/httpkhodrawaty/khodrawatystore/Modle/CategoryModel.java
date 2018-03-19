package admin.store.com.httpkhodrawaty.khodrawatystore.Modle;

/**
 * Created by Taha on 3/17/2018.
 */

public class CategoryModel
{
    String id    ;
    String name  ;
    String image ;
    String ServerName;
    String avilable;

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public CategoryModel(String id, String name, String image , String avilable)
    {
        this.id = id;
        this.name = name;
        this.image = image;
        this.ServerName = image;
        this.avilable = avilable;
    }

    public String isAvilable() {
        return avilable;
    }

    public void setAvilable(String avilable) {
        this.avilable = avilable;
    }

    public CategoryModel()
    {
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
}
