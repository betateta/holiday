<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "css/style.css">
    <title>start session page</title>
</head>
<body>
    <h2>${statistic_title}</h2>
     <div>
        <form name="" action="/user/session" method="GET">
            <input type="submit" value="${statistic_button_new}" />
        </form>
     </div>

    <div class="container">
        <div class="layer1">
             <table border="1px" cellspacing="2" border="1" cellpadding="5">
             <caption><h3>${statistic_table_parameters_title}</h3></caption>
                <tr>
                    <td><b>${statistic_table_parameters_user}</b></td>
                    <td>${user.username}</td>
                </tr>
                <tr>
                    <td><b>${statistic_table_parameters_begin}</b></td>
                    <td>${session.startTime}</td>
                </tr>
                <tr>
                    <td><b>${statistic_table_parameters_end}</b></td>
                    <td>${session.stopTime}</td>
                </tr>
                <tr>
                    <td><b>${statistic_table_parameters_duration}</b></td>
                    <td>${session.sessionDuration}</td>
                </tr>
                <tr>
                    <td><b>${statistic_table_parameters_players}</b></td>
                    <td>${session.sessionPlayers}</td>
                </tr>
                <tr>
                    <td><b>${statistic_table_parameters_holidays}</b></td>
                    <td>${session.numberOfHolidays}</td>
                </tr>
                <tr>
                    <td><b>${statistic_table_parameters_uncomplete}</b></td>
                    <td>${session.numberOfIncompleteHolidays}</td>
                </tr>
                <tr>
                    <td><b>${statistic_table_parameters_points}</b></td>
                    <td>${session.points}</td>
                </tr>
                <tr>
                    <td colspan="2">
                            <input class="hide" id="hd-2" type="checkbox">
                            <label for="hd-2">${statistic_list_players_title}</label>
                            <div>
                                <table border="1px" cellspacing="2" border="1" cellpadding="5">
                                <caption><h3>${statistic_list_players_title}</h3></caption>
                                     <tr>
                                        <th>${statistic_list_players_name}</th>
                                        <th>${statistic_list_players_points}</th>
                                        <th>${statistic_list_players_shots}</th>
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
                        <label for="hd-1">${statistic_list_param_title}</label>
                        <div>
                            <table border="1px" cellspacing="1" border="1" cellpadding="5">
                              <tr>
                                <td>${statistic_list_param_AddshotChance}</td>
                                <td>${parameters.playersAddshotChance}</td>
                              </tr>
                              <tr>
                                <td>${statistic_list_param_AddshotMin}</td>
                                <td>${parameters.playersAddshotMin}</td>
                              </tr>
                              <tr>
                                <td>${statistic_list_param_AddshotMax}</td>
                                <td>${parameters.playersAddshotMax}</td>
                              </tr>
                              <tr>
                                <td>${statistic_list_param_NumberAddshot}</td>
                                <td>${parameters.playersNumberAddshot}</td>
                              </tr>
                              <tr>
                                <td>${statistic_list_param_SampleFreq}</td>
                                <td>${parameters.holidaySampleFreq}</td>
                              </tr>
                                <td>${statistic_list_param_FillChance}</td>
                                <td>${parameters.holidayFillChance}</td>
                              </tr>
                              <tr>
                                <td>${statistic_list_param_PushChance}</td>
                                <td>${parameters.holidayPushChance}</td>
                              </tr>
                              <tr>
                                <td>${statistic_list_param_SimpleChance}</td>
                                <td>${parameters.holidaySimpleChance}</td>
                              </tr>
                              <tr>
                                <td>${statistic_list_param_BanquetChance}</td>
                                <td>${parameters.holidayBanquetChance}</td>
                              </tr>
                              <tr>
                                <td>${statistic_list_param_DinnerChance}</td>
                                <td>${parameters.holidayDinnerChance}</td>
                              </tr>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">  <img src="/getImgAsBytes/1.jpeg" /> </td>
                </tr>
                <tr>
                    <td colspan="2">  <img src="/getImgAsBytes/2.jpeg" /> </td>
                </tr>
             </table>
        </div>


        <div class="layer3">
            <table border="1px" cellspacing="2" border="1" cellpadding="5">
            <caption><h3>${statistic_table_holidays_title}</h3></caption>
                 <tr>
                    <th>ID</th>
                    <th>${statistic_table_holidays_begin}</th>
                    <th>${statistic_table_holidays_end}</th>
                    <th>${statistic_table_holidays_type}</th>
                    <th>${statistic_table_holidays_cap}</th>
                    <th>${statistic_table_holidays_points}</th>
                    <th>${statistic_table_holidays_members}</th>
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
                                <label for="hc-${calculate.id}">${statistic_list_members_title}</label>
                                <div>
                                    <table border="1px" cellspacing="1" border="1" cellpadding="5">
                                        <tr>
                                            <th>${statistic_list_members_name}</th>
                                            <th>${statistic_list_members_in}</th>
                                            <th>${statistic_list_members_out}</th>
                                            <th>${statistic_list_members_time}</th>
                                            <th>${statistic_list_members_points}</th>
                                            <th>${statistic_list_members_org}</th>
                                        </tr>
                                        <#list calculate.memberSet as member>
                                            <tr>
                                                <td>${member.player.name}</td>
                                                <td>${member.inputTime}</td>
                                                <td>${member.outputTime}</td>
                                                <td>${member.duration}</td>
                                                <td>${member.holidayPoints}</td>
                                                <#if member.isOrganizator == true>
                                                    <td bgcolor="green">${member.isOrganizator?string('${statistic_isOrg_yes}', '${statistic_isOrg_no}')}</td>
                                                <#else>
                                                    <td> ${member.isOrganizator?string('${statistic_isOrg_yes}', '${statistic_isOrg_no}')}</td>
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