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
                <legend>Установочные параметры сессии</legend>
                <form name="session properties" action="save" method="POST">
                    <@ui.formInput id="t1" name="username" label="Пользователь" value="${user.username ! none}"/> <br/>
                    <@ui.formInput id="t2" name="session_players" label="Количество игроков" value="${parameters.sessionPlayers ! 0}"/> <br/>
                    <@ui.formInput id="t3" name="session_duration" label="Длительность сессии" value="${parameters.sessionDuration ! 0}"/> <br/>
                    <@ui.formInput id="t4" name="players_addshot_chance" label="Вероятность дополнительных попыток, %" value="${parameters.playersAddshotChance ! 0}"/> <br/>
                    <@ui.formInput id="t5" name="players_addshot_min" label="Минимальное количество дополнительных попыток" value="${parameters.playersAddshotMin ! 0}"/> <br/>
                    <@ui.formInput id="t6" name="players_addshot_max" label="Максимальное количество дополнительных попыток" value="${parameters.playersAddshotMax ! 0}"/> <br/>
                    <@ui.formInput id="t7" name="players_number_addshot" label="Количество игроков с шансом на дополнительные попытки" value="${parameters.playersAddshotMax ! 0}"/> <br/>
                    <@ui.formInput id="t8" name="holiday_sample_freq" label="Частота дискретизации, мин" value="${parameters.holidaySampleFreq ! 0}"/> <br/>
                    <@ui.formInput id="t9" name="holiday_fill_chance" label="Веротяность заполнения праздника, %" value="${parameters.holidayFillChance ! 0}"/> <br/>
                    <@ui.formInput id="t10" name="holiday_push_chance" label="Веротяность смещения игрока с праздника ,%" value="${parameters.holidayPushChance ! 0}"/> <br/>
                    <@ui.formInput id="t11" name="holiday_simple_chance" label="Веротяность простого праздника" value="${parameters.holidaySimpleChance ! 0}"/> <br/>
                    <@ui.formInput id="t12" name="holiday_banquet_chance" label="Веротяность пиршенства" value="${parameters.holidayBanquetChance ! 0}"/> <br/>
                    <@ui.formInput id="t13" name="holiday_dinner_chance" label="Веротяность ужина" value="${parameters.holidayDinnerChance ! 0}"/> <br/>
                    <input type="submit" value="Save" />
                </form>
            </fieldset>
        </div>
   </body>
</html>