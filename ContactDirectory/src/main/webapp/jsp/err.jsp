<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="css/wrap.css">
    <link rel="stylesheet" href="css/grid.css">
    <link rel="stylesheet" href="css/inputs.css">
</head>
<body>
    <header>
        <div class="header-title">
            <span class="app-title"><a href="ContactList">Contact directory</a></span>
            <span class="section-title">
                    <span class="title-separator">-</span>
                    Error
                </span>
        </div>
    </header>
    <div id="content">
        <div class="purple-block">
            <h1>Error</h1>
            <p class="home-subtitle"> ${errorMessage} </p>
        </div>
        <div class="white-block">
            <br /><br /><br /><br /><br /><br /><br />
        </div>
    </div>
    <footer>
        <div class="footer-info">
            by Alexandra Ryzhevich
        </div>
    </footer>
</body>
</html>