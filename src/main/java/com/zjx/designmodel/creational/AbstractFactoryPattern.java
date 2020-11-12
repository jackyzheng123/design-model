package com.zjx.designmodel.creational;

/**
 * 抽象工厂模式
 *
 * 抽象工厂模式（Abstract factory pattern）提供了一系列相关或者相互依赖的对象的接口，关键字是“一系列”。
 *
 * 优点：
 * 1.具体产品从客户端代码中抽离出来，解耦。
 * 2.将一个系列的产品族统一到一起创建。
 *
 * 缺点：拓展新的功能困难，需要修改抽象工厂的接口；
 *
 * 综上所述，抽象工厂模式适合那些功能相对固定的产品族的创建。
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/11/6 17:10
 * @Version V1.0
 **/
public class AbstractFactoryPattern {

    public static void main (String[] args){
        PhoneFactory phoneFactory = new PhoneFactoryImpl();
        HuaWei huaWei = phoneFactory.getHuaWei();
        XiaoMi xiaoMi = phoneFactory.getXiaoMi();
        huaWei.call();
        xiaoMi.call();
    }
}

/**
 * 手机接口
 */
interface Phone{

    void call();
}

/**
 * 华为手机抽象类
 */
abstract class HuaWei implements Phone{
    @Override
    public abstract void call();
}

/**
 * 小米手机抽象类
 */
abstract class XiaoMi implements Phone{
    @Override
    public abstract void call();
}

/**
 * HuaWeiP40类
 */
class HuaWeiP40 extends HuaWei {

    @Override
    public void call() {
        System.out.println("HuaWeiP40 call");
    }
}

/**
 * 小米8类
 */
class XiaoMi8 extends XiaoMi {

    @Override
    public void call() {
        System.out.println("XiaoMi8 call");
    }
}

/**
 * 手机抽象工厂
 */
interface PhoneFactory{

    HuaWei getHuaWei();
    XiaoMi getXiaoMi();
}

/**
 * 手机具体工厂
 */
class PhoneFactoryImpl implements PhoneFactory{

    @Override
    public HuaWei getHuaWei() {
        return new HuaWeiP40();
    }

    @Override
    public XiaoMi getXiaoMi() {
        return new XiaoMi8();
    }
}
