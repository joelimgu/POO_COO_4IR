FROM gradle:jdk17
COPY . .
ENTRYPOINT gradle run --args='server';