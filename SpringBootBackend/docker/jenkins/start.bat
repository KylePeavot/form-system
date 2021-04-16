IF NOT EXIST ./ran_before (
	docker network create weffs-jenkins-network-adapter
	echo "" > ./ran_before
)

docker run --name weffs-jenkins-docker --memory="1g" --rm --detach ^
  --privileged --network weffs-jenkins-network-adapter --network-alias docker ^
  --env DOCKER_TLS_CERTDIR=/certs ^
  --volume jenkins-docker-certs:/certs/client ^
  --volume jenkins-data:/var/jenkins_home ^
  docker:dind

cd ./blueocean
docker build . -t weffs-jenkins-blueocean:1.1

docker run --name weffs-jenkins-blueocean --memory="1g" --rm --detach ^
  --network weffs-jenkins-network-adapter --env DOCKER_HOST=tcp://docker:2376 ^
  --env DOCKER_CERT_PATH=/certs/client --env DOCKER_TLS_VERIFY=1 ^
  --volume jenkins-data:/var/jenkins_home ^
  --volume jenkins-docker-certs:/certs/client:ro ^
  --publish 8100:8080 --publish 50000:50000 weffs-jenkins-blueocean:1.1