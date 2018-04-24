# BarsHelper




关于状态栏，导航栏的工具类，详细的注释，详细的实现策略


# 基本方法

| 方法                                              | 作用                                                         |
| :------------------------------------------------ | :----------------------------------------------------------- |
| ```setStatusTransparentAndColor```                | 设置沉浸式布局，并且使状态栏颜色为指定，默认为不透明         |
| ```setDrawerLayoutTransparent```                  | 设置Drawer侧滑布局时ContentView沉浸式并且侧滑NavigationView表现为全屏沉浸式 |
| ```addGradientOrNormalStatusBarViewAtDecorView``` | 添加沉浸式状态栏，表现为普通同一颜色式（最常用）和渐变式状态栏（和Toolbar搭配使用【设置渐变背景】起到渐变式的额头效果） |
| ```setNavigationImmersive```                      | 粘性沉浸式模式                                               |
| ```setAndroidtStatusBarUIAndTextColor```          | 设置当前状态栏文字UI为黑色或者白色，Android6.0默认为白色     |
| ```checkDeviceSupportNavigationBar```             | 判断手机是否支持导航栏                                       |
| ```setStatusBarColor```                           | 设置状态栏或者导航栏颜色                                     |
| ```getStatusBarOrNavigationHeight```              | 通过反射获取当前状态栏或者导航栏的高度,单位为px              |
| ```hideNavigationBar```                           | 隐藏导航栏                                                   |
| ```showNavigationBar```                           | 显示导航栏                                                   |
| ```isStatusBarVisible```                          | 判断当前Activity的可见性                                     |
| ```setNavigationBarColor```                       | 设置当前的导航栏颜色                                         |
| ```setNavBarVisibility```                       | 设置当前的导航栏沉浸式                                      |

详细使用可以看注释

# 使用效果如下



1 普通Activity下有Toobar式

![使用前](/img/Screenshot_2018-03-30-15-46-28.png) 

```
BarsHelper.addGradientOrNormalStatusBarViewAtDecorView(this, true, -1, getResources().getColor(R.color.colorPrimaryDark), 1.0f);
```

![使用后](/img/Screenshot_2018-03-30-15-50-52.png)

---

2 渐变式状态栏

![使用前](/img/Screenshot_2018-03-30-16-06-25.png)
```
 BarsHelper.addGradientOrNormalStatusBarViewAtDecorView(this, false, R.drawable.gradient_1, -1, 1.0f);
```
![使用后](/img/Screenshot_2018-03-30-16-10-49.png)

---


3 普通Activity全沉浸

![使用前](/img/Screenshot_2018-03-30-15-56-33.png)

```
BarsHelper.setStatusTransparentAndColor(this,  getResources().getColor(R.color.colorPrimaryDark), 0.0f);
```

![使用后](/img/Screenshot_2018-03-30-15-56-49.png)

---

4 侧滑Activity+Toolbar式

![使用前](/img/Screenshot_2018-03-30-15-58-48.png)

```
BarsHelper.setDrawerLayoutTransparent(this, getResources().getColor(R.color.colorPrimaryDark), R.id.layout_content);
```

![使用后](/img/Screenshot_2018-03-30-15-59-14.png)

---

5 侧滑无Toolbar+全沉浸

![使用前](/img/Screenshot_2018-03-30-16-01-26.png)
```
BarsHelper.setStatusTransparentAndColor(this, -1, 0.0f)
```
![使用后](/img/Screenshot_2018-03-30-16-04-52.png)


