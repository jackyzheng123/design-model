package com.zjx.designmodel.creational;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 原型模式
 * 原型实例指定创建对象的种类，通过拷贝这些原型创建新的对象。
 * <p>
 * 适用于：
 * 1.类初始化消耗较多资源；
 * 2.循环体中生产大量对象的时候。
 * <p>
 * 优点：
 * 1.原型模式性能比直接new一个对象性能好；
 * 2.简化创建对象过程。
 * <p>
 * 缺点：
 * 1.对象必须重写Object克隆方法；
 * 2.复杂对象的克隆方法写起来较麻烦（深克隆、浅克隆）
 * <p>
 * 这种方式会比直接在循环中创建Student性能好。
 * <p>
 * 当对象包含引用类型属性时，需要使用深克隆，比如Student包含Date属性时：
 * <p>
 * 值得注意的是，克隆会破坏实现了Cloneable接口的单例对象。
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/11/9 16:39
 * @Version V1.0
 **/
public class PrototypePattern {

    public static void main(String[] args) {
        try {
            Student student = new Student();
            ArrayList<Student> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Student s = (Student) student.clone();
                s.setName("学生" + i);
                s.setAge(20 + i);

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, i);
                s.setBirthDay(calendar.getTime());
                list.add(s);
            }
            System.out.println(list);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

}

@Data
@ToString
class Student implements Cloneable {
    private String name;
    private int age;
    private Date birthDay; // 引用类型需要深克隆

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Student student = (Student) super.clone();
        // 引用类型深克隆
        if (student != null && student.getBirthDay() != null) {
            Date birthDay = (Date) student.getBirthDay().clone();
            student.setBirthDay(birthDay);
        }
        return student;
    }
}
