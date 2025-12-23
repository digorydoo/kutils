import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(libs.kotlin.gradle.plugin)
    }
}

// Unlike "subprojects", "allprojects" includes the root project (this one)
allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    // The following makes sure that all subprojects having plugin kotlin("jvm") target the same JDK version
    plugins.withId("org.jetbrains.kotlin.jvm") {
        the<KotlinJvmProjectExtension>().apply {
            jvmToolchain(17) // JDK version
        }
    }

    // The following modifies the configuration of the "test" task in all subprojects
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()

        testLogging {
            events("skipped", "failed") // emit details for skipped and failed tests only, not "passed"
            showStandardStreams = true // otherwise we won't see anything in console
            exceptionFormat = TestExceptionFormat.FULL

            addTestListener(object: TestListener {
                override fun beforeTest(testDescriptor: TestDescriptor) = Unit
                override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) = Unit
                override fun beforeSuite(suite: TestDescriptor) = Unit

                override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                    if (suite.parent == null) {
                        println(
                            "${suite.name} ${result.resultType}: " +
                                "${result.successfulTestCount} passed, " +
                                "${result.failedTestCount} failed, " +
                                "${result.skippedTestCount} skipped"
                        )
                    }
                }
            })
        }
    }
}
