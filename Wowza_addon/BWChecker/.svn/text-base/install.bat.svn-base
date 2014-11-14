@ECHO OFF
echo Installing BWChecker...
xcopy "%WMSAPP_HOME%\examples\BWChecker\conf" "%WMSAPP_HOME%\conf" /s  /Y  /Q > NUL
IF NOT EXIST "%WMSAPP_HOME%\applications\bwcheck" mkdir "%WMSAPP_HOME%\applications\bwcheck"
copy "%WMSAPP_HOME%\examples\BWChecker\lib\*" "%WMSAPP_HOME%\lib" > NUL
IF NOT "%1" == "all" pause
