buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url ="https://adgeneration.github.io/ADG-Android-SDK/repository")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.android.tools.build:gradle:8.5.1")
        classpath("com.beust:klaxon:5.5")
        classpath("com.socdm.d.adgeneration:adg:+")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        // maven(url = "https:// some custom repo")
        maven(url ="https://adgeneration.github.io/ADG-Android-SDK/repository")
        maven(url ="https://voyagegroup.github.io/FluctSDK-Android/m2/repository")
        maven(url ="https://sdk.tapjoy.com/")
        maven(url ="https://verve.jfrog.io/artifactory/verve-gradle-release")
        val nativeDir = if (System.getProperty("os.name").lowercase().contains("windows")) {
            System.getenv("CORONA_ROOT")
        } else if (System.getProperty("os.name").lowercase().contains("linux")) {
            "/home/carlos/tools/Corona/Native"
        } else {
            "${System.getenv("HOME")}/Library/Application Support/Corona/Native/"
        }
        flatDir {
            dirs("$nativeDir/Corona/android/lib/gradle", "$nativeDir/Corona/android/lib/Corona/libs")
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory.asFile.get())
}
