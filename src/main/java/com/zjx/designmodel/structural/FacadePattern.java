package com.zjx.designmodel.structural;

/**
 * 外观模式
 * 外观模式又叫门面模式，提供了统一得接口，用来访问子系统中的一群接口。
 *
 * 适用于：
 *
 * 子系统越来越复杂，增加外观模式提供简单接口调用；
 * 构建多层系统结构，利用外观对象作为每层的入口，简化层间调用。
 *
 * 优点：
 * 1.简化了调用过程，无需了解深入子系统；
 * 2.减低耦合度；
 * 3.更好的层次划分；
 * 4.符合迪米特法则。
 *
 * 缺点：
 * 1.增加子系统，拓展子系统行为容易引入风险；
 * 2.不符合开闭原则。
 *
 * 举个订外卖的例子。
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/11/10 17:31
 * @Version V1.0
 **/
public class FacadePattern {

    public static void main(String[] args) {
        Takeaway takeaway = new Takeaway();
        takeaway.setName("泡椒 ");

        TakeawayService takeawayService = new TakeawayService();
        takeawayService.takeOrder(takeaway);
    }
}

class Takeaway {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class OrderService {

    public boolean placeAnOrder(Takeaway takeaway) {
        System.out.println(takeaway.getName() + "下单成功");
        return true;
    }
}

class PayService {

    public boolean pay(Takeaway takeaway) {
        System.out.println("商品" + takeaway.getName() + "支付成功");
        return true;
    }
}

class DeliveryService {

    public void delivery(Takeaway takeaway) {
        System.out.println(takeaway.getName() + "已由骑手XX接单，订单派送中");
    }
}

class TakeawayService {

    private OrderService orderService = new OrderService();
    private PayService payService = new PayService();
    private DeliveryService deliveryService = new DeliveryService();

    public void takeOrder(Takeaway takeaway) {
        if (orderService.placeAnOrder(takeaway)) {
            if (payService.pay(takeaway)) {
                deliveryService.delivery(takeaway);
            }
        }
    }
}