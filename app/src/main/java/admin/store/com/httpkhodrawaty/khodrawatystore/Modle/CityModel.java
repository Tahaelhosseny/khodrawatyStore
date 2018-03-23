package admin.store.com.httpkhodrawaty.khodrawatystore.Modle;

import java.io.Serializable;

/**
 * Created by Taha on 3/23/2018.
 */

public class CityModel implements Serializable
{
    String id ;
    String name ;


    public CityModel(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public CityModel()
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

    @Override
    public String toString()
    {

        return name;
    }
}
