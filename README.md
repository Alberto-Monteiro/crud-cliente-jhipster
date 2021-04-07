# Bem vindo ao projeto de teste do CRUD do cliente

Esse sistema é um monólito que tem um CRUD simples da entidade Cliente. Ele foi desenvolvido usando um arquétipo do JHipster e
aprimorado a API Rest. [https://albertomonteiro.dev/](https://albertomonteiro.dev/)<br> Você pode logar como admin admin ou user user

## Tecnologia usada

- Java 11
- Angular 11
- Spring Boot
- Spring Security
- Spring Data
- JWT
- mapstruct
- Liquibase
- PostgreSQL
- Redis
- Swagger
- Cucumber

#### Em produção

- Google Cloud Platform
- Docker

## Como foi desenvolvido

![](https://raw.githubusercontent.com/Alberto-Monteiro/repositorio-imagens/main/crudcliente/Captura%20de%20tela%202021-04-01%20115012.png)<br>
A partir dessa modelagem o sistema foi feito.

A estrutura das tabelas se encontram em [crud-cliente-jhipster/src/main/resources/config/liquibase/changelog/](https://github.com/Alberto-Monteiro/crud-cliente-jhipster/tree/master/src/main/resources/config/liquibase/changelog) baseado em liquibase

A API Rest possui uma documentação feita em Swagger que pode ser consultada em [https://albertomonteiro.dev/docs](https://albertomonteiro.dev/docs). Para explorar o Swagger você pode configurar o tokem:<br> bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTYxOTg0NzUxMn0.O5aV*YFXtawLf2u55fdMS-jxkpdBZovt5QF1SPZ9bPNNJGVA7GKICCrzvjRU0j-bp8_QceeEru*-A6VrxJAJaQ

Também tem a documentação em Postman [https://documenter.getpostman.com/view/15195822/TzCP5mJB](https://documenter.getpostman.com/view/15195822/TzCP5mJB)<br>
E o arquivo exportado pelo Postman [https://www.postman.com/collections/2788deaf95a50f000ea7](https://www.postman.com/collections/2788deaf95a50f000ea7)

O sistema está usando um provedor de cache pelo redis

#### Testes:

- protractor
- cucumber
- gatling

## Produção

O sistema está rodando em produção em uma maquina virtual no Google Cloud Platform dentro de um container do Docker, a imagem foi gerada pelo Maven usando um plugin jib da google, com ele é possível gerar imagem sem a daemon Docker.

Essa imagem Docker está disponível no meu repositório publico do DockerHub [rocksdf/crudcliente:latest](https://hub.docker.com/r/rocksdf/crudcliente)

Através do proxy traefik eu disponibilizei o link [https://albertomonteiro.dev/](https://albertomonteiro.dev/) com o certificado da Let's Encrypt, sempre é redirecionado para o Https.

O arquivo do docker-compose que está em produção é esse:

```yml
version: '3.9'
services:
  traefik:
    container_name: traefik
    image: library/traefik:v2.4.7
    labels:
      - traefik.http.routers.api.rule=Host(`traefik.albertomonteiro.dev`)
      - traefik.http.routers.api.entrypoints=web
      - traefik.http.routers.api.tls=false
      - traefik.http.routers.api.middlewares=https_redirect
      - traefik.http.middlewares.https_redirect.redirectscheme.scheme=https
      - traefik.http.middlewares.https_redirect.redirectscheme.permanent=true

      - traefik.http.routers.apis.rule=Host(`traefik.albertomonteiro.dev`)
      - traefik.http.routers.apis.entrypoints=websecure
      - traefik.http.routers.apis.tls.certresolver=leresolver
      - traefik.http.routers.apis.service=api@internal
      - traefik.http.routers.apis.middlewares=auth

      - traefik.http.middlewares.auth.basicauth.users=admin:******
    command:
      - --providers.docker=true
      - --entrypoints.web.address=:80
      - --entrypoints.websecure.address=:443
      #- --certificatesresolvers.leresolver.acme.caserver=https://acme-staging-v02.api.letsencrypt.org/directory # <- Comentar em produção
      - --certificatesresolvers.leresolver.acme.email=email@gmail.com
      - --certificatesResolvers.leresolver.acme.storage=/letsencrypt/acme.json
      - --certificatesResolvers.leresolver.acme.httpChallenge.entryPoint=web
      - --api.dashboard=true
      - --api.insecure=false
      #- --log.level=DEBUG
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - /home/dockerVolume/traefik/letsencrypt:/letsencrypt
    restart: always
    ports:
      - 80:80
      - 443:443
      #- 8080:8080

  portainer:
    container_name: portainer
    image: portainer/portainer-ce:2.1.1
    labels:
      - traefik.http.routers.portainer-http.rule=Host(`portainer.albertomonteiro.dev`)
      - traefik.http.routers.portainer-http.entrypoints=web
      - traefik.http.routers.portainer-http.tls=false
      - traefik.http.routers.portainer-http.middlewares=https_redirect
      - traefik.http.middlewares.https_redirect.redirectscheme.scheme=https
      - traefik.http.middlewares.https_redirect.redirectscheme.permanent=true

      - traefik.http.routers.portainer-https.rule=Host(`portainer.albertomonteiro.dev`)
      - traefik.http.routers.portainer-https.entrypoints=websecure
      - traefik.http.routers.portainer-https.tls.certresolver=leresolver

      - traefik.http.services.portainer.loadbalancer.server.port=9000
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - /home/dockerVolume/portainer/data:/data
    restart: always
    #ports:
    #  - 8000:8000
    #  - 9000:9000

  crudcliente-postgresql:
    container_name: crudcliente-postgresql
    image: postgres:13.2
    environment:
      - POSTGRES_DB=crudcliente
      - POSTGRES_PASSWORD=******
    volumes:
      - /home/dockerVolume/postgresql:/var/lib/postgresql/data/
    restart: always
    #ports:
    #  - 5432:5432

  crudcliente-redis:
    container_name: crudcliente-redis
    image: library/redis:6.2.1
    volumes:
      - /home/dockerVolume/redis/data:/data
    restart: always
    #ports:
    #  - 6379:6379

  crudcliente-app:
    container_name: crudcliente-app
    image: rocksdf/crudcliente
    labels:
      - traefik.http.routers.crudcliente-http.rule=Host(`albertomonteiro.dev`)
      - traefik.http.routers.crudcliente-http.entrypoints=web
      - traefik.http.routers.crudcliente-http.tls=false
      - traefik.http.routers.crudcliente-http.middlewares=https_redirect
      - traefik.http.middlewares.https_redirect.redirectscheme.permanent=true
      - traefik.http.middlewares.https_redirect.redirectscheme.scheme=https

      - traefik.http.routers.crudcliente-https.rule=Host(`albertomonteiro.dev`)
      - traefik.http.routers.crudcliente-https.entrypoints=websecure
      - traefik.http.routers.crudcliente-https.tls.certresolver=leresolver

      - traefik.http.services.crudcliente.loadbalancer.server.port=8080
    environment:
      - _JAVA_OPTIONS=-Xmx512m -Xms256m
      # Para gerar um novo, digite: openssl rand -base64 64
      - JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET=ZfVKFfZC4gRM+9lb2lXNWkQl6gCXR+oBT3nKlrUa7mlghJtG4Gm9vz2PZmcRWzCrklZZwMroaZC9d54gedfm8w==
      - SPRING_PROFILES_ACTIVE=prod,api-docs
      - SPRING_LIQUIBASE_CONTEXTS=prod,faker
      - SPRING_LIQUIBASE_URL=jdbc:postgresql://crudcliente-postgresql:5432/crudcliente
      - SPRING_DATASOURCE_URL=jdbc:postgresql://crudcliente-postgresql:5432/crudcliente
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=******
      - JHIPSTER_CACHE_REDIS_SERVER=redis://crudcliente-redis:6379
      - JHIPSTER_CACHE_REDIS_CLUSTER=false
      - SPRING_MAIL_HOST=smtp.sendgrid.net
      - SPRING_MAIL_PORT=587
      - SPRING_MAIL_USERNAME=apikey
      - SPRING_MAIL_PASSWORD=******
      - JHIPSTER_MAIL_FROM=suporte@albertomonteiro.dev
      - JHIPSTER_MAIL_BASEURL=https://albertomonteiro.dev
      - MANAGEMENT_METRICS_EXPORT_PROMETHEUS_ENABLED=false
      - JHIPSTER_SLEEP=30
    restart: always
    #ports:
    #  - 8080:8080
```

Caso queira testar na maquina local você pode usar o seguinte docker-compose:

```yml
version: '3.9'
services:
  crudcliente-redis:
    container_name: crudcliente-redis
    image: library/redis:6.2.1
    restart: always
    #ports:
    #  - 6379:6379

  crudcliente-postgresql:
    container_name: crudcliente-postgresql
    image: postgres:13.2
    environment:
      - POSTGRES_DB=crudcliente
      - POSTGRES_PASSWORD=
      - POSTGRES_HOST_AUTH_METHOD=trust
    restart: always
    #ports:
    #  - 5432:5432

  crudcliente-app:
    container_name: crudcliente-app
    image: rocksdf/crudcliente
    environment:
      - _JAVA_OPTIONS=-Xmx512m -Xms256m
      - SPRING_PROFILES_ACTIVE=prod,api-docs
      - SPRING_LIQUIBASE_CONTEXTS=prod,faker
      - SPRING_LIQUIBASE_URL=jdbc:postgresql://crudcliente-postgresql:5432/crudcliente
      - SPRING_DATASOURCE_URL=jdbc:postgresql://crudcliente-postgresql:5432/crudcliente
      - SPRING_DATASOURCE_USERNAME=postgres
      - JHIPSTER_CACHE_REDIS_SERVER=redis://crudcliente-redis:6379
      - JHIPSTER_SLEEP=30
    restart: always
    ports:
      - 80:8080
```

Caso queira conhecer mais sobre o JHipster você pode continuar lendo... Boa leitura.

# crudcliente

This application was generated using JHipster 7.0.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v7.0.1](https://www.jhipster.tech/documentation-archive/v7.0.1).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

We use npm scripts and [Angular CLI][] with [Webpack][] as our build system.

If you are using redis as a cache, you will have to launch a cache server.
To start your cache server, run:

```
docker-compose -f src/main/docker/redis.yml up -d
```

The cache can also be turned off by adding to the application yaml:

```
spring:
    cache:
        type: none
```

See [here](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html#boot-features-caching-provider-none) for details.

**WARNING**: If you using second level hibernate cache and disabling the spring cache, you have to disable the second level hibernate cache as well since they are using
the same CacheManager.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

```
./mvnw
npm start
```

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

### PWA Support

JHipster ships with PWA (Progressive Web App) support, and it's turned off by default. One of the main components of a PWA is a service worker.

The service worker initialization code is disabled by default. To enable it, uncomment the following code in `src/main/webapp/app/app.module.ts`:

```typescript
ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
```

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

```
npm install --save --save-exact leaflet
```

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

```
npm install --save-dev --save-exact @types/leaflet
```

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Edit [src/main/webapp/app/app.module.ts](src/main/webapp/app/app.module.ts) file:

```
import 'leaflet/dist/leaflet.js';
```

Edit [src/main/webapp/content/scss/vendor.scss](src/main/webapp/content/scss/vendor.scss) file:

```
@import '~leaflet/dist/leaflet.css';
```

Note: There are still a few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Using Angular CLI

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

```
ng generate component my-component
```

will generate few files:

```
create src/main/webapp/app/my-component/my-component.component.html
create src/main/webapp/app/my-component/my-component.component.ts
update src/main/webapp/app/app.module.ts
```

## Building for production

### Packaging as jar

To build the final jar and optimize the crudcliente application for production, run:

```
./mvnw -Pprod clean verify
```

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

```
java -jar target/*.jar
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

### Client tests

Unit tests are run by [Jest][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

```
npm test
```

UI end-to-end tests are powered by [Protractor][], which is built on top of WebDriverJS. They're located in [src/test/javascript/e2e](src/test/javascript/e2e)
and can be run by starting Spring Boot in one terminal (`./mvnw spring-boot:run`) and running the tests (`npm run e2e`) in a second one.

### Other tests

Performance tests are run by [Gatling][] and written in Scala. They're located in [src/test/gatling](src/test/gatling).

To use those tests, you must install Gatling from [https://gatling.io/](https://gatling.io/).

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off authentication in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod verify jib:dockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 7.0.1 archive]: https://www.jhipster.tech/documentation-archive/v7.0.1
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v7.0.1/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v7.0.1/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v7.0.1/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v7.0.1/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v7.0.1/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v7.0.1/setting-up-ci/
[gatling]: https://gatling.io/
[node.js]: https://nodejs.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: https://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: https://jasmine.github.io/2.0/introduction.html
[protractor]: https://angular.github.io/protractor/
[leaflet]: https://leafletjs.com/
[definitelytyped]: https://definitelytyped.org/
