package ute.fit.model;
public class JellyCreator implements ToppingCreator
{

    @Override
    public boolean Support(String typeTopping) {
        return typeTopping.equalsIgnoreCase("Jelly");
    }

    @Override
    public Product CreateTopping(Product topping) {
        return new Jelly(topping, this.getName(),this.getPrice());
    }
    
    @Override
    public String getName()
    {
    	return "Jelly";
    }
    
    @Override 
    public double getPrice()
    {
    	return 4000;
    }
    
}
