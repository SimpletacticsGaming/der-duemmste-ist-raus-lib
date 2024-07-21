#### Nexus secrets setzen
Folgende Variablen müssen in der `gradle.properties` Datei definiert werden:

```
nexusSnapshotUrl=
nexusUrl=
nexusUser=
nexusPassword=
```

#### Im Falle eines Gradle Upgrades ####

Nach dem Updaten der Variablen:
`versionGradle=8.0.2`

Die Version muss auch in folgendem File angepasst werden:
`gradle\wrapper\gradle-wrapper.properties`

Den Gradle-Wrapper aktuallisieren per Comandline/Task:
`./gradlew wrapper`