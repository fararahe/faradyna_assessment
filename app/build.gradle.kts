plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = libs.versions.appId.get()
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        applicationId = libs.versions.appId.get()
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }
}

dependencies {
    
    with(libs) {
        // Core
        implementation(bundles.core)

        // Lifecycle
        implementation(androidx.lifecycle.runtime.ktx)

        // Material 3
        implementation(androidx.material3)

        // Compose
        implementation(bundles.compose)
        debugImplementation(androidx.ui.tooling)
        debugImplementation(androidx.ui.test.manifest)
        implementation(platform(androidx.compose.bom))
        androidTestImplementation(platform(androidx.compose.bom))

        // Test
        testImplementation(junit)
        androidTestImplementation(androidx.junit)
        androidTestImplementation(androidx.espresso.core)
        androidTestImplementation(androidx.ui.test.junit4)

        // Timber
        implementation(timber)

        // Intuit SDP SSP
        implementation(bundles.intuit)

        // Hilt
        implementation(hilt.android)
        ksp(hilt.android.compiler)

        // Coroutines
        implementation(bundles.coroutine)

        // OkHttp
        implementation(bundles.okhttp)

        // Retrofit
        implementation(bundles.retrofit)

        // Moshi
        implementation(moshi.kotlin)
        ksp(moshi.kotlin.codegen)

        // Chucker
        debugImplementation(chucker)
        releaseImplementation(chucker.no.op)

        // Room
        implementation(bundles.room)
        ksp(room.compiler)

        // Datastore
        implementation(bundles.datastore)

        // Crypto
        implementation(androidx.security.crypto)

        // Accompaniest
        implementation(bundles.accompanies)
    }
}