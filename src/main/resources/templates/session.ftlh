<#import "ui.ftlh" as ui/>

<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Игровая сессия</title>
   </head>
   <body>
        <div>
            <fieldset>
                <legend><h3>Установочные параметры сессии</h3></legend>
                <form name="parameters" action="save" method="POST">
                    <@ui.formInput id="t2" name="sessionPlayers" label="Количество игроков"
                        value="${parameters.sessionPlayers ! 0}"/> <br/>
                    <@ui.formInput id="t3" name="sessionDuration" label="Длительность сессии, суток"
                        value="${parameters.sessionDuration ! 0}"/> <br/>
                    <@ui.formInput id="t4" name="playersAddshotChance" label="Вероятность дополнительных попыток, %"
                        value="${parameters.playersAddshotChance ! 0}"/> <br/>
                    <@ui.formInput id="t5" name="playersAddshotMin" label="Минимальное количество дополнительных попыток"
                        value="${parameters.playersAddshotMin ! 0}"/> <br/>
                    <@ui.formInput id="t6" name="playersAddshotMax" label="Максимальное количество дополнительных попыток"
                        value="${parameters.playersAddshotMax ! 0}"/> <br/>
                    <@ui.formInput id="t7" name="playersNumberAddshot" label="Количество игроков с шансом на дополнительные попытки"
                        value="${parameters.playersNumberAddshot ! 0}"/> <br/>
                    <@ui.formInput id="t8" name="holidaySampleFreq" label="Частота дискретизации, мин"
                        value="${parameters.holidaySampleFreq ! 0}"/> <br/>
                    <@ui.formInput id="t9" name="holidayFillChance" label="Вероятность заполнения праздника, %"
                        value="${parameters.holidayFillChance ! 0}"/> <br/>
                    <@ui.formInput id="t10" name="holidayPushChance" label="Вероятность смещения игрока с праздника ,%"
                        value="${parameters.holidayPushChance ! 0}"/> <br/>
                    <@ui.formInput id="t11" name="holidaySimpleChance" label="Вероятность простого праздника ,%"
                        value="${parameters.holidaySimpleChance ! 0}"/> <br/>
                    <@ui.formInput id="t12" name="holidayBanquetChance" label="Вероятность пиршенства ,%"
                        value="${parameters.holidayBanquetChance ! 0}"/> <br/>
                    <@ui.formInput id="t13" name="holidayDinnerChance" label="Вероятность ужина ,%"
                        value="${parameters.holidayDinnerChance ! 0}"/> <br/>

                        <input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}" />
                    <input type="submit" value="Сохранить параметры" />
                </form>
            </fieldset>
        </div>
        <div>
                    <fieldset>
                        <legend><h3>Новая сессия</h3></legend>
                        <form name="parameters" action="start_session" method="GET">

                            <input type="submit" value="Начать новую сессию" />
                        </form>
                    </fieldset>
                </div>
   </body>
</html>