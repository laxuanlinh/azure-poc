import { useIsAuthenticated, useMsal, useAccount, useMsalAuthentication } from "@azure/msal-react";
import { AuthenticationResult } from '@azure/msal-browser';
import { useState } from 'react';
import { Config, loginRequest, protectedResources } from "../Config";

export default function Login() {
    const isAuthenticated = useIsAuthenticated();
    const { instance, accounts, inProgress } = useMsal();
    const account = instance.getActiveAccount();
    const [value, setValue] = useState("");
    const getTest = async (endpoint) => {
        const silentRequest = {
            scopes: [Config.auth.clientId+"/.default"],
            account: account
        };
        const token: AuthenticationResult = await instance.acquireTokenSilent(
            silentRequest
        );
        fetch(endpoint, {
            headers: {
                "Authorization": "Bearer "+token.accessToken
            }
        })
        .then(res => res.json())
        .then(res => {setValue(res.key)}, error => {setValue("You're not allowed to fetch from "+endpoint)});
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
                isAuthenticated ? 
                <div>
                    <button onClick={() => getTest(protectedResources.pageContent.endpoint)}>Page content</button>
                    <button onClick={() => getTest(protectedResources.codeTable.endpoint)}>Code table</button>
                    <button onClick={() => getTest(protectedResources.routing.endpoint)}>Routing</button>
                    <button onClick={() => getTest(protectedResources.processData.endpoint)}>Process request</button>
                </div> : ""
            }
            {
                isAuthenticated ? <p>{value}</p> : ""
            }
        </div>
    )
}