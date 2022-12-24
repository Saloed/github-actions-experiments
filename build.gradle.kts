plugins {
    kotlin("jvm") version "1.7.20"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter", "junit-jupiter-params", "5.8.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

task<TestReport>("mergeTestReports") {
    destinationDir = rootDir.resolve("mergedReports")
    val reports = rootDir.resolve("reports")
    val binaryReports = reports.walkTopDown().filter { it.isDirectory && it.name == "binary" }.toList()
    reportOn(*binaryReports.toTypedArray())
}
