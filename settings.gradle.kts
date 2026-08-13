pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")

            }
        }
        mavenCentral()
        gradlePluginPortal()



    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Read Mapbox token from local.properties (preferred), then from gradle.properties/user properties, then env var
        val localPropsFile = File(rootDir, "local.properties")
        val localProps = java.util.Properties()
        if (localPropsFile.exists()) {
            localPropsFile.inputStream().use { localProps.load(it) }
        }
        val tokenFromLocal = localProps.getProperty("API_KEY_MAPBOX")?.trim()
        val tokenFromGradleProp = providers.gradleProperty("MAPBOX_DOWNLOADS_TOKEN").orNull?.trim()
        val tokenFromEnv = System.getenv("MAPBOX_DOWNLOADS_TOKEN")?.trim()
        val mapboxToken = tokenFromLocal ?: tokenFromGradleProp ?: tokenFromEnv

        // mapboxToken resolved from local.properties, gradle properties or environment

        google()
        mavenCentral()

        maven {
            // URL correto do repositório de downloads Mapbox
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")

            credentials {
                username = "mapbox"
                // ler a token do local.properties -> gradle.properties -> variável de ambiente
                password = (mapboxToken ?: "")
            }

            // configura autenticação básica
            authentication.create<BasicAuthentication>("basic")
        }
        }
    }


rootProject.name = "ProjetoJuquery"
include(":app")
