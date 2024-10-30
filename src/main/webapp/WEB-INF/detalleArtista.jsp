<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalle del Artista</title>
</head>
<body>
    <h2>Detalle del Artista</h2>

    <p><strong>Nombre:</strong> ${artista.nombre} ${artista.apellido}</p>
    <p><strong>Biografía:</strong> ${artista.biografía}</p>

    <h3>Canciones del Artista</h3>
    <ul>
        <c:forEach var="cancion" items="${artista.canciones}">
            <li>${cancion.titulo}</li>
        </c:forEach>
    </ul>

    <a href="/artistas">Volver a lista de artistas</a>
</body>
</html>