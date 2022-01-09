import com.mercandalli.build_src.main.Const
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Const.compileSdkVersion)

    defaultConfig {
        applicationId = "com.mercandalli.android.apps.sampler"
        minSdkVersion(Const.minSdkVersion)
        targetSdkVersion(Const.targetSdkVersion)
        versionCode = Const.versionCode
        versionName = Const.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    externalNativeBuild {
        cmake {
            path(File("$projectDir/CMakeLists.txt"))
        }
    }
    ndkVersion = Const.ndkVersion

    sourceSets {
        getByName("main") {
            // Split resources.
            // https://medium.com/google-developer-experts/android-project-structure-alternative-way-29ce766682f0#.sjnhetuhb
            res.srcDirs(
                    "src/main/res/main",
                    "src/main/res/pad"
            )
        }
    }
}

// KtLint - Static code analysis
// https://proandroiddev.com/kotlin-static-analysis-why-and-how-a12042e34a98
val ktlint by configurations.creating

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    // KtLint - Static code analysis
    // https://proandroiddev.com/kotlin-static-analysis-why-and-how-a12042e34a98
    ktlint("com.pinterest:ktlint:0.43.2")
}

// KtLint - Static code analysis
// https://proandroiddev.com/kotlin-static-analysis-why-and-how-a12042e34a98
tasks.register<JavaExec>("ktlint") {
    group = "verification"
    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args("--android", "src/**/*.kt")
}
