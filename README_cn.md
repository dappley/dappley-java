# Dappley Java

![platform](https://img.shields.io/badge/platform-Android%20%7C%20Java-lightgrey.svg)
![Gradle](https://img.shields.io/badge/Gradle-4.6-brightgreen.svg)
[![last commit](https://img.shields.io/github/last-commit/dappley/dappley-java.svg)](https://github.com/dappley/dappley-java/commits/master)
![repo size](https://img.shields.io/github/repo-size/dappley/dappley-java.svg)
[![Licence](https://img.shields.io/github/license/dappley/dappley-java.svg)](https://github.com/dappley/dappley-java/blob/master/LICENSE)

Dappley公链项目Java生态应用接入SDK。

English versioned document see [README](README.md).

## Android SDK指南
dappley-android-sdk是Dappley项目在Android平台上的SDK。

### 安装引入
推荐使用Gradle/Maven：
```xml
dependencies {
    implementation 'com.dappley:android-sdk:0.2-alpha'
}
```
更多安装指引参考[Android 安装教程](https://github.com/dappley/dappley-java/wiki/android_setup_cn)。

### 快速上手
android-sdk提供了钱包地址生成、导入；账户未花费交易及余额查询；交易转账功能（与链节点通信）的功能，APP中只需要将SDK接入项目，调用其中提供的开放接口即可。

APP接入SDK后，需在Activity或Application中调用初始化代码，且应当仅调用一次，建议在Application中调用init函数，初始化示例：

```java
public class AppData extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // register dappley service
        Dappley.init(getApplicationContext(), Dappley.DataMode.REMOTE_ONLINE);
    }
}
```
SDK提供一个全局实例，例如创建钱包：

```java
Wallet wallet = Dappley.createWallet();
```
其他更详细的用法参见[Android 使用教程](https://github.com/dappley/dappley-java/wiki/android_tutorial_cn)。

## Java SDK指南
dappley-java-sdk是Dappley项目在Java平台上的SDK。

### 安装引入
推荐使用Gradle/Maven：
```xml
dependencies {
    implementation 'com.dappley:java-sdk:0.1-alpha'
}
```
更多安装指引参考[Java 安装教程](https://github.com/dappley/dappley-java/wiki/java_setup_cn)。

### 快速上手
java-sdk同Android平台接口一样，提供了诸多与Dappley链相关的开放接口。

Java应用接入SDK后，需在Application中调用初始化代码，且应当仅调用一次，例如SpringBoot框架中应在SpringBootApplication中调用init函数。初始化示例：

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
    }
}
```

SDK提供一个全局实例，例如创建钱包：

```java
Wallet wallet = Dappley.createWallet();
```
其他更详细的用法参见[Java 使用教程](https://github.com/dappley/dappley-java/wiki/java_tutorial_cn)。

## FAQ
具体参见[常见问题](https://github.com/dappley/dappley-java/wiki/FAQ_cn)

## 致谢
* [MMKV](https://github.com/Tencent/MMKV)
* [web3j](https://github.com/web3j/web3j)
* [TodayStepCounter](https://github.com/jiahongfei/TodayStepCounter)
* [Google Fit](https://www.google.com/fit)

## 开源协议
dappley-java项目遵从开源协议[LGPL-3.0](https://www.gnu.org/licenses/lgpl-3.0.en.html).