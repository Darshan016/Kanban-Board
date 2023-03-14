FROM openjdk:11-jre-slim
EXPOSE 8082
WORKDIR usr/src
ENV MONGO_URI=mongodb://localhost:27017/kanban_Db
ADD ./target/KanbanService-0.0.1-SNAPSHOT.jar /usr/src/KanbanService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","KanbanService-0.0.1-SNAPSHOT.jar"]
