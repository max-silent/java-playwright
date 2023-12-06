package drafts;

public class SingletonThreadSafe {

    private static volatile SingletonThreadSafe instance;
//    private static Object mutex = new Object();

    private SingletonThreadSafe() {
    }

    public static SingletonThreadSafe getInstance() {
        SingletonThreadSafe result = instance;
        if (result == null) {
            synchronized (SingletonThreadSafe.class) {
//            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new SingletonThreadSafe();
            }
        }
        return result;
    }
}
