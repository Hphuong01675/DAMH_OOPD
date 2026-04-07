package ute.fit.model;

public class CheeseFoamCreator implements ToppingCreator
{

    @Override
    public boolean Support(String typeTopping) {
        return typeTopping.equalsIgnoreCase("CheeseFoam");
    }

    @Override
    public Product CreateTopping(Product topping) {
        return new CheeseFoam(topping, this.getName(),this.getPrice());
    }
    
    @Override
    public String getName()
    {
    	return "CheeseFoam";
    }
    
    @Override 
    public double getPrice()
    {
    	return 9000;
    }
    
}
