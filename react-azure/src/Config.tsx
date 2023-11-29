import { LogLevel } from "@azure/msal-browser";

export const Config = {
    auth: {
        clientId:  "3b107724-46f1-4b93-819a-9318ae1ddf5f",
        authority: "https://login.microsoftonline.com/b9912f5e-1348-4b8d-a638-01bd28d62837", 
        redirectUri: "/",
        postLogoutRedirectUri: "/",
        navigateToLoginRequestUrl: false
    },
    cache: {
        cacheLocation: "sessionStorage",
        storeAuthStateInCookie: false
    },
    system: {
        loggerOptions: {
            loggerCallback: (level, message, containsPii) => {
                if (containsPii) {
                    return;
                }
                switch (level) {
                    case LogLevel.Error:
                        console.error(message);
                        return;
                    case LogLevel.Info:
                        console.info(message);
                        return;
                    case LogLevel.Verbose:
                        console.debug(message);
                        return;
                    case LogLevel.Warning:
                        console.warn(message);
                        return;
                }
            }
        }
    }
};

export const protectedResources = {
    testApi: {
        endpoint: "http://localhost:8080/authTest",
        scopes: {}
    }
}

export const loginRequest = {
    scopes: ["user.read"]
}