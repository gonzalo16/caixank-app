# BankingApp
<img src="https://github.com/gonzalo16/BankingApp/blob/main/Backend.png"></img>
<div align="center">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=flat&logo=spring&logoColor=white" height="25" alt="spring logo"  />
  <img width="12" />
  <img src="https://img.shields.io/badge/mysql-4479A1.svg?style=flat&logo=mysql&logoColor=white" height="25" alt="mysql logo"  />
</div>


Este repositorio forma parte de un proyecto de un desafío de <a href="https://nuwe.io/">NUWE<a/> en crear una aplicación financiera del banco CaixaBank usando Spring.
Quiero destacar que el desafío finalizó el 09/01/2025 , yo en mi caso me tomé el desafio como una forma de aprender y practicar conocimientos en desarrollo backend y con el framework Spring.

Debido a que actualmente estoy realizando un curso presencial de SAP, voy realizando los puntos requeridos que indican en el proyecto y cada punto que tenga realizado lo iré colocando con ✅ y los que tenga pendientes por hacer los indicaré con 🕘 (pendiente).

Este es mi primer repositorio "serio" que quiero crear y mantenerlo mediante las actualizaciones que vaya haciendo al proyecto/desafio y documentandolo de la mejor manera posible.

# Background
Las finanzas digitales evolucionan, CaixaBank se dedica a ampliar los límites de las soluciones bancarias seguras e innovadoras.  En este desafío, usted asume el papel de desarrollador encargado de crear funciones esenciales para una aplicación de servicios financieros, centrándose en la implementación práctica de una sólida gestión de cuentas, seguridad de las transacciones y eficiencia operativa.


# Tareas 📝
- Tarea 1: Crear un dockerfile y health check.
- Tarea 2: Acciones de usuario.
- Tarea 3: Transacciones de cuentas, monitoreo y validaciones.
- Tarea 4: Invertir cuenta.
- Tarea 5: Seguridad y manejo de errores.


## Tarea 1: Crear un dockerfile y comprobar estado de salud. 🕘
La primera cosa es configurar un archivo Dockerfile para poder probar el contenedor de la aplicación.
**Health check** es un endpoint para la verificación del estado de salud del contenedor Docker.

## Tarea 2: Acciones de usuario.
En esta tarea tendremos que crear los distintos endpoints para el registro y login del usuario, en la siguiente tabla se muestran los protocolos, parametros y respuesta de los endpoints.

|  **Endpoint** | **Method**  | **Params/body**  | **Requi auth**  | **Resp cod**  | **Desc**     |
| ------------ | ------------ | ------------ | ------------ | ------------ | ------------ |
| /users/register  | POST  | {name,password,email}  | No  | 200,400("Email already exist") | Register new user |
| /users/login  | POST  | {id,password}  | No  | 200,401("Bad credentials") | Login user return JWT |
| /users/logout  | GET  | N/A    | SI  | 200,401("Access denied") | Desloguea el usuario e invalida el JWT |
| /dashboard/user  | GET  | N/A  | SI  | 200,401("Access denied") | Recupera la informacion del usuario |
| /dashboard/account  | GET  | N/A  | SI  | 200,401("Access denied") | Recupera la informacion principal de la cuenta incluida el balance |
| /dashboard/account/{index}  | GET  | {index} | SI  | 200,401,404 | Recupera la informacion principal de la cuenta pasada por parametro |
| /account/create | POST  | {accountNumber,accountType} | SI  | 200,400 | Crea una nueva cuenta para el usuario usando el numero de cuenta principal y el tipo de cuenta |
| /account/deposit  | POST  | {amount}  | SI  | 200,401("Access denied") | Deposita una cantidad específica en la cuenta del usuario con las tarifas aplicables |
| /account/withdraw  | POST  | {amount}  | SI  | 200,401("Access denied") | Retira una cantidad específica a la cuenta del usuario con las tarifas aplicables |
| /account/fund-transfer  | POST  | {targetAccountNumber}  | SI  | 200,401("Access denied") | Transfiere fondos a otra cuenta, con detección de fraude si corresponde |
