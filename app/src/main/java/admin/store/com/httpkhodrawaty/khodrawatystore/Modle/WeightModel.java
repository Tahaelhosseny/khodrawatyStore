package admin.store.com.httpkhodrawaty.khodrawatystore.Modle;

import java.io.Serializable;

/**
 * Created by Taha on 3/22/2018.
 */

public class WeightModel implements Serializable
{
    String id ;
    String name ;


    public WeightModel(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public WeightModel(String name)
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
    public String toString() {

        return name;
    }
}
