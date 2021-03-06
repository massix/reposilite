# Reposilite [![Build Status](https://travis-ci.com/dzikoysk/reposilite.svg?branch=master)](https://travis-ci.com/dzikoysk/reposilite) [![Coverage Status](https://coveralls.io/repos/github/dzikoysk/reposilite/badge.svg?branch=master)](https://coveralls.io/github/dzikoysk/reposilite?branch=master)
Reposilite *(formerly NanoMaven)* - lightweight repository manager for Maven artifacts. 
It is a simple solution to replace managers like Nexus, Archiva or Artifactory. 

![Preview](https://user-images.githubusercontent.com/4235722/83308288-ca8d9000-a206-11ea-8db4-5981b39f2239.png)

#### Features
* [x] Working Maven repository manager *(example: [repo.panda-lang.org](https://repo.panda-lang.org))*
* [x] Docker image *(repository: [dzikoysk/reposilite](https://hub.docker.com/r/dzikoysk/reposilite))*
* [x] Authorization *(deploy and downloads)*
* [x] Deploy *(using tokens and BCrypt)*
* [x] Customizable front page
* [x] [dependabot](https://dependabot.com/) supported
* [x] Multiple repositories under a single URL
* [x] CLI
* [x] Snapshots
* [x] Proxy for the specified remote repositories
* [x] Multithreading
* [x] Statistics
* [x] REST API
* [x] Repository browser
* [x] Dashboard (+ Admin panel)
* [ ] Docs

#### Installation
Releases: [GitHub Downloads](https://github.com/dzikoysk/reposilite/releases) <br>
Images: [DockerHub Repository](https://hub.docker.com/r/dzikoysk/reposilite) <br>
Requirements: 
* Java 8+
* RAM 8MB+

| Amount | Description |
|:------:|-------------|
| *8MB* | Tiny repository for personal projects |
| *16MB* - *32MB* | *--------------------^------------------* + CI + Proxy |
| *48MB - 128MB* | Tiny public repository *(recommended)* |
| *128MB+* | Public repository | 

To launch Reposilite with defined amount of RAM, use `Xmx` parameter:
```bash
$ java -Xmx<Amount>M -jar reposilite.jar
```
If you will not define the memory size, Reposilite will probably use around *~30MB to ~250MB*.
You may also use Reposilite through the docker image:

```bash
$ docker pull dzikoysk/reposilite
```

You can also pass custom configuration values through the environment variables:

```bash
$ docker run -e JAVA_OPTS='-Xmx128M -Dreposilite.port="8080"' reposilite
```

#### Guide
List of available management commands

```bash
Reposilite 2.6.1 Commands:
  help - List available commands
  status - Display summary status of app health
  stats [<limiter>/<pattern>] - Display collected metrics and (optional) filter them using the given limiter or pattern
  tokens - List all generated tokens
  keygen <path> <alias> - Generate a new access token for the given path
  revoke <alias> - Revoke token
  purge - Clear cache
  stop - Shutdown server
```

To deploy artifacts we have to generate `access token` assigned to the given path. Example usages:

```bash
keygen / admin
19:55:20.692 INFO | Generated new access token for admin (/)
19:55:20.692 INFO | AW7-kaXSSXTRVL_Ip9v7ruIiqe56gh96o1XdSrqZCyTX2vUsrZU3roVOfF-YYF-y
19:55:20.723 INFO | Stored tokens: 1

keygen /org/panda-lang/reposilite reposilite
19:56:09.109 INFO | Generated new access token for reposilite (/org/panda-lang/reposilite)
19:56:09.109 INFO | OFnV-2GiZeX0cHpeDvuLo0xjUpU5wNUcpkR4521fG68U9anfqNwKsVkFcQUCK4yk
19:56:09.114 INFO | Stored tokens: 2
```

To use generated token add a new server in your `~/m2/settings.xml`  

```xml
<server>
  <id>{repository-id}</id>
  <username>{alias}</username>
  <password>{token}</password>
</server>
```

#### FAQ
**Q:** Maven randomly interrupts deploy and throws socket write error <br>
**A:** It is a common issue on Java 12 due to the bug related to SSL. To resolve this problem, just change Java version used by Maven build, e.g. 8 or 14. 

**Q:** Checksum validation failed on the maven-metadata.xml <br>
**A:** Checksum validation always fails, because Reposilite does not trust the metadata files offered by client - they're generated by server, based on the repository content to ensure consistency. 
It's fine and it does not affect your builds.
