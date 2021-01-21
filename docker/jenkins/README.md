# Jenkins Setup

## What is Jenkins?
Short and sweet, Jenkins is a pipeline tool that lets us run CI locally.

## Prerequisites
1. Docker
2. Time

## Alternative to Docker
You may prefer to not use Docker, in which case you can run a local Windows-built instance of Jenkins.

I have no idea how to set it up, although it should mostly be similar. Good luck.

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
5. Create your account
   - I'd recommend using the admin username.
    - Upon updating the password it may error and sign you out. The password should have updated as this point.
6. Install recommended plugins
7. Done!

## Future launches
1. Run `start.bat`
2. Navigate to `http://localhost:8100`
3. Login

## Jenkins Setup

### Plugin Setup
You'll have to install a plugin called `Bitbucket Build Status Notifier Plugin`

To do this:
1. On the Jenkins dashboard, click `Manage Jenkins` in the navigation side menu.
2. Go to `Manage Plugins`
3. Install `Bitbucket Build Status Notifier Plugin`
   - Don't restart after installing. Jenkins is broken when doing this and so you'll have to stop the docker containers and run `start.bat` again.
4. Upon restarting go to `Manage Jenkins`
5. Click `Configure System`
6. At the bottom you'll see a `Bitbucket Build Status Notifier Plugin` section. Add global credentials.
   - Keep everything as default, but set the username to the OAuth key and the password to the value.
7. Save changes.

### Executors (optional)
1. Go to `Configure System`
2. Set the `# of executors` to a value you'd like. 3 is recommended as it can run both steps in parallel.
3. Save

### Build setup
You'll have to create a job in order to run builds.

1. From the Jenkins dashboard click `New Item`
2. Give it a name (WEFFS is probably a good idea).
3. Select `Bitbucket Team/Project`.
4. Click `OK`
5. Give it a display name.
6. Under the `Projects` section, add your credentials from the [Plugin Setup](#plugin-setup) section if they exist.
   - If the credentials don't exist here, add the same as in [Plugin Setup](#plugin-setup).
7. Under `Discover branches` set the strategy to whatever you'd prefer. `All branches` may be the best option.
8. Click `Save`.
9. You'll be taken to a `Scan Organization Folder Log` page. Wait for the generated text to end with `Finished: SUCCESS`.
10. Click `Open Blue Ocean`

## Generating a `ca.pem` certificate (If required)

**I initially had issues with the setup which I think caused it to fail generating the certificates.
Deleting my volumes and rebuilding no longer required this step.**

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