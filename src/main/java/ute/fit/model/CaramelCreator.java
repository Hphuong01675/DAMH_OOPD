package ute.fit.model;

public class CaramelCreator implements ToppingCreator
{

    @Override
    public boolean Support(String typeTopping) {
        return typeTopping.equalsIgnoreCase("Caramel");
    }

    @Override
    public Product CreateTopping(Product topping) {
        return new Caramel(topping, this.getName(),this.getPrice());
    }
    
    @Override
    public String getName()
    {
    	return "Caramel";
    }
    
    @Override 
    public double getPrice()
    {
    	return 9000;
    }
    
}
