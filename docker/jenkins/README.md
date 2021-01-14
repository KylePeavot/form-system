# Jenkins Setup

## What is Jenkins?
Short and sweet, Jenkins is a pipeline tool that lets us run CI locally.

## Prerequisites
1. Docker

## First launch
1. Run `start.bat`
    - Note that the first run will take a while.
    - Subsequent launches are extremely fast.
2. Run `logs.bat`
    - You'll need to find the secret key in your `jenkins-data` folder.
    - If you miss this, you'll need to find where Docker stores your volumes.
    - If using Docker + WSL2, path can be found here: `\\wsl$\docker-desktop\mnt\host\wsl\docker-desktop-data\version-pack-data\community\docker\volumes`
3. Navigate to `http://localhost:8100`
4. Enter the secret copied from step 2.
5. Update the admin account password.
    - Upon updating the password it may error and sign you out. The password should have updated as this point.
6. Done!

## Future launches
1. Run `start.bat`
2. Navigate to `http://localhost:8100`
3. Login

## Generating a `ca.pem` certificate
This is something required by Jenkins in order to make web requests, and is relatively complicated considering.

Execute the following commands in a terminal
1. `docker exec -it weffs-jenkins-blueocean bash`
2. `openssl genrsa -des3 -out /var/jenkins_home/CA-key.pem 2048`
    - Enter a passphrase. It can be anything.
3. `openssl req -new -key /var/jenkins_home/CA-key.pem -x509 -days 1000 -out /var/jenkins_home/ca.pem`
    - The passphrase here should match the passphrase entered in part 3.
4. Enter the country code (UK).
5. Enter State/Province as Kent.
6. Leave the rest as default.
7. Navigate to your volume folder (see [First launch](#first-launch)).
8. Under `jenkins_data/_data` copy `ca.pem`
9. Go back to your volume folder and paste `ca.pem` into `jenkins-docker-certs/_data`
    - This command couldn't be ran directly into here as this folder is read-only from inside the container, explaining the manual approach.
10. Builds requiring a `ca.pem` file will now work correctly.