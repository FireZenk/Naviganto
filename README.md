# Naviganto
A small routing library for Android

[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=58638e1a030a610100a8b4ed&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/58638e1a030a610100a8b4ed/build/latest?branch=master)
[![Build Status](https://travis-ci.org/FireZenk/Naviganto.svg?branch=master)](https://travis-ci.org/FireZenk/Naviganto)
[![Code Climate](https://codeclimate.com/github/FireZenk/Naviganto/badges/gpa.svg)](https://codeclimate.com/github/FireZenk/Naviganto)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/dwyl/esta/issues)


### GRADLE:
```groovy
	repositories {
	    	...
	    	maven { url 'https://github.com/FireZenk/maven-repo/raw/master/'}
	}
	dependencies {
			...
			provided 'javax.annotation:javax.annotation-api:1.2'
			provided 'com.squareup:javapoet:1.8.0'

			def NVersion = '2.3.4'
			compile "org.firezenk.naviganto:annotations:$NVersion"
			compile "org.firezenk.naviganto:processor:$NVersion"
			compile "org.firezenk.naviganto:library:$NVersion"
	}
```

### DESCRIPTION:

_Naviganto_ consists of 5 main classes:
- `Naviganto` which is in charge of navigate between views (`Activity` or `View`).
- `Route` that contains the desired route.
- `@RoutableActivity` and `@RoutableView` to use auto-routes.
- `Routable` the interface that is implemented for each of our _custom_ `Route`s.

Additionally, two custom exceptions are provided for make the debugging easier:
- `ParameterNotFoundException` launched when not found a path parameter that we need.
- `NotEnoughParametersException` which is launched if the route has not received all the necessary parameters.

### USAGE

#### 1. Route to the target view

There are two cases when using the router:
- Route to a another `Activity`
- Route to a different `View` inside the `Activity`

```java
// Navigate to another Activity; Bundle for custom routes or Object[] for auto-routes
Naviganto.get().routeTo(this, new Route<>(DetailRoute.class, bundle));

// Or if you need a result for your previous Activity
Naviganto.get().routeTo(this, new Route<>(DetailRoute.class, bundle, requestCode));
```

```java
// Navigate to a View; Bundle for custom routes or Object[] for auto-routes
Naviganto.get().routeTo(this, new Route<>(ProductRoute.class, bundle, placeholder));

// Or if you need a result for your previous Activity
Naviganto.get().routeTo(this, new Route<>(ProductRoute.class, bundle, placeholder, requestCode));
```

As we can see the only difference is that if we need to navigate to a `View`, we need to serve a placeholder.
Besides this, in our `Activity` have to specify the following (to enable "back button" navigation):

```java
@Override public void onBackPressed() {
	if (!Naviganto.get().back(this))
		super.onBackPressed();
}
```

In all the above cases should be understood `this` as `Context`

##### 2. Mark the target view as Route

Finally, to implement a route, there are two ways to use this library since version 2.0:

1 - Use auto-routes (remember to rebuild to generate the routes):

```java
@RoutableActivity(params = {...}, requestCode = ...) // for Activities {parameter types separated by commas}, define requestCode as -1 if you don't need it (generates SomeActivityRoute.java)
public class SomeActivity extends AppCompatActivity {

}

@RoutableView(params = {...}, requestCode = ...) // for Views {Parameter types separated by commas}, define requestCode as -1 if you don't need it (generates SomeViewRoute.java)
class SomeView extends FrameLayout {

}
```

2 - Or implement your custom routes from the `Routable` like this:

```java
public class MyCustomRoute<C extends Context, B extends Bundle> implements Routable<C, B> {

	@Override public void route(@NonNull Context context, @NonNull Bundle parameters, @Nullable Object viewParent)
		    throws ParameterNotFoundException, NotEnoughParametersException {
		// How to opening our Activity or View
	}
}
```

Sample `Auto-route` and `Routable` implementations:

[Auto-route for Activity](https://github.com/FireZenk/Naviganto/blob/master/sample/src/main/java/org/firezenk/naviganto/sample/detail/DetailActivity.java)

[Implementation for Activity](https://github.com/FireZenk/Naviganto/blob/master/sample/src/main/java/org/firezenk/naviganto/sample/home/HomeRoute.java)

[Auto-route for View](https://github.com/FireZenk/Naviganto/blob/master/sample/src/main/java/org/firezenk/naviganto/sample/product/ProductView.java)

[Implementation for View](https://github.com/FireZenk/Naviganto/blob/master/sample/src/main/java/org/firezenk/naviganto/sample/profile/ProfileRoute.java)

### ADDITIONAL NOTES

- No, it is not contemplated the use of fragments, although it is possible (using `View` sample)
- I recommend to use auto-routes (available since version 2.0) because you can avoid to use `Parcelables`
- User `.clearHistory()` if you need to clear all the navigation history
- There is some more self documented [functions here](https://github.com/FireZenk/Naviganto/blob/master/library/src/main/java/org/firezenk/naviganto/library/INaviganto.java)
- For more info an samples, see `sample` module


### CHANGES

[See CHANGES.md](https://github.com/FireZenk/Naviganto/blob/master/CHANGES.md)
