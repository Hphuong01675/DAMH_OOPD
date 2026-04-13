package ute.fit.model;

public class PearlCreator implements ToppingCreator
{
    @Override
    public boolean Support(String typeTopping) {
        return typeTopping.equalsIgnoreCase("Pearl");
    }

    @Override
    public Product CreateTopping(Product topping) {
        return new Pearl(topping, this.getName(),this.getPrice());
    }
    
    @Override
    public String getName()
    {
    	return "Pearl";
    }
    
    @Override 
    public double getPrice()
    {
    	return 5000;
    }
    
}
