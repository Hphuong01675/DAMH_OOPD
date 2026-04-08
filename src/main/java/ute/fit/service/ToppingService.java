package ute.fit.service;

import ute.fit.model.ToppingDTO;
import java.util.*;

public class ToppingService {

    public List<ToppingDTO> findAll() {
        List<ToppingDTO> list = new ArrayList<>();
        list.add(new ToppingDTO(1, "Pearl", 5000));
        list.add(new ToppingDTO(2, "CheeseFoam", 9000));
        list.add(new ToppingDTO(3, "Caramel", 9000));
        list.add(new ToppingDTO(4, "Jelly", 4000));
        return list;
    }
}