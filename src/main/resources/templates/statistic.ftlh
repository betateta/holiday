<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "css/statistic.css">
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
                    <td>Пользователь</td>
                    <td>${user.username}</td>
                </tr>
                <tr>
                    <td>Дата начала</td>
                    <td>${session.startTime}</td>
                </tr>
                <tr>
                    <td>Дата завершения</td>
                    <td>${session.stopTime}</td>
                </tr>
                <tr>
                    <td>Всего игроков</td>
                    <td>${session.sessionPlayers}</td>
                </tr>
                <tr>
                    <td>Всего праздников</td>
                    <td>${session.numberOfHolidays}</td>
                </tr>
                <tr>
                    <td>Незаконченных праздников</td>
                    <td>${session.numberOfIncompleteHolidays}</td>
                </tr>
                <tr>
                    <td>Набранных очков</td>
                    <td>${session.points}</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input class="hide" id="hd-1" type="checkbox">
                        <label for="hd-1">Показать детали</label>
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
             </table>
        </div>
        <div class="layer2">
            <table border="1px" cellspacing="2" border="1" cellpadding="5">
            <caption><h3>Список игроков</h3></caption>
                 <tr>
                    <td>Имя игрока</td>
                    <td>Количество очков</td>
                </tr>
                <#list players as player>
                    <tr>
                        <td>${player.name}</td>
                        <td>${player.sessionPoints}</td>
                    </tr>
                </#list>
            </table>
        </div>
        <div class="layer3">
            <table border="1px" cellspacing="2" border="1" cellpadding="5">
            <caption><h3>Список праздников</h3></caption>
                 <tr>
                    <td>Время начала</td>
                    <td>Время конца</td>
                    <td>Тип праздника</td>
                    <td>Вместимость</td>
                    <td>Количество очков</td>
                    <td>Уникальных посетителей</td>
                </tr>
                <#list calculates as calculate>
                    <tr>
                        <td>${calculate.startTime}</td>
                        <td>${calculate.stopTime}</td>
                        <td>${calculate.holiday.name}</td>
                        <td>${calculate.capacity}</td>
                        <td>${calculate.points}</td>
                        <td>${calculate.uniqPlayersNumber}</td>

                    </tr>
                </#list>
            </table>
        </div>

    </div>


</body>
</html>