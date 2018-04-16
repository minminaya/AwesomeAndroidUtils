# AwesomeAndroidUtils（AAU）

这不仅仅是一个工具类的集合，这里有详细的注释和操作步骤，甚至还有原理的分析，我希望每一个Android开发者都能了解到底层设计的思想和每一行代码的含义，而不仅仅当一个API小王子。

# 进度

v1.0.1:[状态栏及导航栏帮助类](/MD/BarsHelper.md)


v1.0.2:[范型单例工具类](/MD/SingletonHelper.md)和[SizeUtils](/MD/SizeUtils.md)

# 使用方法

一、复制所需的工具类的所需要的方法到你的工程（推荐）

二、gradle集成


1. 在你的```Project build.gradle```下

```
allprojects {
    repositories {
        ....
   	maven { url 'https://jitpack.io' }
    }
}
```
2. 在```app build.gradle```下

```
dependencies {
    ....
    implementation 'com.github.minminaya:AwesomeAndroidUtils:v1.0.3'
}
```

3. 在Application类的onCreate下，初始化Context

```

AAUHelper.initial(this);

```


# LECENSE

Apache License 2.0
