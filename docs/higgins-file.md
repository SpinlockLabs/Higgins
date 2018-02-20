# Project Configuration
Project configurations should always be stored in the root of the source
code repository. The preferred name of the file is `higgins.json`, but
if you want to, you may prefix it with a dot to hide it: `.higgins.json`.

## Example configuration for Gradle
```json
{
  "jobs": [
    {
      "name": "build",
      "tasks": [
        {
          "type": "gradle",
          "gradleTask": "build"
        },
        {
          "type": "artifact",
          "files": [
            "web/build/libs/web-0.1.0-SNAPSHOT.jar",
            "agent/build/libs/agent-0.1.0-SNAPSHOT.jar"
          ]
        }
      ]
    },
    {
      "name": "smokeTest",
      "tasks": [
      ]
    }
  ]
}
```
