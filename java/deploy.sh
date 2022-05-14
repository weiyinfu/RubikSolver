mvn clean
mvn -Dmaven.test.skip=true package
scp target/Rubik-0.0.1-SNAPSHOT-prod.jar me:~/app/RubikSolver/