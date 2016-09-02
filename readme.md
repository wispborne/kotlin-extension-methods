# Kotlin Extension Methods

A few extension methods that I use and have been found to be very useful.

## Usage
### exception.hasCause
```kotlin
try {
  throw Exception(RuntimeException())
} catch (e: Exception) {
  Assert.isTrue(e.hasCause(RuntimeException.javaClass))
}
```

### exception.hasHandlingActivity
```kotlin
if(intent.hasHandlingActivity(packageManager)) {
  startActivity(intent)
} else {
  // User can't launch Intent
}
```

### any.simpleClassName
```kotlin
Assert.equals("RuntimeException", RuntimeException().simpleClassName)
```

### String.empty
```kotlin
Assert.equals("", String.empty)
```

### String.getOrNullIfBlank
```kotlin
Assert.equals(null, "".getOrNullIfBlank)
Assert.equals("hello", "hello".getOrNullIfBlank)
```

### String.isNotNullOrBlank
```kotlin
Assert.isTrue("hello".isNotNullOrBlank())
```
