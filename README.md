# Dappley Java

![platform](https://img.shields.io/badge/platform-Android | Java-lightgrey.svg)
![Gradle](https://img.shields.io/badge/Gradle-4.6-brightgreen.svg)
[![last commit](https://img.shields.io/github/last-commit/dappley/dappley-java.svg)](https://github.com/dappley/dappley-java/commits/master)
![repo size](https://img.shields.io/github/repo-size/dappley/dappley-java.svg)
[![Licence](https://img.shields.io/github/license/dappley/dappley-java.svg)](https://github.com/dappley/dappley-java/blob/master/LICENSE)

Project dapplay-java is built for applications of Dappley project base on java language.

中文版文档详见 [README](README_cn.md).

## Android SDK Guide
The dappley-android-sdk is a sdk for Dappley project on Android platform.

### Installation
Via Gradle or Maven:
```xml
dependencies {
    implementation 'com.dappley:android-sdk:0.2-alpha'
}
```
For more installation guides, see [Android SDK Setup](https://github.com/dappley/dappley-java/wiki/android_setup).

### Getting Started

android-sdk provides these interfaces:

- Wallet create and import;
- Wallet balance query and unspent transaction vouts query;
- Send transaction between two user address;
- Encrypt and decrypt of wallet data;
- Other related apis.

Dappley should be a single instance object in your project, so remember to call init method when your application started. On Android, you may put these codes in your Application class:

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
SDK has a global instance, you can use it directly. You can create a wallet like this:

```java
Wallet wallet = Dappley.createWallet();
```
For more details of tutorial, see [Android SDK Tutorial](https://github.com/dappley/dappley-java/wiki/android_tutorial)。

## Java SDK Guide
The dappley-java-sdk is a sdk for Dappley project on Java platform.

### Installation

Via Gradle or Maven:

```xml
dependencies {
    implementation 'com.dappley:java-sdk:0.1-alpha'
}
```
For more installation guides, see [Java SDK Setup](https://github.com/dappley/dappley-java/wiki/java_setup)。

### Getting Started
Our java-sdk is more like the android-sdk. We also provide several interfaces about blockchain Dappley.

Dappley should be a single instance object in your project, so remember to call init method when your application started. On platform SpringBoot, you may put these codes in your SpringBootApplication class:

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
    }
}
```

SDK has a global instance, you can use it directly. You can create a wallet like this:

```java
Wallet wallet = Dappley.createWallet();
```
For more detail uses of Dappley in java sdk, see [Java SDK Tutorial](https://github.com/dappley/dappley-java/wiki/java_tutorial)。

## FAQ
Go and see [FAQ](https://github.com/dappley/dappley-java/wiki/FAQ)

## Thanks
* [MMKV](https://github.com/Tencent/MMKV)
* [web3j](https://github.com/web3j/web3j)
* [TodayStepCounter](https://github.com/jiahongfei/TodayStepCounter)

## License
The dappley-java project is licensed under the [GNU Lesser General Public License Version 3.0 (“LGPL v3”)](https://www.gnu.org/licenses/lgpl-3.0.en.html).