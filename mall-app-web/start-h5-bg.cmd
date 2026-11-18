@echo off
set "PATH=D:\dev\tools\node-v20.11.1-win-x64;%PATH%"
cd /d D:\dev\projects\mall-app-web
"D:\dev\tools\node-v20.11.1-win-x64\npm.cmd" run dev:h5 > D:\dev\projects\mall-app-web\h5-run.log 2>&1
