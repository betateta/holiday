<#import "/spring.ftl" as spring />

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
                    Количество игроков:<br>
                        <@spring.formInput path="parameters.sessionPlayers" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Длительность сессии, суток: <br>
                        <@spring.formInput path="parameters.sessionDuration" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Вероятность дополнительных попыток, %:<br>
                        <@spring.formInput path="parameters.playersAddshotChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Минимальное количество дополнительных попыток:<br>
                        <@spring.formInput path="parameters.playersAddshotMin" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Максимальное количество дополнительных попыток:<br>
                        <@spring.formInput path="parameters.playersAddshotMax" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Количество игроков с шансом на дополнительные попытки:<br>
                        <@spring.formInput path="parameters.playersNumberAddshot" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Частота дискретизации, мин :<br>
                        <@spring.formInput path="parameters.holidaySampleFreq" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Вероятность заполнения праздника, %:<br>
                        <@spring.formInput path="parameters.holidayFillChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Вероятность смещения игрока с праздника ,%:<br>
                        <@spring.formInput path="parameters.holidayPushChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Вероятность простого праздника ,%:<br>
                        <@spring.formInput path="parameters.holidaySimpleChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Вероятность пиршенства ,%:<br>
                        <@spring.formInput path="parameters.holidayBanquetChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    Вероятность ужина ,%:<br>
                        <@spring.formInput path="parameters.holidayDinnerChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>

                        <input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}" />
                    <div>
                      <p> <input type="submit" value="Сохранить параметры" /> </p>
                    </div>
                </form>
                <div>
                    <form name="parameters" action="start_session" method="GET">
                    <p><input type="submit" value="Начать новую сессию" /> </p>
                    </form>
                 </div>
                <div>
                 <form action="backtomain" method="GET">
                    <p><input type="submit" value="<-Назад" /></p>
                 </form>
                 </div>
            </fieldset>
        </div>

   </body>
</html>