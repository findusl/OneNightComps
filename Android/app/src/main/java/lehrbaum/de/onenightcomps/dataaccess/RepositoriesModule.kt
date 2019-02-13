package lehrbaum.de.onenightcomps.dataaccess

import org.koin.dsl.module.module

val repositoriesModule = module {
	single { CompositionRepository() }
	single { RoleRepository() }
	single { UserRepository() }
	single { RestServiceFactory.getRestService() }
}