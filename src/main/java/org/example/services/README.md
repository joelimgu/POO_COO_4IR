

The implementation of our singletons will be:
```java
public class Singleton {
    private static Singleton single;
 
    private Singleton() {
        /*Some state initialization*/
    }    
 
    public static Singleton getInstance() {
        if (single == null) {
            synchronized(Singleton.class) {
                if (single == null) {
                    single = new Singleton();
                }
            }
        }
        return single;
    }
}
```
