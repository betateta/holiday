<#import "/spring.ftl" as spring/>

<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
        <#include "css/style.css">
      <title>User information</title>
   </head>
   <body>
         <h2>Пользователь : ${name}</h2>
         <div>
                 <table border="1px" cellspacing="2" border="1" cellpadding="5">
                        <tr>
                            <td>Идентификатор</td>
                            <td>${id}</td>
                        </tr>
                         <tr>
                             <td>Права</td>
                             <td>
                             <#list roles as role>
                                ${role.rolename},
                             </#list>
                             </td>
                         </tr>
                       <tr>
                            <td colspan="2">
                                <input class="hide" id="hd-1" type="checkbox">
                                <label for="hd-1">Показать список сессий</label>
                                <div>
                                    <table border="1px" cellspacing="1" border="1" cellpadding="5">
                                        <tr>
                                            <th>Время начала</th>
                                            <th>Время конца</th>
                                            <th>Длительность</th>
                                            <th>Игроков</th>
                                            <th>Праздников</th>
                                            <th>Очков</th>
                                        </tr>
                                        <#list sessions as session>
                                            <tr>
                                                <td>${session.startTime}</td>
                                                <td>${session.stopTime}</td>
                                                <td>${session.sessionDuration}</td>
                                                <td>${session.sessionPlayers}</td>
                                                <td>${session.numberOfHolidays}</td>
                                                <td>${session.points}</td>
                                            </tr>
                                        </#list>
                                    </table>
                                </div>
                            </td>
                        </tr>
                 </table>
         </div>
         <div>
            <form name="users_list" action="userslist" method="GET">
                <p> <input type="submit" value="Список пользователей" /> </p>
            </form>
         </div>
         <div>
            <form name="session" action="session" method="GET">
                <p> <input type="submit" value="Создать новую сессию ->" /> </p>
            </form>
         </div>
   </body>
</html>