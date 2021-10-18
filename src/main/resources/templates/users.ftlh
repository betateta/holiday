<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Users list</title>
</head>
<body>
         <div>
                 <table border="1px" cellspacing="2" border="1" cellpadding="5">
                        <tr>
                            <td>Идентификатор</td>
                            <td>Имя пользователя</td>
                            <td>Набранные очки</td>
                        </tr>
                             <#list users as user>
                                <tr>
                                  <td>${user.id}</td>
                                    <td>${user.username}</td>
                                    <td>${user.points}</td>
                                </tr>
                             </#list>
                 </table>
         </div>

</body>
</html>
