package com.zjx.designmodel.creational;

/**
 * 工厂方法模式
 *
 * 为了解决简单工厂模式的缺点，诞生了工厂方法模式（Factory method pattern）。
 * 定义：定义创建对象的接口，让实现这个接口的类来决定实例化哪个类，工厂方法让类的实例化推迟到了子类进行。
 *
 * 优点：
 *
 * 具体产品从客户端代码中抽离出来，解耦。
 *
 * 加入新的类型时，只需添加新的工厂方法（无需修改旧的工厂方法代码），符合开闭原则。
 *
 * 缺点：类的个数容易过多，增加复杂度。
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/11/6 16:55
 * @Version V1.0
 **/
public class FactoryMethodPattern {

    public static void main(String[] args) {
        AnimalFactory animalFactory = new DogFactory();
        Animal animal = animalFactory.produce();
        animal.jump();
    }
}

/**
 * 动物抽象类
 */
abstract class Animal {

    public abstract void jump();
}

/**
 * 狗类
 */
class Dog extends Animal {

    @Override
    public void jump() {
        System.out.println("狗在跳");
    }
}

/**
 * 动物工厂抽象类
 */
abstract class AnimalFactory {

    public abstract Animal produce();
}

/**
 * 狗工厂类
 */
class DogFactory extends AnimalFactory {

    @Override
    public Animal produce() {
        return new Dog();
    }
}