$ip = Read-Host -Prompt "Enter your name"
(Get-Content ..\backend\src\main\resources\application.properties.docker) -replace 'host-ip', $ip | Out-File -encoding UTF-8 ..\backend\src\main\resources\application.properties.docker
(Get-Content ..\backend\src\main\java\pl\edu\pk\backend\security\SockConfig.java) -replace 'host-ip', $ip | Out-File -encoding UTF-8 ..\backend\src\main\java\pl\edu\pk\backend\security\SockConfig.java
(Get-Content ..\backend\src\main\java\pl\edu\pk\backend\controllers\RestHandler.java) -replace 'host-ip', $ip | Out-File -encoding UTF-8 ..\backend\src\main\java\pl\edu\pk\backend\controllers\RestHandler.java
(Get-Content ..\frontend\src\config\backend.json) -replace 'host-ip', $ip | Out-File -encoding UTF-8 ..\frontend\src\config\backend.json
docker build -f Dockerfile.spring -t spring .
docker build -f Dockerfile.react -t react .