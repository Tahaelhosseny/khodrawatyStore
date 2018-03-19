package admin.store.com.httpkhodrawaty.khodrawatystore.Modle;

/**
 * Created by Taha on 3/18/2018.
 */

public class Image
{
    String name ;
    String image ;
    Boolean checked = false ;
    String serverName;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    String path = "http://khodrawaty.com/uploads/";

    public Image(String name, String image)
    {
        this.name = name;
        this.serverName = image ;
        this.image = path+image;
    }

    public Image()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = path+image;
    }
}

