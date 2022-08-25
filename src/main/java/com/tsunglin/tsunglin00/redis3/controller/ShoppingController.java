package com.tsunglin.tsunglin00.redis3.controller;

import java.util.List;

import com.tsunglin.tsunglin00.redis2.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsunglin.tsunglin00.redis3.model.Shopping;
import com.tsunglin.tsunglin00.redis3.repo.ShoppingDao;

@RestController
@RequestMapping("/shoppingItems")
public class ShoppingController {

    @Autowired
    private ShoppingDao shoppingdao;

    // curl -d '{"id":"1","name":"cookies","price":"20"}' -X POST http://localhost:28019/shoppingItems -H "Content-Type: application/json"
    @PostMapping
    public Shopping save(@RequestBody Shopping shopping) {
        return shoppingdao.save(shopping);
    }
    // http://localhost:28019/shoppingItems/save?id=1&name=cookies&price=20
    @GetMapping(value = "save")
    public String saveCity(int id,String name,Long price){
        Shopping shopping = new Shopping(id,name,price);
        shoppingdao.save(shopping);
        return "success";
    }
    // http://localhost:28019/shoppingItems
    @GetMapping
    public List<Shopping> getAllProducts() {
        return shoppingdao.findAll();
    }
    // http://localhost:28019/shoppingItems/1
    @GetMapping("/{id}")
    public Shopping findItems(@PathVariable int id) {
        return shoppingdao.findProductById(id);
    }
    // http://localhost:28019/shoppingItems/del/1
    @GetMapping("/del/{id}")
    public String remove2(@PathVariable int id)   {
        return shoppingdao.deleteProduct(id);
    }
    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id)   {
        return shoppingdao.deleteProduct(id);
    }



}
