FROM gradle:7.3.3-jdk11
EXPOSE 8282
ADD --chown=gradle . /code
WORKDIR /code
RUN gradle clean build -x test
CMD gradle bootRun