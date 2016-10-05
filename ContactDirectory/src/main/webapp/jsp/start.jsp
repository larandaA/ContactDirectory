<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Contact Directory</title>
    <link rel="stylesheet" href="css/wrap.css">
    <link rel="stylesheet" href="css/grid.css">
</head>
<body>
    <header>
        <div class="header-title">
            <span class="app-title"><a href="">Contact directory</a></span>
            <span class="section-title">
                    <span class="title-separator">-</span>
                    Start page
                </span>
        </div>
    </header>
    <div id="content">
        <div class="home">
            <h1>Contact directory</h1>
            <p class="home-subtitle">Keep your contacts here.
                <br /> Made by Alexandra Ryzhevich.
            </p>
        </div>
        <div class="white-block">
            <div class="cont-row">
                <div class="cont-cell-5"></div>
                <div class="cont-cell-2">
                    <form>
                        <input type="hidden" name="page" value="1">
                        <button formaction="ContactList" class="btn btn-start">Start</button>
                    </form>
                </div>
                <div class="cont-cell-5"></div>
            </div>
        </div>
    </div>
    <div id="pre-footer"></div>
    <footer>
        <div class="footer-info">
            by Alexandra Ryzhevich
        </div>
    </footer>
</body>
</html>