import React from 'react';
// Supports weights 100-900
import "@fontsource/inter"; // Defaults to weight 400
import "@fontsource/inter/400.css"; // Specify weight
import "@fontsource/inter/400-italic.css"; // Specify weight and style
import { Provider } from 'react-redux';
import { store } from './state';
import AuthenticationStateHandler from './components/organisms/AuthenticationStateHandler';
import { createBrowserRouter, Route, RouterProvider, Routes } from 'react-router-dom';
import {
    AboutMenuLayout, Architecture,
    AuthenticationProvider,
    DidAdd,
    DidAddCompleted,
    DidAddPreview,
    DidDetail,
    DidList,
    I18n,
    Introduction,
    Loading,
    Login,
    MainMenuLayout
} from './components';
import { configureAxiosDefaults } from './AxiosConfig';
import { PrimeReactProvider } from 'primereact/api';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import './theme/themes/mytheme/theme.scss';
import 'primeflex/primeflex.scss';
import './App.css';
import { Home } from './components/pages/Home';
import { TrustedIssuerListList } from './components/pages/about/TrustedIssuerListList';


function App() {

    configureAxiosDefaults(store);

    const router = createBrowserRouter([
        {
            path: "*", element: <Root/>, handle: {
                crumb: () => "Home"
            }
        },
    ]);

    function Root() {
        return (
            <AuthenticationProvider>
                <AuthenticationStateHandler
                    loading={<Loading/>}
                    unAuthenticated={
                        <Routes>
                            <Route path="/" element={<MainMenuLayout/>}>
                                <Route path="/" element={<DidList/>}/>
                                <Route path="*" element={<DidList/>}/>
                                <Route path="/did" element={<DidList/>}/>
                                <Route path="/did/add" element={<DidAdd/>}/>
                                <Route path="/did/add/preview/:did" element={<DidAddPreview/>}/>
                                <Route path="/did/add/completed" element={<DidAddCompleted/>}/>
                                <Route path="/did/:externalKey" element={<DidDetail/>}/>
                                <Route path="/home" element={<Home/>}/>
                                <Route path="/login" element={<Login/>}/>
                                <Route path="/search" element={<DidList/>}/>
                            </Route>
                            <Route path="/about" element={<AboutMenuLayout/>}>
                                <Route path="introduction" element={<Introduction/>}/>
                                <Route path="architecture" element={<Architecture/>}/>
                                <Route path="trustedIssuers" element={<TrustedIssuerListList/>}/>
                            </Route>
                        </Routes>
                    }
                    authenticated={
                        <Routes>
                            <Route path="/" element={<MainMenuLayout/>}>
                                <Route path="/" element={<Home/>}/>
                                <Route path="*" element={<Home/>}/>
                            </Route>
                        </Routes>
                    }
                />
            </AuthenticationProvider>
        );
    }


    return (
        <Provider store={store}>
            <I18n/>
            <PrimeReactProvider>
                <RouterProvider router={router}/>
            </PrimeReactProvider>
        </Provider>
    );
}

export default App;
