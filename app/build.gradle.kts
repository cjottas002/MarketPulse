plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    //Kapt
    alias(libs.plugins.kapt)
    //Hilt
    alias(libs.plugins.hilt)
    //Room
//    alias(libs.plugins.room)
    //KSP
    alias(libs.plugins.ksp)
}

android {
    namespace = "es.market.pulse"
    compileSdk = 34

    defaultConfig {
        applicationId = "es.market.pulse"
        minSdk = 34
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    // Core Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Retrofit for Network Requests
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // OkHttp for HTTP Requests
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Room for Database
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // DataStore for Preferences
    implementation(libs.datastore.preferences)

    // Image Loading
    implementation(libs.coil)

    // Lifecycle Libraries
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Navigation Component
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Coroutines for Asynchronous Operations
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Serialization for JSON
    implementation(libs.serialization.json)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)



    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Kotlin Coroutines Test - para pruebas de corrutinas
    testImplementation(libs.kotlinx.coroutines.test)

    // MockK - para mocking en pruebas unitarias
    testImplementation (libs.mockito.core)

    // AndroidX Core Testing - para pruebas de componentes de Android como LiveData
    testImplementation(libs.androidx.core.testing)

    // AndroidX Lifecycle ViewModel KTX - para trabajar con ViewModels en Kotlin
    testImplementation(libs.lifecycle.viewmodel.ktx)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}