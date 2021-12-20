<#import "/spring.ftl" as spring />

<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>${session_title}</title>
       <#include "css/style.css">
   </head>
   <body>
        <div>
            <fieldset>
                <legend><h3>${session_title}</h3></legend>
                <form name="parameters" action="/user/save" method="POST">
                    ${sessionPlayers}<br>
                        <@spring.formInput path="parameters.sessionPlayers" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${sessionDuration}<br>
                        <@spring.formInput path="parameters.sessionDuration" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${playersAddshotChance}<br>
                        <@spring.formInput path="parameters.playersAddshotChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${playersAddshotMin}<br>
                        <@spring.formInput path="parameters.playersAddshotMin" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${playersAddshotMax}<br>
                        <@spring.formInput path="parameters.playersAddshotMax" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${playersNumberAddshot}<br>
                        <@spring.formInput path="parameters.playersNumberAddshot" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${holidaySampleFreq}<br>
                        <@spring.formInput path="parameters.holidaySampleFreq" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${holidayFillChance}<br>
                        <@spring.formInput path="parameters.holidayFillChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${holidayPushChance}<br>
                        <@spring.formInput path="parameters.holidayPushChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${holidaySimpleChance}<br>
                        <@spring.formInput path="parameters.holidaySimpleChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${holidayBanquetChance}<br>
                        <@spring.formInput path="parameters.holidayBanquetChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>
                    ${holidayDinnerChance}<br>
                        <@spring.formInput path="parameters.holidayDinnerChance" attributes="" fieldType="number"/>
                        <@spring.showErrors "<br>"/> <br>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <div>
                      <p> <input type="submit" value="${session_button_save}" /> </p>
                    </div>
                </form>
                <div>
                    <form name="parameters" action="/user/start_session" method="GET">
                    <p><input type="submit" value="${session_button_begin}" /> </p>
                    </form>
                 </div>
                <div>
                 <form action="/user" method="GET">
                    <p><input type="submit" value="${button_back}" /></p>
                 </form>
                 </div>
            </fieldset>
        </div>

   </body>
</html>