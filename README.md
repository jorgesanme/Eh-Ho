## Descripción
Eh-Ho es una app que consume los datos desde la API de [Discourse](https://docs.discourse.org/#tag/Topics/paths/~1posts.json/post)
 


## Explicación de la app:
Esta app muestra los Topic del foro Discourse en un RecycleView.

Previamente el usuario tiene que hacer LogIn o SignIn. Para ello se le muestras las pantallas adecuadas.


>Se usa una arquitectura MVVM. 

Para ello se ha usado:

- **Okhttp3** + logging-interceptor
- **Binding** => para vincular las view
- **Picasso**  => carga las imagenes desde http
- **DIProvider** => Injección de dependencias
- **SwipeRefreshLayout** => Recargar la vista con Pull to Request. 


## Imagenes de muestra
#### LogIn  | LogIn-Check | SignIn  | Topics |
<img src="https://github.com/jorgesanme/Eh-Ho/blob/main/images/eh-ho_1.png" width="160" height="300" />|
<img src="https://github.com/jorgesanme/Eh-Ho/blob/main/images/eh-ho_3.png" width="160" height="300" />|
<img src="https://github.com/jorgesanme/Eh-Ho/blob/main/images/eh-ho_2.png" width="160" height="300" />|
<img src="https://github.com/jorgesanme/Eh-Ho/blob/main/images/eh-ho_1.gif" width="160" height="300" />
