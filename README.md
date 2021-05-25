![Sonarqube result](https://i.imgur.com/Ecedyc9.png)

Backend test
=
Este proyecto es una aplicación simple REST. Utiliza Spring Boot, Spring Data JPA, Lombok y otros utilitarios.
Actualmente, la aplicación puede traer cuentas bancarias ejecutando un request GET en `/accounts/{id}`, donde `{id}` es
el identificador de la cuenta. Su tarea es añadir transacciones bancarias, la aplicación debe soportar estos requests:

    POST at /accounts/{1}/transactions - which should save a new transaction for account with passed {id}
    GET at /accounts/{1}/transactions - which should return all transactions for an account with passed {id}

Para ello usted debe cumplir con lo siguiente:

- En el servicio `AccountService` debe implementar los métodos `getTransactionsForAccount()` y `addTransaction()`.
    - Mire los comentarios de cada método, hay un `javadoc` que describe el comportamiento esperado.
- Hay algunas pruebas unitarias que actualmente fallan, y su solución debe pasar esas pruebas.
- Recuerde que las pruebas verifican el funcionamiento de la aplicación, pero no olvide cumplir con las **mejores
  prácticas** de desarrollo.
- Escribir una prueba de integración nueva.
- En una de las clases de `src/main/java` existe una anotación incorrecta que debe detectar y corregir.
- Puede refactorizar el código para cumplir con el principio Single-responsability?
    - Para ello. **No puede cambiar las pruebas unitarias existentes** pero si puede llevarlas a otra clase.
- Extra: utilizar logs.

Siga las convenciones utilizadas en este proyecto y recuerde **No puede cambiar las pruebas unitarias existentes**.

Siéntase libre de crear archivos y modificar los existentes. Puede agregar dependencias si lo desea, para hacerlo
modifique el `pom.xml`.
