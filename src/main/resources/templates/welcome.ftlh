<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome </title>
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
        <h1> <font color="red"  face="Marker Felt">Добро пожаловать в игру Holidays!</font></h1>
        <div class = "text"><b>
            В игре эмулируется проведение праздников с накоплением очков для каждого участника,
            в зависимости от вклада в праздник и типа праздника. Каждое участие в празднике приносит игрокам очки.
            Количество игроков и праздников ограниченно, задается предустановками.
            </b>
        </div>
        <div >
            <form name="welcome" action="begin" method="GET">
             <p> <input type="submit" value="Начать"/> </p>
            </form>
         </div>
     </div>
</body>
</html>