<#import "/spring.ftl" as spring/>

<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
        <#include "css/style.css">
      <title>${index_title}</title>
   </head>
   <body>
         <h2>${index_user} ${name}</h2>
         <div>
                 <table border="1px" cellspacing="2" border="1" cellpadding="5">
                       <tr>
                            <td colspan="2">
                                <input class="hide" id="hd-1" type="checkbox">
                                <label for="hd-1">${index_list_session_title}</label>
                                <div>
                                    <table border="1px" cellspacing="1" border="1" cellpadding="5">
                                        <tr>
                                            <th>${index_list_session_begin}</th>
                                            <th>${index_list_session_end}</th>
                                            <th>${index_list_session_duration}</th>
                                            <th>${index_list_session_players}</th>
                                            <th>${index_list_session_holidays}</th>
                                            <th>${index_list_session_points}</th>
                                        </tr>
                                        <#list sessions as session>
                                            <tr>
                                                <td>${session.startTime}</td>
                                                <td>${session.stopTime}</td>
                                                <td>${session.sessionDuration}</td>
                                                <td>${session.sessionPlayers}</td>
                                                <td>${session.numberOfHolidays}</td>
                                                <td>${session.points}</td>
                                                <tr>
                                                    <td colspan="6">
                                                        <input class="hide" id="hd-${session.id}" type="checkbox">
                                                        <label for="hd-${session.id}">${index_list_parameters_title}</label>
                                                        <div>
                                                            <table border="1px" cellspacing="1" border="1" cellpadding="5">
                                                                <tr>
                                                                    <td>${playersAddshotChance}</td>
                                                                    <td>${session.playersAddshotChance}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>${playersAddshotMin}</td>
                                                                    <td>${session.playersAddshotMin}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>${playersAddshotMax}</td>
                                                                    <td>${session.playersAddshotMax}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>${playersNumberAddshot}</td>
                                                                    <td>${session.playersNumberAddshot}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>${holidaySampleFreq}</td>
                                                                    <td>${session.holidaySampleFreq}</td>
                                                                </tr>
                                                                    <td>${holidayFillChance}</td>
                                                                    <td>${session.holidayFillChance}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>${holidayPushChance}</td>
                                                                    <td>${session.holidayPushChance}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>${holidaySimpleChance}</td>
                                                                    <td>${session.holidaySimpleChance}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>${holidayBanquetChance}</td>
                                                                    <td>${session.holidayBanquetChance}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>${holidayDinnerChance}</td>
                                                                    <td>${session.holidayDinnerChance}</td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                    </td>
                                                </tr>    
                                            </tr>
                                        </#list>
                                    </table>
                                </div>
                            </td>
                        </tr>
                 </table>
         </div>

         <div>
            <form name="session" action="/user/session" method="GET">
                <p> <input type="submit" value="${index_button_new}" /> </p>
            </form>
         </div>
         <div>
            <form name="test" action="/user/test_sse" method="GET">
                <p> <input type="submit" value="${index_button_fast}" /> </p>
            </form>
         </div>
         <div>
              <p>
                <a href="/logout">Logout</a>
              </p>
         </div>
   </body>
</html>