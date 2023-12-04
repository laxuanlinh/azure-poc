import { useIsAuthenticated, useMsal, useAccount, useMsalAuthentication } from "@azure/msal-react";
import { AuthenticationResult } from '@azure/msal-browser';
import { useState } from 'react';
import { Config, loginRequest, protectedResources } from "../Config";

export default function Login() {
    const isAuthenticated = useIsAuthenticated();
    const { instance, accounts, inProgress } = useMsal();
    const account = instance.getActiveAccount();
    const [value, setValue] = useState("");
    const getTest = async () => {
        const silentRequest = {
            scopes: [Config.auth.clientId+"/.default"],
            account: account
        };
        const token: AuthenticationResult = await instance.acquireTokenSilent(
            silentRequest
        );
        fetch(protectedResources.testApi.endpoint, {
            headers: {
                "Authorization": "Bearer "+token.accessToken
            }
        })
        .then(res => res.json())
        .then(res => setValue(res.key));
    }
    return (
        <div>
            {
                isAuthenticated ? 
                <button onClick={() => instance.logoutRedirect()}>Logout</button> 
                : <button onClick={() => instance.loginRedirect({...loginRequest})}>Login</button>
            }
            {
                isAuthenticated && account ? <p>{account.name}</p> : <p></p>
            }
            {
                isAuthenticated ? <button onClick={getTest}>Fetch</button> : ""
            }
            <p>{value}</p>
        </div>
    )
}