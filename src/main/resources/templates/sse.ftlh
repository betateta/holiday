<!DOCTYPE html>
<html>
<head>

    <title>Progress Bar: Basic Sample (Javascript)</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">
    <style type="text/css">
        <#include "css/lib/control/progressbar.css">
    </style>

    <script type="text/javascript">
         <#include "lib/control/progressbar.min.js">
    </script>

    <script type="text/javascript">

            let progressBar;
            window.onload = function(){
                progressBar = new ProgressBar("progressbar", {'width':'70%', 'height':'10px'});
                progressBar.setPercent(0);
            }
    </script>

</head>

<body>
<main>
    <h2>Progress Bar</h2>
    <div id="progressbar"></div>
</main>
</body>
<script>
    let sse = new EventSource('/sse');
    let count = 0;
    sse.addEventListener("COMPLETE", function(evt) {
        progressBar.setPercent(100);
        sse.close();
        document.location.href = "/get_statistic";
    });
    sse.addEventListener("START", function(evt) {
          progressBar.setPercent(0);
    });
    sse.addEventListener("CONTINUE", function(evt) {
              progressBar.setPercent(evt.data);
    });

        var xhr = function(url) {
            return new Promise(function(resolve, reject) {
              try {
                var xmhr = new XMLHttpRequest();
                //Listen for API Response
                xmhr.onreadystatechange = function() {
                  if (xmhr.readyState == XMLHttpRequest.DONE && xmhr.status == 200) return resolve(xmhr.responseText);
                };
                //Open connection
                xmhr.open("GET", url, true);
                //Additional headers as needed
                //x.withCredentials = true;
                //x.setRequestHeader("Accept", "application/json");
                x.setRequestHeader("Content-Type", "text/plain");
                //Perform the actual AJAX call
                xmhr.send();
              } catch (ex) {
                reject("Exception: Error of XMLHttpRequest");
              }
            });
          };
</script>
</html>
