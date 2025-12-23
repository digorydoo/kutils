plugins {
    kotlin("jvm")
    `java-library`
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src/java"))
        }
    }
}

dependencies {
    // NOTE: versions are now maintained in gradle/libs.versions.toml
    implementation(platform(kotlin("bom")))
    testImplementation(kotlin("test"))
}
