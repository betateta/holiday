<!DOCTYPE html>
<html>
<head>

    <title>Progress</title>
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
    <h2 id="title" >${sse_title}</h2>
    <div id="progressbar"></div>
</main>
</body>
<script>
    let sse = new EventSource('/user/sse');
    let count = 0;
    sse.addEventListener("COMPLETE", function(evt) {
        progressBar.setPercent(100);
        sse.close();
        document.location.href = "/user/get_statistic";
    });
    sse.addEventListener("START", function(evt) {
          progressBar.setPercent(0);
    });
    sse.addEventListener("CONTINUE", function(evt) {
              let elem = document.getElementById('title');
             // elem.innerHTML.replaceWith("Сохранение результатов");
              progressBar.setPercent(evt.data);
    });


</script>
</html>
