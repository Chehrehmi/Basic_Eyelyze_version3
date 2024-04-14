plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.basic_eyelyze_version3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.basic_eyelyze_version3"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //lottie
    implementation("com.airbnb.android:lottie:3.7.0")
    //MLKIT3
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:17.0.0")
}