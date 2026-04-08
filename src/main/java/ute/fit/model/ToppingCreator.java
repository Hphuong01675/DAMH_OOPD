package ute.fit.model;

interface ToppingCreator 
{
    boolean Support(String typeTopping);
    Product CreateTopping(Product topping);   
    String getName();
    double getPrice();
}
