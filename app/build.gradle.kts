plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.solutionplus.altasherat"
    flavorDimensions += "logging"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.solutionplus.altasherat"
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

    productFlavors {
        create("logCat") {
            dimension = "logging"
        }

        create("logWriter") {
            dimension = "logging"
        }

        create("production") {
            dimension = "logging"
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    androidComponents {
        beforeVariants { variant ->
            val isReleaseWithLogCatOrLogWriterFlavor = variant.buildType == "release" &&
                    variant.productFlavors.any { it.second in listOf("logCat", "logWriter") }

            val isDebugWithProductionFlavor =
                variant.buildType == "debug" && variant.productFlavors.any { it.second == "production" }

            if (isReleaseWithLogCatOrLogWriterFlavor || isDebugWithProductionFlavor) {
                variant.enable = false
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}