package lehrbaum.de.onenightcomps

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import org.koin.core.parameter.ParameterDefinition
import org.koin.core.parameter.emptyParameterDefinition
import org.koin.core.scope.Scope
import org.koin.standalone.StandAloneContext

//Not such a fan of constructor injection with viewmodels, it is to tight coupling.
//I prefer simple injecting everywhere, these functions allow that.

inline fun <reified T : Any> inject(
	name: String = "",
	scope: Scope? = null,
	noinline parameters: ParameterDefinition = emptyParameterDefinition()
) = lazy { get<T>(name, scope, parameters) }

inline fun <reified T : Any> get(
	name: String = "",
	scope: Scope? = null,
	noinline parameters: ParameterDefinition = emptyParameterDefinition()
): T = StandAloneContext.getKoin().koinContext.get(name, scope, parameters)

//Some things are nicer to read with extension functions.

fun <X, Y> LiveData<X>.map(mapper: ((X) -> Y)): LiveData<Y> {
	return Transformations.map(this, mapper)
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
	observe(lifecycleOwner::getLifecycle, block)
}