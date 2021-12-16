<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
      <#include "css/style.css">
    <style>
    body{
        background-color: #3366CC; /* Цвет фона веб-страницы */
    }
    h1 {
       background-color: RGB(249, 201, 16); /* Цвет фона под заголовком */
       border-radius: 5px;
      }
    .welcome {
          text-align:  center;
        }
    .text {
        border: 4px outset maroon;
        border-radius: 10px;
        padding: 10px;
        color: white;
        background-color: maroon;
       }
      </style>
</head>
<body>
    <div class ="welcome">
        <h1> <font color="red"  face="Marker Felt">${title}</font></h1>
        <div class = "text"><b>${text}</b>
        </div>
        <div >
            <form name="welcome" action="begin" method="GET">
             <p> <input type="submit" value=${button}> </p>
            </form>
         </div>
     </div>
</body>
</html>