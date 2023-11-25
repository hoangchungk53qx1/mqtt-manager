# Get started

## 1. Add dependency

- Add `mavenCentral()` to `repositories` list in `build.gradle.kts`/`settings.gradle.kts`.

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
  [...]
  repositories {
    mavenCentral()
    [...]
  }
}
```

- Add dependency to `build.gradle.kts`.

```kotlin
// build.gradle.kts
dependencies {
  implementation("io.github.hoangchungk53qx1:mqtt-manager:1.1.2")
}
```

## 2. Overview
