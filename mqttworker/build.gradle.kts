import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("com.vanniktech.maven.publish")
}

android {
    namespace = "com.chungha.mqttworker"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("com.github.hannesa2:paho.mqtt.android:4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    afterEvaluate {
        publishing {
            publications {
                register("release", MavenPublication::class.java) {
                    from(components["release"])
                    groupId = "com.github.hoangchungk53qx1"
                    artifactId = "mqtt-manager"
                }
            }
        }
    }

    mavenPublishing {
      publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
      signAllPublications()
    }

    mavenPublishing {
        coordinates("io.github.hoangchungk53qx1", "mqtt-manager", "0.0.1")

        pom {
            name.set("mqtt-manager")
            description.set("A description of what my library does.")
            inceptionYear.set("2020")
            url.set("https://github.com/hoangchungk53qx1/mqtt-manager/")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("hoangchungk53qx1")
                    name.set("hoangchungk53qx1")
                    url.set("https://github.com/hoangchungk53qx1/")
                }
            }
            scm {
                url.set("https://github.com/hoangchungk53qx1/mqtt-manager/")
                connection.set("scm:git:git://github.com/hoangchungk53qx1/mqtt-manager.git")
                developerConnection.set("scm:git:ssh://git@github.com/hoangchungk53qx1/mqtt-manager.git")
            }
        }
    }

}
