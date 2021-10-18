<#import "/spring.ftl" as spring/>

<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
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
                            <td>Набранные очки</td>
                            <td>${points}</td>
                        </tr>

                 </table>
         </div>
         <div>
            <form name="users_list" action="userslist" method="GET">
                <p> <input type="submit" value="Список пользователей" /> </p>
            </form>
         </div>
         <div>
            <form name="new_session" action="session" method="GET">
                <p> <input type="submit" value="Создать новую сессию" /> </p>
            </form>
         </div>
   </body>
</html>