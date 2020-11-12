package com.zjx.designmodel.structural;

/**
 * 装饰者模式
 * 在不改变原有对象的基础之上，将功能附加到对象上，提供了比继承更有弹性的替代方案。
 * <p>
 * 适用于：
 * 拓展一个类的功能；
 * 动态给对象添加功能，并且动态撤销。
 * <p>
 * 优点：
 * 继承的有力补充，不改变原有对象的情况下给对象拓展功能；
 * 通过使用不同的装饰类、不同的组合方式，实现不同的效果。
 * 符合开闭原则。
 * <p>
 * 缺点：
 * 增加程序复杂性；
 * <p>
 * 举个水果沙拉的例子。
 * <p>
 * 比如在点水果沙拉外卖时，可以往水果沙拉里加各种水果，价格也会相应的调整，要让程序支持不同水果自由组合，并计算相应的价格，则可以使用装饰者模式来完成。
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/11/11 17:07
 * @Version V1.0
 **/
public class DecoratorPattern {

    public static void main(String[] args) {
        // 点了份水果沙拉，并加了两份猕猴桃和一份西瓜，看看最终价格是多少？
//        AbstractFruitSalad fruitSalad = new FruitSalad();
//        fruitSalad = new KiwiDecorator(fruitSalad);
//        fruitSalad = new KiwiDecorator(fruitSalad);
//        fruitSalad = new WaterMelonDecorator(fruitSalad);
//        System.out.println(fruitSalad.remark() + "价格是：" + fruitSalad.price());

        //方式二写法
        AbstractFruitSalad fruitSalad = new FruitSalad();
        fruitSalad = new WaterMelonDecorator(new KiwiDecorator(new KiwiDecorator(fruitSalad)));

        System.out.println(fruitSalad.remark() + "价格是：" + fruitSalad.price());
    }
}

/**
 * 水果沙拉抽象类
 */
abstract class AbstractFruitSalad {

    public abstract String remark();

    public abstract int price();
}

/**
 * 抽象的装饰器
 */
class AbstractDecorator extends AbstractFruitSalad {

    private AbstractFruitSalad fruitSalad;

    public AbstractDecorator(AbstractFruitSalad fruitSalad) {
        this.fruitSalad = fruitSalad;
    }

    @Override
    public String remark() {
        return fruitSalad.remark();
    }

    @Override
    public int price() {
        return fruitSalad.price();
    }
}

/**
 * 水果沙拉类
 */
class FruitSalad extends AbstractFruitSalad {
    @Override
    public String remark() {
        return "水果（标准）\n";
    }

    @Override
    public int price() {
        return 9;
    }
}

/**
 * 猕猴桃装饰器
 */
class KiwiDecorator extends AbstractDecorator {

    public KiwiDecorator(AbstractFruitSalad fruitSalad) {
        super(fruitSalad);
    }

    @Override
    public String remark() {
        return super.remark() + "加份猕猴桃\n";
    }

    @Override
    public int price() {
        return super.price() + 2;
    }
}

/**
 * 西瓜装饰器
 */
class WaterMelonDecorator extends AbstractDecorator {

    public WaterMelonDecorator(AbstractFruitSalad fruitSalad) {
        super(fruitSalad);
    }

    @Override
    public String remark() {
        return super.remark() + "加份西瓜\n";
    }

    @Override
    public int price() {
        return super.price() + 3;
    }
}