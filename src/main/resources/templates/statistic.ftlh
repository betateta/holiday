<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "css/style.css">
    <title>start session page</title>
</head>
<body>
    <h2>Статистика расчета</h2>
     <div>
        <form name="" action="back" method="GET">
            <input type="submit" value="<- Создать новую сессию" />
        </form>
     </div>

    <div class="container">
        <div class="layer1">
             <table border="1px" cellspacing="2" border="1" cellpadding="5">
             <caption><h3>Параметры сессии</h3></caption>
                <tr>
                    <td><b>Пользователь</b></td>
                    <td>${user.username}</td>
                </tr>
                <tr>
                    <td><b>Дата начала</b></td>
                    <td>${session.startTime}</td>
                </tr>
                <tr>
                    <td><b>Дата завершения</b></td>
                    <td>${session.stopTime}</td>
                </tr>
                <tr>
                    <td><b>Длительность, суток</b></td>
                    <td>${session.sessionDuration}</td>
                </tr>
                <tr>
                    <td><b>Всего игроков</b></td>
                    <td>${session.sessionPlayers}</td>
                </tr>
                <tr>
                    <td><b>Всего праздников</b></td>
                    <td>${session.numberOfHolidays}</td>
                </tr>
                <tr>
                    <td><b>Незаконченных праздников</b></td>
                    <td>${session.numberOfIncompleteHolidays}</td>
                </tr>
                <tr>
                    <td><b>Набранных очков</b></td>
                    <td>${session.points}</td>
                </tr>
                <tr>
                    <td colspan="2">
                            <input class="hide" id="hd-2" type="checkbox">
                            <label for="hd-2">Список игроков</label>
                            <div>
                                <table border="1px" cellspacing="2" border="1" cellpadding="5">
                                <caption><h3>Список игроков</h3></caption>
                                     <tr>
                                        <th>Имя игрока</th>
                                        <th>Количество очков</th>
                                        <th>Количество доп. попыток</th>
                                    </tr>
                                    <#list players as player>
                                        <tr>
                                            <td>${player.name}</td>
                                            <td>${player.sessionPoints}</td>
                                            <td>${player.bonusShots}</td>
                                        </tr>
                                    </#list>
                                </table>
                            </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input class="hide" id="hd-1" type="checkbox">
                        <label for="hd-1">Установочные параметры</label>
                        <div>
                            <table border="1px" cellspacing="1" border="1" cellpadding="5">
                              <tr>
                                <td>Вероятность дополнительных попыток, %</td>
                                <td>${parameters.playersAddshotChance}</td>
                              </tr>
                              <tr>
                                <td>Минимальное количество дополнительных попыток</td>
                                <td>${parameters.playersAddshotMin}</td>
                              </tr>
                              <tr>
                                <td>Максимальное количество дополнительных попыток</td>
                                <td>${parameters.playersAddshotMax}</td>
                              </tr>
                              <tr>
                                <td>Количество игроков с шансом на дополнительные попытки</td>
                                <td>${parameters.playersNumberAddshot}</td>
                              </tr>
                              <tr>
                                <td>Частота дискретизации, мин</td>
                                <td>${parameters.holidaySampleFreq}</td>
                              </tr>
                                <td>Вероятность заполнения праздника, %</td>
                                <td>${parameters.holidayFillChance}</td>
                              </tr>
                              <tr>
                                <td>Вероятность смещения игрока с праздника ,%</td>
                                <td>${parameters.holidayPushChance}</td>
                              </tr>
                              <tr>
                                <td>Вероятность простого праздника ,%</td>
                                <td>${parameters.holidaySimpleChance}</td>
                              </tr>
                              <tr>
                                <td>Вероятность пиршенства ,%</td>
                                <td>${parameters.holidayBanquetChance}</td>
                              </tr>
                              <tr>
                                <td>Вероятность ужина ,%</td>
                                <td>${parameters.holidayDinnerChance}</td>
                              </tr>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">  <img src="http://localhost:8080/getImgAsBytes/1.jpeg" /> </td>
                </tr>
                <tr>
                    <td colspan="2">  <img src="http://localhost:8080/getImgAsBytes/2.jpeg" /> </td>
                </tr>
             </table>
        </div>


        <div class="layer3">
            <table border="1px" cellspacing="2" border="1" cellpadding="5">
            <caption><h3>Список праздников</h3></caption>
                 <tr>
                    <th>ID</th>
                    <th>Время начала</th>
                    <th>Время конца</th>
                    <th>Тип праздника</th>
                    <th>Вместимость</th>
                    <th>Количество очков</th>
                    <th>Уникальных посетителей</th>
                </tr>
                <#list calculates as calculate>
                    <tr>
                        <td>${calculate.id}</td>
                        <td>${calculate.startTime}</td>
                        <td>${calculate.stopTime}</td>
                        <td>${calculate.holiday.name}</td>
                        <td>${calculate.capacity}</td>
                        <td>${calculate.points}</td>
                        <td>${calculate.uniqPlayersNumber}</td>
                        <tr>
                            <td colspan="7">
                                <input class="hide" id="hc-${calculate.id}" type="checkbox">
                                <label for="hc-${calculate.id}">Список участников</label>
                                <div>
                                    <table border="1px" cellspacing="1" border="1" cellpadding="5">
                                        <tr>
                                            <th>Имя игрока</th>
                                            <th>Вход</th>
                                            <th>Выход</th>
                                            <th>Время пребывания, сек</th>
                                            <th>Очков</th>
                                            <th>Организатор</th>
                                        </tr>
                                        <#list calculate.memberSet as member>
                                            <tr>
                                                <td>${member.player.name}</td>
                                                <td>${member.inputTime}</td>
                                                <td>${member.outputTime}</td>
                                                <td>${member.duration}</td>
                                                <td>${member.holidayPoints}</td>
                                                <#if member.isOrganizator == true>
                                                    <td bgcolor="green">${member.isOrganizator?string('да', 'нет')}</td>
                                                <#else>
                                                    <td> ${member.isOrganizator?string('да', 'нет')}</td>
                                                </#if>
                                            </tr>
                                        </#list>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </tr>
                </#list>
            </table>
        </div>
    </div>

</body>
</html>