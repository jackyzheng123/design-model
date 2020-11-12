package com.zjx.designmodel.creational;

/**
 * 简单工厂方法模式
 *
 * 简单工厂模式严格意义上来说，并不属于设计模式中的一种，不过这里还是简单记录下。
 * 定义：由一个工厂对象决定创建出哪一种类型实例。客户端只需传入工厂类的参数，不关心创建过程。
 *
 * 优点：具体产品从客户端代码中抽离出来，解耦。
 *
 * 缺点：工厂类职责过重，增加新的类型时，得修改工程类得代码，违背开闭原则。
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/11/6 16:36
 * @Version V1.0
 **/
public class SimpleFactoryPattern {

    public static void main(String[] args) {
        FruitFactory fruitFactory = new FruitFactory();
        Fruit apple = fruitFactory.produce("apple");
        apple.eat();
    }
}

/**
 * 水果抽象类
 */
abstract class Fruit {

    public abstract void eat();
}

/**
 * 苹果类
 */
class Apple extends Fruit {

    @Override
    public void eat() {
        System.out.println("吃苹果");
    }
}

/**
 * 水果工厂类
 */
class FruitFactory {

    public Fruit produce(String name) {
        if ("apple".equals(name)) {
            return new Apple();
        }
        return null;
    }
}

