# AwesomeAndroidUtils（AAU）

这不仅仅是一个工具类的集合，这里有详细的注释和操作步骤，甚至还有原理的分析，我希望每一个Android开发者都能了解到底层设计的思想和每一行代码的含义，而不仅仅当一个API小王子。

# 进度

v1.0.1:[状态栏及导航栏帮助类](/MD/BarsHelper.md)


v1.0.2:[范型单例工具类](/MD/SingletonHelper.md)和[SizeUtils](/MD/SizeUtils.md)

2018-04-22 添加MVP基本的Presenter类，测试通过

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
2. 获取最新版本[JitPack](https://jitpack.io/#minminaya/AwesomeAndroidUtils)在```app build.gradle```下



```
dependencies {
    ....
    implementation 'com.github.minminaya:AwesomeAndroidUtils:{x.x.x}'
}
```

3. 在Application类的onCreate下，初始化Context

```

AAUHelper.initial(this);

```


# LECENSE

Copyright 2018 minminaya

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
