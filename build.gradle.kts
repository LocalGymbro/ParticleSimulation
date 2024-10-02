plugins {
    id("java")
    id("java-library")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // https://mvnrepository.com/artifact/com.badlogicgames.gdx/gdx
    implementation("com.badlogicgames.gdx:gdx:1.12.1")
    // https://mvnrepository.com/artifact/com.badlogicgames.gdx/gdx-backend-lwjgl3
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.12.1")
    // https://mvnrepository.com/artifact/com.badlogicgames.gdx/gdx-platform
    implementation("com.badlogicgames.gdx:gdx-platform:1.12.1")
    api("com.badlogicgames.gdx:gdx-platform:1.12.1:natives-desktop")
    //api "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"
    // https://mvnrepository.com/artifact/com.badlogicgames.gdx/gdx-backend-lwjgl
    testImplementation("com.badlogicgames.gdx:gdx-backend-lwjgl:1.12.1")
    // https://mvnrepository.com/artifact/com.badlogicgames.gdx/gdx-freetype
    implementation("com.badlogicgames.gdx:gdx-freetype:1.12.1")

    implementation("com.crashinvaders.vfx:gdx-vfx-core:0.5.4")
    implementation("com.crashinvaders.vfx:gdx-vfx-effects:0.5.4")    // Optional, if you need standard filter/effects.

    //classpath("com.williambl.bluej_jar:bluej-jar:0.1")
}

tasks.test {
    useJUnitPlatform()
}