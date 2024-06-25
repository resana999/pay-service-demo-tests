к резюме ildar показал основной стек

запуск ./install.sh или команды по очереди <br>
1 сборка mvn clean package <br>
2 докеризация docker build -t sprinbootcore:dev . <br>
3 запуск бд в докере и приложения docker-compose up -d --build <br>
