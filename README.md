# WEFFS #

## Aim ##

We are trying to create a web based form filling system to replace paper based 
versions of the forms that are currently being sent by emails.

## Setup ##
### Prerequisites

    JDK 15.

    IntelliJ Ultimate (Preferable for Spring Boot and Lombok support).

    Node 12.x

    Yarn package manager (npm install -g yarn)

### Backend Setup

    Set JDK and language level to 15 in IntelliJ’s project settings.

    Install the Lombok plugin in IntelliJ.

    Enable annotation processors.

        See: https://www.jetbrains.com/help/idea/annotation-processors-support.html

    Enable Optimize Imports

        See: https://www.jetbrains.com/help/idea/creating-and-optimizing-imports.html

    Right click the WeffsApplication java file and click “Run”.

    In your run configuration that is created, set your active profile to development

### Frontend Setup

Perform all of the following steps within a terminal. IntelliJ has a terminal available internally which can be used.

    cd frontend

    yarn install

    yarn serve

Finally, install the Vue plugin in IntelliJ.

## Notes

The backend must be started prior to the frontend to prevent port conflicts.

You can hot-reload the backend using ctrl+f9 within IntelliJ providing that no method signatures have changed and no methods have been added.

The Vue frontend will hot-reload automatically on save.



