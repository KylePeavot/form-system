call git pull
cd ../../frontend
call yarn install
call yarn build --modules-folder frontend
cd ../backend
call mvnw.cmd package --file pom.xml
cd ../
call docker build -t weffs -f docker/deploy/Dockerfile backend

call docker stop weffs_app
call docker rm weffs_app
call docker run -d --name weffs_app -p 80:80 --memory="1g" weffs