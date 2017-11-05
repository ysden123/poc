# deploy1
Playing with SBT Native Packager.

Creates ZIP file with directories
  - bin - shell scripts to execute an application
  - lib - all necessary JARs

See [SBT Native Packager](https://github.com/sbt/sbt-native-packager)

## Build
Run command
```sbtshell
sbt universal:packageBin
```

## Running
1. Unzip target\universal\deploy1-0.0.2.zip to any place
1. Go to bin
1. Run command
```
deploy1.bat
```
