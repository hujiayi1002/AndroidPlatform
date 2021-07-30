# baselib
    baselib
    使用方式

    android内添加

    kotlinOptions {
        jvmTarget = '1.8'
    }
    viewBinding {
        enabled = true
    }
    dataBinding {
        enabled = true
    }

    allprojects {
        repositories {
            google()
            jcenter()
            mavenCentral()
            maven { url 'https://jitpack.io' }
            maven { url 'http://maven.aliyun.com/nexus/content/groups/public/' }
        }
    }


    dependencies {
	        implementation 'com.github.hujiayi1002:baselib:Tag'
    }
