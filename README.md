# ConceptRouter
A small routing library for Android

###GRADLE:
```groovy
	repositories {
	    	...
	    	maven { url 'https://github.com/FireZenk/maven-repo/raw/master/'}
	}
	dependencies {
			...
			compile 'org.firezenk:conceptrouter:{last_version}@aar'
	}
```

###DESCRIPTION:

_ConceptRouter_ consists of 3 main classes:
- `ConceptRouter` which is in charge of navigate between views (`Activity` or `View`).
- `Route` that contains the desired route.
- `Routable` the interface that is implemented for each of our `Route`s.

Additionally, two custom exceptions are provided for make the debugging easier:
- `ParameterNotFoundException` launched when not found a path parameter that we need.
- `NotEnoughParametersException` which is launched if the route has not received all the necessary parameters.

###USAGE

There are two cases when using the router:
- Route to a another `Activity`
- Route to a different `View` inside the `Activity` 

```java
// Navigate to another Activity
ConceptRouter.get().routeTo(this, new Route(DetailRoute.class, bundle));
```

```java
// Navigate to a View
ConceptRouter.get().routeTo(this, new Route(ProductRoute.class, bundle, placeholder));
```

As we can see the only difference is that if we need to navigate to a `View`, we need to serve a placeholder.
Besides this, in our `Activity` have to specify the following (to enable "back button" navigation):

```java
@Override public void onBackPressed() {
	if (!ConceptRouter.get().back(this))
		super.onBackPressed();
}
```

In all the above cases should be understood `this` as `Context`

Finally, to implement a route, implement from the `Routable` the following method:

```java
@Override public void route(@NonNull Context context, @NonNull Bundle parameters, @Nullable Object viewParent)
            throws ParameterNotFoundException, NotEnoughParametersException {
	// How to opening our Activity or View
}
```

Sample `Routable` implementations:

[Implementation for Activity](https://github.com/FireZenk/ConceptRouter/blob/master/sample/src/main/java/org/firezenk/conceptrouter/sample/detail/DetailRoute.java)

[Implementation for View](https://github.com/FireZenk/ConceptRouter/blob/master/sample/src/main/java/org/firezenk/conceptrouter/sample/profile/ProfileRoute.java)

###ADDITIONAL NOTES

- No, it is not contemplated the use of fragments, although it is possible (using `View` sample)
- For more info an samples, see `sample` module
