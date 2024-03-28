plugins {
    id("com.android.application")
//    kotlin("kapt")
//    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.mj.musicyun"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mj.musicyun"
        minSdk = 30
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
    buildFeatures {
        viewBinding = true
        dataBinding=true
    }
}
// Allow references to generated code
//kapt {
//    correctErrorTypes = true
//}

dependencies {

    //hilt
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("androidx.annotation:annotation:1.6.0")
//    kapt("com.google.dagger:hilt-android-compiler:2.44")


    //Room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
//    // To use Kotlin annotation processing tool (kapt)
//    kapt("androidx.room:room-compiler:$room_version")
////    // To use Kotlin Symbol Processing (KSP)
//    ksp("androidx.room:room-compiler:$room_version")

    //   //解决创建fragment-viewmodel后出现的重复问题
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.20"))

    //Preference Setting
    implementation("androidx.preference:preference-ktx:1.2.0")


    //smartreflash
    implementation  ("io.github.scwang90:refresh-layout-kernel:2.0.6")      //核心必须依赖
    implementation  ("io.github.scwang90:refresh-header-classics:2.0.6")    //经典刷新头

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.4.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.4.0")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")

    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    //okhttp
    implementation ("com.squareup.okhttp3:okhttp:3.12.0")
    //glide dependence
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")
    //recycleview
    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    //media3 dependencies
    implementation ("androidx.media3:media3-exoplayer:1.1.1")
    implementation ("androidx.media3:media3-exoplayer-dash:1.1.1")
    implementation ("androidx.media3:media3-ui:1.1.1")
    implementation ("androidx.media3:media3-session:1.1.1")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


}