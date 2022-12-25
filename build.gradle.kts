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
    val mergePrefix = project.property("testReportMergePrefix").toString()
    destinationDir = rootDir.resolve(mergePrefix)
    val reports = rootDir.resolve("reports").listFiles { f: File -> f.name.startsWith(mergePrefix) }
    reportOn(*reports)
}
