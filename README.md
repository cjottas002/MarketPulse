# MarketPulse

**MarketPulse** es una aplicación móvil de e-commerce desarrollada en Android, diseñada para ofrecer una experiencia de compra en línea fluida y eficiente. La aplicación permite a los usuarios navegar por productos, añadirlos al carrito, realizar compras y gestionar sus cuentas. También incluye una interfaz de administración para gestionar productos y usuarios.

## Características

- **Navegación de Productos**: Los usuarios pueden explorar una amplia gama de productos disponibles en la tienda.
- **Carrito de Compras**: Los usuarios pueden agregar productos a su carrito y proceder a la compra.
- **Gestión de Usuarios**: Funcionalidad de registro e inicio de sesión para usuarios y administradores.
- **Panel de Administración**: Los administradores pueden agregar, editar o eliminar productos, así como gestionar usuarios.
- **Persistencia de Datos**: Implementación de Room para la persistencia local de datos.
- **Arquitectura MVVM**: La aplicación sigue el patrón de arquitectura MVVM para una mejor separación de responsabilidades y testabilidad.
- **Inyección de Dependencias**: Uso de Dagger Hilt para manejar la inyección de dependencias.

## Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación principal.
- **Android Jetpack**: 
  - **ViewModel**: Para la gestión del ciclo de vida de la interfaz de usuario y la persistencia de datos.
  - **LiveData**: Para la comunicación reactiva entre ViewModels y las vistas.
  - **Room**: Base de datos SQLite para almacenamiento persistente.
  - **Navigation Component**: Para manejar la navegación entre fragmentos.
- **Dagger Hilt**: Framework de inyección de dependencias.
- **Retrofit**: Cliente HTTP para la comunicación con APIs.
- **Glide**: Biblioteca de carga de imágenes.


## Librerías Utilizadas

### Core Libraries
- `androidx.core:core-ktx`: Extensiones de Kotlin para Android.
- `androidx.appcompat:appcompat`: Compatibilidad hacia atrás para componentes de UI.
- `com.google.android.material:material`: Componentes de diseño de materiales de Google.
- `androidx.activity:activity-ktx`: Extensiones de Kotlin para la gestión de Activities.
- `androidx.constraintlayout:constraintlayout`: Layout para diseñar interfaces de usuario```markdown
## Librerías Utilizadas

### Core Libraries
- `androidx.core:core-ktx`: Extensiones de Kotlin para Android.
- `androidx.appcompat:appcompat`: Compatibilidad hacia atrás para componentes de UI.
- `com.google.android.material:material`: Componentes de diseño de materiales de Google.
- `androidx.activity:activity-ktx`: Extensiones de Kotlin para la gestión de Activities.
- `androidx.constraintlayout:constraintlayout`: Layout para diseñar interfaces de usuario flexibles.

### Hilt para Inyección de Dependencias
- `com.google.dagger:hilt-android`: Framework de inyección de dependencias.
- `com.google.dagger:hilt-compiler`: Procesador de anotaciones para Hilt.

### Retrofit para Peticiones de Red
- `com.squareup.retrofit2:retrofit`: Biblioteca para manejar peticiones HTTP.
- `com.squareup.retrofit2:converter-gson`: Conversor para manejar JSON con Gson.

### OkHttp para Peticiones HTTP
- `com.squareup.okhttp3:okhttp`: Cliente HTTP eficiente.
- `com.squareup.okhttp3:logging-interceptor`: Interceptor para logs de peticiones HTTP.

### Room para la Base de Datos
- `androidx.room:room-runtime`: Biblioteca de persistencia de datos.
- `androidx.room:room-compiler`: Procesador de anotaciones para Room.
- `androidx.room:room-ktx`: Extensiones de Kotlin para Room.

### DataStore para Preferencias
- `androidx.datastore:datastore-preferences`: Manejo de preferencias de usuario.

### Carga de Imágenes
- `io.coil-kt:coil`: Biblioteca para cargar imágenes de manera eficiente.

### Librerías de Lifecycle
- `androidx.lifecycle:lifecycle-livedata-ktx`: Extensiones de LiveData para Kotlin.
- `androidx.lifecycle:lifecycle-viewmodel-ktx`: Extensiones de ViewModel para Kotlin.

### Componente de Navegación
- `androidx.navigation:navigation-fragment-ktx`: Extensiones de Kotlin para navegación entre fragmentos.
- `androidx.navigation:navigation-ui-ktx`: Extensiones de Kotlin para manejar la interfaz de usuario con la navegación.

### Coroutines para Operaciones Asíncronas
- `org.jetbrains.kotlinx:kotlinx-coroutines-core`: Soporte para corrutinas en Kotlin.
- `org.jetbrains.kotlinx:kotlinx-coroutines-android`: Integración de corrutinas con Android.

### Serialización para JSON
- `org.jetbrains.kotlinx:kotlinx-serialization-json`: Biblioteca de serialización para JSON.

### Librerías de Testing
- `junit:junit`: Biblioteca para realizar pruebas unitarias.
- `androidx.test.ext:junit`: Extensiones de JUnit para pruebas en Android.
- `androidx.test.espresso:espresso-core`: Framework para pruebas de UI en Android.
