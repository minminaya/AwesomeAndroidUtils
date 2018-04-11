# SingletonHelper




范型实现的单例工具类，和双重锁检查模式（DCL）单例有异曲同工之妙


# 基本方法

| 方法                                                  | 作用                                                         |
| :---------------------------------------------------- | :----------------------------------------------------------- |
| ```getSingleton(Class<T> clzss, String objectName)``` | 获取范型单例                                                 |
| ```removeSingleton(String objectName)```              | 移除范型单例，释放对象内存                                   |
| ```CONCURRENT_HASH_MAP```                             | ConcurrentHashMap<String, Object>集合，用于保存对象，类名-key，对象-value |



# 使用


比如一个类Cat

```
public class Cat {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

```


1. 使用范型单例：
```
ingletonHelper.getSingleton(Cat.class, "cat1").setName("小花1");
 
        
```

```
Log.d("HomeActivity", "Name:" + SingletonHelper.getSingleton(Cat.class, "cat1").getName());

```

2. 释放对象

```
SingletonHelper.removeSingleton("cat1");
```






