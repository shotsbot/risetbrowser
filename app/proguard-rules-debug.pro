# ProGuard rules for development/debugging
-dontobfuscate
-dontoptimize

# Keep source file names for stack traces
-keepattributes SourceFile,LineNumberTable

# Keep debugging information
-keepattributes *Annotation*,InnerClasses

# Keep method parameter names
-keepattributes MethodParameters
