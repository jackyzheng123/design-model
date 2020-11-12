package com.zjx.designmodel.creational;

/**
 * 单例模式
 * <p>
 * 单例模式目的是为了一个类只有一个实例。
 * <p>
 * 优点：
 * 1.内存中只有一个实例，减少了内存开销；
 * 2.可以避免对资源的多重占用；
 * 3.设置全局访问点，严格控制访问。
 * <p>
 * 缺点：
 * 1.没有接口，拓展困难。
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/11/9 15:35
 * @Version V1.0
 **/

import org.springframework.util.Assert;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

public class SingletonPattern {

    public static void main(String[] args) {
        int num = 10;
        CountDownLatch latch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            new Thread(()->{
                final Singleton instance = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + ": " + instance);
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 懒汉模式
 * <p>
 * 双重同步锁单例模式
 */
class Singleton {

    private static volatile Singleton singleton = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}

/**
 * 枚举单例模式
 */
enum EnumSingleton {

    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) throws Exception {

        // 测试枚举单例
        enumTest();
        // 测试序列化攻击
        enumSerialTest();
        // 测试反射攻击
        enumReflectTest();
    }

    private static void enumTest() {
        EnumSingleton instance = EnumSingleton.getInstance();
        instance.setData(new Object());
        EnumSingleton newInstance = EnumSingleton.getInstance();

        System.out.println(instance);
        System.out.println(newInstance);
        System.out.println(instance.getData());
        System.out.println(newInstance.getData());
    }

    /**
     * Java禁止通过反射创建枚举对象。
     * <p>
     * 正是因为枚举类型拥有这些天然的优势，所以用它创建单例是不错的选择，这也是Effective Java推荐的方式。
     */
    private static void enumReflectTest() {
        try {
            EnumSingleton instance = EnumSingleton.getInstance();
            Class<EnumSingleton> c = EnumSingleton.class;
            // 枚举类只包含一个(String,int)类型构造器
            Constructor<EnumSingleton> constructor = c.getDeclaredConstructor(String.class, int.class);
            constructor.setAccessible(true);
            EnumSingleton newInstance = constructor.newInstance("hello", 1);
            System.out.println(instance == newInstance);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 原理：跟踪ObjectInputStream#readObject源码，其中当反编译对象为枚举类型时，将调用readEnum方法：
     * name为枚举类里的枚举常量，对于线程来说它是唯一的，存在方法区，所以通过Enum.valueOf((Class)cl, name)方法得到的枚举对象都是同一个。
     */
    private static void enumSerialTest() {
        try {
            EnumSingleton instance = EnumSingleton.getInstance();
            instance.setData(new Object());
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("file"));
            outputStream.writeObject(instance);

            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("file"));
            EnumSingleton newInstance = (EnumSingleton) inputStream.readObject();

            System.out.println(instance);
            System.out.println(newInstance);
            System.out.println(instance == newInstance);

            System.out.println(instance.getData());
            System.out.println(newInstance.getData());
            System.out.println(instance.getData() == newInstance.getData());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 静态内部类单例模式
 * <p>
 * 1.JVM在类的初始化阶段会加Class对象初始化同步锁，同步多个线程对该类的初始化操作；
 * 2.静态内部类InnerClass的静态成员变量instance在方法区中只会有一个实例。
 * <p>
 * 在Java规范中，当以下这些情况首次发生时，A类将会立刻被初始化：
 * 1.A类型实例被创建；
 * 2.A类中声明的静态方法被调用；
 * 3.A类中的静态成员变量被赋值；
 * 4.A类中的静态成员被使用（非常量）
 */
class StaticInnerClassSingleton {

    private StaticInnerClassSingleton() {
    }

    private static class InnerClass {
        private static StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
    }

    public static StaticInnerClassSingleton getInstance() {
        return InnerClass.instance;
    }
}

/**
 * 饿汉模式
 * <p>
 * 这种模式在类加载的时候就完成了初始化，所以并不存在线程安全性问题；但由于不是懒加载，饿汉模式不管需不需要用到实例都要去创建实例，如果创建了不使用，则会造成内存浪费。
 */
class HungrySingleton implements Serializable {

    private static final long serialVersionUID = -9109204420418751311L;

    private final static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
        // 添加判断逻辑来防御反射更改私有构造器权限
        if (instance != null) {
            throw new RuntimeException("forbidden");
        }
    }

    public static HungrySingleton getInstance() {
        return instance;
    }

    // 新增
    private Object readResolve() {
        return instance;
    }

    /**
     * 演示
     *
     * @param args
     */
    public static void main(String[] args) {
        // 序列化破坏单例模式
        serialTest();
        // 反射破坏单例模式
        reflectTest();

    }

    /**
     * 反射破坏单例模式
     * <p>
     * 输出：
     * com.zjx.designmodel.creational.HungrySingleton@63961c42
     * com.zjx.designmodel.creational.HungrySingleton@c4437c4
     * false
     * 可以看到，我们通过反射破坏了私有构造器权限，成功创建了新的实例。
     * <p>
     * 对于这种情况，饿汉模式下的例子可以在构造器中添加判断逻辑来防御（懒汉模式的就没有办法了），比如修改HungrySingleton的代码如下所示
     */
    private static void reflectTest() {
        try {
            HungrySingleton instance = HungrySingleton.getInstance();
            // 反射创建实例
            Class<HungrySingleton> c = HungrySingleton.class;
            // 获取构造器
            Constructor<HungrySingleton> constructor = c.getDeclaredConstructor();
            // 打开构造器权限
            constructor.setAccessible(true);
            HungrySingleton newInstance = constructor.newInstance();

            System.out.println(instance);
            System.out.println(newInstance);
            System.out.println(instance == newInstance);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示序列化破坏单例模式
     * <p>
     * 输出：
     * com.zjx.designmodel.creational.HungrySingleton@63961c42
     * com.zjx.designmodel.creational.HungrySingleton@c4437c4
     * false
     * <p>
     * 可以看到，虽然是单例模式，但却成功创建出了两个不一样的实例，单例遭到了破坏。
     * 要让反序列化后的对象和序列化前的对象是同一个对象的话，可以在HungrySingleton里加上readResolve方法：
     * <p>
     * 输出:
     * com.zjx.designmodel.creational.HungrySingleton@63961c42
     * com.zjx.designmodel.creational.HungrySingleton@63961c42
     * true
     * <p>
     * 可以看到，这种方式最终反序列化出来的对象和序列化对象是同一个对象。但这种方式反序列化过程内部还是会重新创建HungrySingleton实例，只不过因为HungrySingleton类定义了readResolve方法（方法内部返回instance引用），反序列化过程会判断目标类是否定义了readResolve该方法，是的话则通过反射调用该方法。
     */
    private static void serialTest() {
        HungrySingleton instance = HungrySingleton.getInstance();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("file"));
            outputStream.writeObject(instance);

            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("file"));
            HungrySingleton newInstance = (HungrySingleton) inputStream.readObject();

            System.out.println(instance);
            System.out.println(newInstance);
            System.out.println(instance == newInstance);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}