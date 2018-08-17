# movieDB

## CAPAZ  DE LA APP

###  DATA :
Esta capa es la encargada de proveer de informacion.Provee datos locales y datos remotos de la api.Normalmente hay una capa extra llamada repositorio ej :MovieRepository ,"CategorieRepository" y estas se encargan del manejo de datos locales y remotos de una manera mas eficiento si se implementa con RxJava por ejemplo.Nota,por temas de tiempo no implemente room.

#### Clases: 
###### ListMoviesResponseWrapper-> Funciona como un wrapper para respuestas de la api .Funciona por ej para paginar una respuesta.
###### SearchMoviesResponseWrapper -> Igual que ListMoviesResponseWrapper pero con busquedas.
###### RetrofitManager -> Clase encargada de proveer a la aplicacion con una instancia de retrofit para poder realizar requests.
###### MovieService-> En ella se expone los endpoints de la api.Tambien se declara el tipo de valor a retornar.
###### SharedPrefercesHelper-> Clase que nos ayuda a poder guardar y obtener datos locales.

### MODEL:
En esta capa declaro todos los objetos como tales.
#### Clases:
###### GenreResponse-> Funciona como un wrapper al igual que ListMoviesResponseWrapper y SearchMoviesResponseWrapper
###### Genre y Movie -> Objectos de la aplicacion.

### UI:
#### Capa encargada de mostrar las vistas en android.Lo separe for feature ->pantalla home,detail, searchOffline.En las tres categorias se econtrara el siguiente comportamiento
###### FeatureActivity-> En ella se escuchan eventos .Tambien es la encargada de enviar "mensajes" al presentador.
###### FeatureContract->En ella se exponen y se declarn las interfaces a implementar en presentador y la vista.
###### FeaturePreseter->Clase que escucha hace de puente los "mensajes" de la actividad ,realiza una accion y le notifica nuevos valores a su actividad


#### 1. En qué consiste el principio de responsabilidad única? Cuál es su propósito? 
##### Proveniente de la primer "s" de SOLID PRINCIPLE .Este principio nos dice que cada clase tiene que tener una sola responsabilidad y debe tener una razon para ser modificada.Su proposito es ayudar a no tener objetos y clases enormes con miles de lineas de codigo que termina siendo una clase "dios" que se encarga de muchas tares.Se vuelve poco escalable y mantenible.

#### 2. Qué características tiene, según su opinión, un “buen” código o código limpio? 
###### En mi opinion un buen codigo ,debe estar organizado bajo alguna arquitectura.Ya sea mvp,mvvm,flux.Tambien creo debe ser un codigo leegible (puede tambien ser comentado),modularizado.Debe tener variables con nombres representativos y seguir algun tipo de tipo de escritura.Debe poder ser testeable facilmente y para esto tomar buenas practicas como separar el framework de android (fragments,actvididade,views,layouts...)y mantenerlo dentro de su actividad o fragment y no andar modificandolos en otras clases.




