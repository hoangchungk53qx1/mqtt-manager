import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }

    publishing {
        multipleVariants {
            withSourcesJar()
            withJavadocJar()
            allVariants()
        }
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
//
//    publishing {
//        // Configure publishing data
//        publications {
//            register("release", MavenPublication::class.java) {
//                groupId = "io.github.hoangchungk53qx1"
//                artifactId = "mqtt-manager"
//                version = System.getenv("1.0.0")
//
//                afterEvaluate {
//                    from(components["release"])
//                }
//            }
//        }
//    }



//    publishing {
//        repositories {
//            maven {
//                name = "mqtt-manager"
//                url = uri(layout.buildDirectory.dir("mqttworker"))
//            }
//        }
//    }

    mavenPublishing {
        val artifactId = "mqtt-manager"
        coordinates(
            "io.github.hoangchungk53qx1",
            artifactId,
            "1.0.7"
        )

        pom {
            name.set(artifactId)
            description.set("MqttManager for Android, helps easily manage created clients with methods such as connect, subscrible, unsubcrible Topic")
        }
    }

//    mavenPublishing {
//        publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
//        signAllPublications()
//    }
//
//    mavenPublishing {
//        coordinates("io.github.hoangchungk53qx1", "mqtt-manager", "1.0.6")
//
//        pom {
//            name.set("mqtt-manager")
//            description.set("A description of what my library does.")
//            inceptionYear.set("2023")
//            url.set("https://github.com/hoangchungk53qx1/mqtt-manager/tree/main/mqttworker")
//            licenses {
//                license {
//                    name.set("The Apache License, Version 2.0")
//                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                    distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                }
//            }
//            developers {
//                developer {
//                    id.set("hoangchungk53qx1")
//                    name.set("hoanganhchung")
//                    url.set("https://github.com/hoangchungk53qx1/")
//                }
//            }
//            scm {
//                url.set("scm:git:git://github.com/hoangchungk53qx1/mqtt-manager.git")
//                connection.set("scm:git:ssh://github.com/hoangchungk53qx1/mqtt-manager")
//                developerConnection.set("https://github.com/hoangchungk53qx1/mqtt-manager")
//            }
//        }
//    }

}
