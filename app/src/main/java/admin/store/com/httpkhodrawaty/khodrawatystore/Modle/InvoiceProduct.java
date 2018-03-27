package admin.store.com.httpkhodrawaty.khodrawatystore.Modle;

/**
 * Created by Taha on 3/26/2018.
 */

public class InvoiceProduct
{
    String name ;
    String quantity ;
    String price ;


    public InvoiceProduct()
    {
    }

    public InvoiceProduct(String name, String quantity, String price)
    {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
