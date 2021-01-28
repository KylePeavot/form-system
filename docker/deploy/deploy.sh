git pull
cd ../../frontend
yarn install
yarn build --modules-folder frontend
cd ../backend
./mvnw package --file pom.xml
cd ../
docker build -t weffs -f docker/deploy/Dockerfile backend

docker stop weffs_app
docker rm weffs_app
docker run -d --name weffs_app -p 80:80 --memory="1g" weffs