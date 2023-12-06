package drafts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainSingleton {
    public static void main(String[] args) {
        System.out.println("Hi.");
//        Singleton a = Singleton.getInstance();
//        Singleton b = Singleton.getInstance();
        SingletonThreadSafe a = SingletonThreadSafe.getInstance();
        SingletonThreadSafe b = SingletonThreadSafe.getInstance();
        System.out.print("The same instance is: ");
        System.out.println(a == b);

        List<String> list = new ArrayList<>();
        List<String> decorated = Collections.checkedList(list, String.class);
        decorated.add("2");
        list.add("3");
        System.out.println(list);
        System.out.println(decorated);
    }

}

////Creating annotation
//import java.lang.annotation.*;
//import java.lang.reflect.*;
//
//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.METHOD)
//@interface MyAnnotation{
//    int value() default 7;
//}
//
//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.TYPE)
//@interface MyAnnotation2{
//    int value() default 5;
//}
//
////Applying annotation
//@MyAnnotation2()
//class Hello{
//    public int field1 = 3;
//    String field2;
//    @MyAnnotation(value = 10)
//    public void sayHello(){System.out.println("hello annotation");}
//}
//
////Accessing annotation
//class Main{
//    public static void main(String[] args)throws Exception{
//
//        Hello h=new Hello();
//        Method m=h.getClass().getMethod("sayHello");
//
//        MyAnnotation myAnno=m.getAnnotation(MyAnnotation.class);
//        System.out.println("Method's annotation value is: "+myAnno.value());
//
//        MyAnnotation2 myAnno2 = h.getClass().getAnnotation(MyAnnotation2.class);
//        System.out.println("Fields's annotation value is: "+ myAnno2.value());
//    }}
//
