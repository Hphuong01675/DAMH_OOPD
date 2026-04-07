package ute.fit.model;

import java.util.ArrayList;
import java.util.List;

public class ToppingFactory {

    private static List<ToppingCreator> creators = new ArrayList<>();

    public static void register(ToppingCreator creator)
    {
        creators.add(creator);
    }

    public static Product createTopping(String name, Product product) {
        for (ToppingCreator creator : creators) {
            if (creator.Support(name)){
                return creator.CreateTopping(product);
            }
        }
        throw new IllegalArgumentException("Topping not found: " + name);
    }


    public static List<ToppingDTO> getAllToppings() {
        List<ToppingDTO> list = new ArrayList<>();
        int id = 1;

        for (ToppingCreator creator : creators) {
            list.add(new ToppingDTO(
                    id++,
                    creator.getName(),
                    creator.getPrice()
            ));
        }

        return list;
    }
}
