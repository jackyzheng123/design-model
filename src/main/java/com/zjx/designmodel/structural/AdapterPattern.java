package com.zjx.designmodel.structural;

/**
 * 适配器模式
 * 将一个类的接口转换为期望的另一个接口，使原本不兼容的类可以一起工作。
 *
 * 适用于：
 * 已存在的类，它的方法和需求不匹配时（方法结果相同或者相似）
 *
 * 优点:
 * 提高类的透明性和复用，现有的类复用但不需改变；
 * 目标类和适配器类解耦，提高程序拓展性；
 * 符合开闭原则。
 *
 * 缺点：
 * 适配器编写过程需要全面考虑，可能会增加系统的复杂性；
 * 降低代码可读性。
 *
 * 分为：类适配器模式和对象适配器模式。
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/11/11 17:45
 * @Version V1.0
 **/
public class AdapterPattern {

    public static void main (String[] args){
        Pie pie = new ApplePieAdaptor();
        pie.make();
    }
}

/**
 * 苹果类
 */
class Apple {

    public void addApple() {
        System.out.println("添加点苹果");
    }
}

/**
 * 水果派产品线
 */
interface Pie {

    void make();
}

/**
 * 要将Apple加入到Pie产品线，又不想修改Apple类的代码，则可以创建一个适配器ApplePieAdaptor
 */
class ApplePieAdaptor extends Apple implements Pie{
    @Override
    public void make() {
        System.out.println("制作一个苹果派产线");
        super.addApple();
    }
}

/**
 * 对象适配器模式
 */
class ApplePieAdaptor1 implements Pie{

    private Apple apple = new Apple();

    @Override
    public void make() {
        System.out.println("制作一个苹果派产线");
        apple.addApple();
    }
}
