# Lector de Códigos de Barras Inalámbrico

Este proyecto consiste en un **lector de códigos de barras inalámbrico** desarrollado en **Java** para Android y PC. La app escanea códigos de barras con un dispositivo Android y envía los datos en tiempo real a una PC a través de una red Wi-Fi o cableada. Ideal para **sistemas de ventas (POS)** en negocios que buscan una alternativa económica y funcional a los escáneres comerciales.

## Requisitos

- **PC** con **Java** instalado para ejecutar el servidor (`servidor.jar`)
- **Dispositivo Android** para instalar la app
- Ambos dispositivos deben estar conectados a **la misma red Wi-Fi o cableada**

## Guía de Instalación y Uso

### 1. Ejecutar el Servidor en la PC

1. Asegúrate de tener **Java** instalado en la PC
2. Descarga el archivo `servidor.jar` y ejecútalo

### 2. Instalar la App en Android

- Descarga e instala la app en tu dispositivo Android
- Conecta el móvil a la misma red que la PC

### 3. Conectar la App al Servidor

- Abre la app y ve a **Configuración**
- Escanea el QR que muestra el servidor en la PC
- Guarda la configuración

### 4. Escanear Códigos

- Usa la cámara para escanear códigos de barras
- Los datos llegarán automáticamente a la PC

### Solución de Problemas

Si hay errores de conexión:

- Verifica que ambos dispositivos estén en la misma red
- Revisa que el firewall no bloquee el puerto 8000
- Reinicia ambas aplicaciones

### Notas Importantes

- El servidor debe permanecer abierto durante el uso
- La primera conexión puede demorar unos segundos
- Para mejor rendimiento, usa conexión cableada en la PC

## Imágenes

| Imagen 1 | Imagen 2 | Imagen 3 |
|----------|----------|----------|
| ![Imagen 1](capturas/2.jpeg) | ![Imagen 2](capturas/2.png) | ![Imagen 3](capturas/3.jpeg) |

| Imagen 4 | Imagen 5 | Imagen 6 |
|----------|----------|----------|
| ![Imagen 4](capturas/4.jpeg) | ![Imagen 5](capturas/5.jpeg) | ![Imagen 6](capturas/6.jpeg) |


