import './App.css'
import { PublicClientApplication, EventType } from '@azure/msal-browser';
import { Config } from './Config';
import { MsalProvider } from '@azure/msal-react';
import Login from './components/Login';

function App() {
  const msalInstance = new PublicClientApplication(Config);
  msalInstance.addEventCallback(event => {
    console.log(event);
    if (event.eventType === EventType.LOGIN_SUCCESS && event.payload && event.payload.account){
      msalInstance.setActiveAccount(event.payload.account);
    }
  })

  return (
    <>
      <MsalProvider instance={msalInstance}>
        <Login/>
      </MsalProvider>
    </>
  )
}

export default App
