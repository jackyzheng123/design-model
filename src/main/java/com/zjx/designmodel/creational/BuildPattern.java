package com.zjx.designmodel.creational;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 建造者模式
 *
 * 建造者模式也称为生成器模式（Builder Pattern），将复杂对象的建造过程抽象出来（抽象类别），使这个抽象过程的不同实现方法可以构造出不同表现（属性）的对象。简单来说就是，相同的过程可以创建不同的产品。
 * 将复杂对象的建造过程抽象出来（抽象类别），使这个抽象过程的不同实现方法可以构造出不同表现（属性）的对象。
 * 简单来说就是，相同的过程可以创建不同的产品。
 *
 * 适用于：
 * 1.一个对象有非常复杂的内部结构（很多属性）
 * 2.想将复杂对象的创建和使用分离。
 *
 * 优点：
 * 1.封装性好，创建和使用分离
 * 2.拓展性好，建造类之间独立，一定程度上解耦。
 *
 *  缺点：
 * 1.产生多余的Builder对象；
 * 2.产品内部发生变化，建造者需要更改，成本较大。
 *
 * 这种用法和Lombok的@Builder注解效果是一样的
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/11/9 11:46
 * @Version V1.0
 **/

public class BuildPattern{

    public static void main (String[] args){
        ProductBuilder.Product product = new ProductBuilder().name("苹果").price(5.98).address("深圳").build();
        System.out.println(product);
    }
}

class ProductBuilder {

    private String name;
    private double price;
    private String address;

    public static class Product {

        private String name;
        private double price;
        private String address;

        public Product(){}

        public Product(ProductBuilder builder){
            this.name = builder.name;
            this.price = builder.price;
            this.address = builder.address;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }

    public ProductBuilder name(String name){
        this.name = name;
        return this;
    }

    public ProductBuilder price(double price){
        this.price = price;
        return this;
    }

    public ProductBuilder address(String address){
        this.address = address;
        return this;
    }

    public Product build(){
        return new Product(this);
    }

}

