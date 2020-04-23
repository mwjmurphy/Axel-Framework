rem gradle clean build -x test
copy build\libs\axel-run-*.war build\libs\axel-run-*.jar
gradle publish -x test
