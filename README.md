# Keycloak Require Attribute Authenticator

This is a custom authenticator for [Keycloak](https://www.keycloak.org), that requires a user to have a specified attribute and attribute value. If a user doesn't have the attribute or has an incorrect value, the authenticator will fail.

**Table of Contents:**

- [Build](#build)
- [Deploy and Configure](#deploy-and-configure)
- [Development & Contributing](#development--contributing)
- [License](#license)
- [Author Information](#author-information)

---

## Build

For deploying the authenticator the code must first be compiled and packed as a JAR. Two things are required to build the code, Java JDK 11 and Maven.

If you have both Java and Maven installed locally you just have to execute: 

```sh
mvn clean install -DskipTests -Dkeycloak.version=12.0.4
```

If you prefer using Docker for building the authenticator run:

```sh
docker run -it --rm -u $UID -v "$PWD":/build -w /build maven:3-openjdk-11 \
    mvn clean install \
    -Dmaven.repo.local=/build/.m2/repository
    -DskipTests \
    -Dkeycloak.version=12.0.4
```

## Deploy and Configure

Deploying the authenticator is rather simple. Just drop the JAR file into the `standalone/deployments` directory of Keycloak. It will be picked up automatically. The authenticator can then be added to an [authentication flow](https://www.keycloak.org/docs/latest/server_admin/#_authentication-flows).

The extension offers following options:

| Option | Description |
|--------|-------------|
| Attribute Name | Required attribute name that a user needs to have to proceed with the authentication. The placeholder `{clientId}` can be used to incorporate the client name. |
| Attribute Value | A value that the attribute must have. |
| User Source | Use the user itself as a source of the attribute. |
| Role Source | Use the assigned roles as a source of the attribute. |
| Group Source | Use the assigned groups as a source of the attribute. |

Here is an example authentication flow that incorporates the authenticator:

```plaintext
+-------------------+----------------+-----------------+-----------------+-------------+
| User Auth         |                |                 |                 | required    |
| (generic flow)    +----------------+-----------------+-----------------+-------------+
|                   | Cookie         |                 |                 | alternative |
|                   +----------------+-----------------+-----------------+-------------+
|                   | Browser Form   |                 |                 | alternative |
|                   | (generic flow) +-----------------+-----------------+-------------+
|                   |                | Username        |                 | required    |
|                   |                | Password Form   |                 |             |
|                   |                +-----------------+-----------------+-------------+
|                   |                | Conditional OTP |                 | conditional |
|                   |                | (generic flow)  +-----------------+-------------+
|                   |                |                 | Condition -     | required    |
|                   |                |                 | User Configured |             |
|                   |                |                 +-----------------+-------------+
|                   |                |                 | OTP Form        | required    |
+-------------------+----------------+-----------------+-----------------+-------------+
| Require Attribute |                |                 |                 | required    |
+-------------------+----------------+-----------------+-----------------+-------------+
```

# Configuration Example

## Development & Contributing

Improvements of any kind are welcome. Please open an issue if you feel an important aspect is missing or you encountered a bug.

Useful development resources:

- [Keycloak API Reference](https://www.keycloak.org/docs-api/12.0/javadocs/index.html)
- [Authenticator Examples](https://github.com/thomasdarimont/keycloak-extension-playground)

## License

MIT

## Author Information

Andre Lehmann (aisberg@posteo.de)
