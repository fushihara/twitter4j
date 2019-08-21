@echo off
:sjis‚Å‚·
setlocal

set yyyy=%date:~0,4%
set mm=%date:~5,2%
set dd=%date:~8,2%

set time2=%time: =0%

set hh=%time2:~0,2%
set mn=%time2:~3,2%
set ss=%time2:~6,2%
set TIME_STAMP=%yyyy%-%mm%%dd%-%hh%%mn%%ss%

set DIR=build\twitter4j-build-result-%TIME_STAMP%
mkdir build
mkdir %DIR%

mkdir %DIR%\twitter4j-async
cp -r twitter4j-async\src     %DIR%\twitter4j-async\
cp    twitter4j-async\pom.xml %DIR%\twitter4j-async\

mkdir %DIR%\twitter4j-core
cp -r twitter4j-core\src     %DIR%\twitter4j-core\
cp    twitter4j-core\pom.xml %DIR%\twitter4j-core\

mkdir %DIR%\twitter4j-examples
cp -r twitter4j-examples\src     %DIR%\twitter4j-examples\
cp    twitter4j-examples\pom.xml %DIR%\twitter4j-examples\

mkdir %DIR%\twitter4j-media-support
cp -r twitter4j-media-support\src     %DIR%\twitter4j-media-support\
cp    twitter4j-media-support\pom.xml %DIR%\twitter4j-media-support\

mkdir %DIR%\twitter4j-stream
cp -r twitter4j-stream\src     %DIR%\twitter4j-stream\
cp    twitter4j-stream\pom.xml %DIR%\twitter4j-stream\

mkdir %DIR%\twitter4j-http2-support
cp -r twitter4j-http2-support\src     %DIR%\twitter4j-http2-support\
cp    twitter4j-http2-support\pom.xml %DIR%\twitter4j-http2-support\

cp pom.xml %DIR%/pom.xml
cp LICENSE.txt %DIR%/
cp readme.txt %DIR%/

cd %DIR%
cd twitter4j-core
call mvn clean package -Dmaven.test.skip=true
cd ../twitter4j-examples
call mvn clean package -Dmaven.test.skip=true
cd ../twitter4j-media-support
call mvn clean package -Dmaven.test.skip=true
cd ../twitter4j-async
call mvn clean package -Dmaven.test.skip=true
cd ../twitter4j-stream
call mvn clean package -Dmaven.test.skip=true
cd ../twitter4j-http2-support
call mvn clean package -Dmaven.test.skip=true
cd ..

mkdir libs
copy    twitter4j-core\target\*.jar .\libs\
copy    twitter4j-examples\target\*.jar .\libs\
copy    twitter4j-media-support\target\*.jar .\libs\
copy    twitter4j-async\target\*.jar .\libs\
copy    twitter4j-stream\target\*.jar .\libs\
copy    twitter4j-http2-support\target\*.jar .\libs\
